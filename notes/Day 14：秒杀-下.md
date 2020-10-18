# Day 14：秒杀-下

## 学习目标

- ==防止秒杀重复排队==

  ```
  重复排队：一个人抢购商品，如果没有支付，不允许重复排队抢购
  ```

- ==并发超卖问题解决==

  ```
  1个商品卖给多个人：1商品多订单
  ```

- ==秒杀订单支付==

  ```
  秒杀支付：支付流程需要调整
  ```

- ==超时支付订单库存回滚==

  ```
  1.RabbitMQ延时队列
  2.利用延时队列实现支付订单的监听，根据订单支付状况进行订单数据库回滚
  ```


## 1 防止秒杀重复排队

用户每次抢单的时候，一旦排队，我们设置一个自增值，让该值的初始值为1，每次进入抢单的时候，对它进行递增，如果值>1，则表明已经排队,不允许重复排队,如果重复排队，则对外抛出异常，并抛出异常信息100表示已经正在排队。



### 1.1 后台排队记录

修改SeckillOrderServiceImpl的add方法，新增递增值判断是否排队中，代码如下：

![1558785112055](https://dylanguo.xyz/img/1558785112055.png)

上图代码如下：

```java
//递增，判断是否排队
Long userQueueCount = redisTemplate.boundHashOps("UserQueueCount").increment(username, 1);
if(userQueueCount>1){
    //100：表示有重复抢单
    throw new RuntimeException(String.valueOf(StatusCode.REPERROR));
}
```



## 2 并发超卖问题解决

超卖问题，这里是指多人抢购同一商品的时候，多人同时判断是否有库存，如果只剩一个，则都会判断有库存，此时会导致超卖现象产生，也就是一个商品下了多个订单的现象。



### 2.1 思路分析

![1557080237953](https://dylanguo.xyz/img/1557080237953.png)

解决超卖问题，可以利用Redis队列实现，给每件商品创建一个独立的商品个数队列，例如：A商品有2个，A商品的ID为1001，则可以创建一个队列,key=SeckillGoodsCountList_1001,往该队列中塞2次该商品ID。

每次给用户下单的时候，先从队列中取数据，如果能取到数据，则表明有库存，如果取不到，则表明没有库存，这样就可以防止超卖问题产生了。

在我们队Redis进行操作的时候，很多时候，都是先将数据查询出来，在内存中修改，然后存入到Redis，在并发场景，会出现数据错乱问题，为了控制数量准确，我们单独将商品数量整一个自增键，自增键是线程安全的，所以不担心并发场景的问题。

![1557081924548](https://dylanguo.xyz/img/1557081924548.png)



### 2.2 代码实现

每次将商品压入Redis缓存的时候，另外多创建一个商品的队列。

修改SeckillGoodsPushTask,添加一个pushIds方法，用于将指定商品ID放入到指定的数字中，代码如下：

```java
/***
 * 将商品ID存入到数组中
 * @param len:长度
 * @param id :值
 * @return
 */
public Long[] pushIds(int len,Long id){
    Long[] ids = new Long[len];
    for (int i = 0; i <ids.length ; i++) {
        ids[i]=id;
    }
    return ids;
}
```

修改SeckillGoodsPushTask的loadGoodsPushRedis方法，添加队列操作，代码如下：

![1557393449652](https://dylanguo.xyz/img/1557393449652.png)

上图代码如下：

```java
//商品数据队列存储,防止高并发超卖
Long[] ids = pushIds(seckillGood.getStockCount(), seckillGood.getId());
redisTemplate.boundListOps("SeckillGoodsCountList_"+seckillGood.getId()).leftPushAll(ids);
//自增计数器
redisTemplate.boundHashOps("SeckillGoodsCount").increment(seckillGood.getId(),seckillGood.getStockCount());
```



### 2.3 超卖控制

修改多线程下单方法，分别修改数量控制，以及售罄后用户抢单排队信息的清理，修改代码如下图：

![1558788854992](https://dylanguo.xyz/img/1558788854992.png)

上图代码如下：

```java
/***
 * 多线程下单操作
 */
@Async
public void createOrder(){
    //从队列中获取排队信息
    SeckillStatus seckillStatus = (SeckillStatus) redisTemplate.boundListOps("SeckillOrderQueue").rightPop();

    try {
        //从队列中获取一个商品
        Object sgood = redisTemplate.boundListOps("SeckillGoodsCountList_" + seckillStatus.getGoodsId()).rightPop();
        if(sgood==null){
            //清理当前用户的排队信息
            clearQueue(seckillStatus);
            return;
        }

        //时间区间
        String time = seckillStatus.getTime();
        //用户登录名
        String username=seckillStatus.getUsername();
        //用户抢购商品
        Long id = seckillStatus.getGoodsId();

        //获取商品数据
        SeckillGoods goods = (SeckillGoods) redisTemplate.boundHashOps("SeckillGoods_" + time).get(id);

        //如果有库存，则创建秒杀商品订单
        SeckillOrder seckillOrder = new SeckillOrder();
        seckillOrder.setId(idWorker.nextId());
        seckillOrder.setSeckillId(id);
        seckillOrder.setMoney(goods.getCostPrice());
        seckillOrder.setUserId(username);
        seckillOrder.setCreateTime(new Date());
        seckillOrder.setStatus("0");

        //将秒杀订单存入到Redis中
        redisTemplate.boundHashOps("SeckillOrder").put(username,seckillOrder);

        //商品库存-1
        Long surplusCount = redisTemplate.boundHashOps("SeckillGoodsCount").increment(id, -1);//商品数量递减
        goods.setStockCount(surplusCount.intValue());    //根据计数器统计

        //判断当前商品是否还有库存
        if(surplusCount<=0){
            //并且将商品数据同步到MySQL中
            seckillGoodsMapper.updateByPrimaryKeySelective(goods);
            //如果没有库存,则清空Redis缓存中该商品
            redisTemplate.boundHashOps("SeckillGoods_" + time).delete(id);
        }else{
            //如果有库存，则直数据重置到Reids中
            redisTemplate.boundHashOps("SeckillGoods_" + time).put(id,goods);
        }
        //抢单成功，更新抢单状态,排队->等待支付
        seckillStatus.setStatus(2);
        seckillStatus.setOrderId(seckillOrder.getId());
        seckillStatus.setMoney(seckillOrder.getMoney().floatValue());
        redisTemplate.boundHashOps("UserQueueStatus").put(username,seckillStatus);
    } catch (Exception e) {
        e.printStackTrace();
    }
}

/***
 * 清理用户排队信息
 * @param seckillStatus
 */
public void clearQueue(SeckillStatus seckillStatus){
    //清理排队标示
    redisTemplate.boundHashOps("UserQueueCount").delete(seckillStatus.getUsername());

    //清理抢单标示
    redisTemplate.boundHashOps("UserQueueStatus").delete(seckillStatus.getUsername());
}
```



## 3 订单支付

![1557113836465](https://dylanguo.xyz/img/1557113836465.png)

完成秒杀下订单后，进入支付页面，此时前端会每3秒中向后台发送一次请求用于判断当前用户订单是否完成支付，如果完成了支付，则需要清理掉排队信息，并且需要修改订单状态信息。



### 3.2 创建支付二维码

下单成功后，会跳转到支付选择页面，在支付选择页面要显示订单编号和订单金额，所以我们需要在下单的时候，将订单金额以及订单编号信息存储到用户查询对象中。

选择微信支付后，会跳转到微信支付页面，微信支付页面会根据用户名查看用户秒杀订单，并根据用户秒杀订单的ID创建预支付信息并获取二维码信息，展示给用户看,此时页面每3秒查询一次支付状态，如果支付成功，需要修改订单状态信息。



#### 3.2.1 回显订单号、金额

下单后，进入支付选择页面，需要显示订单号和订单金额，所以需要在用户下单后将该数据传入到pay.html页面，所以查询订单状态的时候，需要将订单号和金额封装到查询的信息中，修改查询订单装的方法加入他们即可。

修改SeckillOrderController的queryStatus方法，代码如下：

![1558789543285](https://dylanguo.xyz/img/1558789543285.png)

上图代码如下：

```java
return new Result(true,seckillStatus.getStatus(),"抢购状态",seckillStatus);
```



#### 3.2.2 创建二维码

用户创建二维码，可以先查询用户的秒杀订单抢单信息，然后再发送请求到支付微服务中创建二维码，将订单编号以及订单对应的金额传递到支付微服务:`/weixin/pay/create/native`。

使用Postman测试效果如下：

http://localhost:9022/weixin/pay/create/native?outtradeno=1132510782836314112&money=1

![1558847312481](https://dylanguo.xyz/img/1558847312481.png)



### 3.3 支付流程分析

![1558832314454](https://dylanguo.xyz/img/1558832314454.png)

如上图，步骤分析如下：

```properties
1.用户抢单，经过秒杀系统实现抢单，下单后会将向MQ发送一个延时队列消息，包含抢单信息，延时半小时后才能监听到
2.秒杀系统同时启用延时消息监听，一旦监听到订单抢单信息，判断Redis缓存中是否存在订单信息，如果存在，则回滚
3.秒杀系统还启动支付回调信息监听，如果支付完成，则将订单吃句话到MySQL，如果没完成，清理排队信息回滚库存
4.每次秒杀下单后调用支付系统，创建二维码，如果用户支付成功了，微信系统会将支付信息发送给支付系统指定的回调地址，支付系统收到信息后，将信息发送给MQ，第3个步骤就可以监听到消息了。
```



### 3.4 支付回调更新

支付回调这一块代码已经实现了，但之前实现的是订单信息的回调数据发送给MQ，指定了对应的队列，不过现在需要实现的是秒杀信息发送给指定队列，所以之前的代码那块需要动态指定队列。



#### 3.4.1 支付回调队列指定

关于指定队列如下：

```properties
1.创建支付二维码需要指定队列
2.回调地址回调的时候，获取支付二维码指定的队列，将支付信息发送到指定队列中
```

在微信支付统一下单API中，有一个附加参数,如下：

```properties
attach:附加数据,String(127)，在查询API和支付通知中原样返回，可作为自定义参数使用。
```

我们可以在创建二维码的时候，指定该参数，该参数用于指定回调支付信息的对应队列，每次回调的时候，会获取该参数，然后将回调信息发送到该参数对应的队列去。



##### 3.4.1.1 改造支付方法

修改支付微服务的WeixinPayController的createNative方法，代码如下：

![1558839099792](https://dylanguo.xyz/img/1558839099792.png)



修改支付微服务的WeixinPayService的createNative方法，代码如下：

![1558839160020](https://dylanguo.xyz/img/1558839160020.png)



修改支付微服务的WeixinPayServiceImpl的createNative方法，代码如下：

![1558839263213](https://dylanguo.xyz/img/1558839263213.png)



我们创建二维码的时候，需要将下面几个参数传递过去

```properties
username:用户名,可以根据用户名查询用户排队信息
outtradeno：商户订单号，下单必须
money：支付金额，支付必须
queue：队列名字，回调的时候，可以知道将支付信息发送到哪个队列
```



修改WeixinPayApplication，添加对应队列以及对应交换机绑定，代码如下：

```java
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class WeixinPayApplication {

    public static void main(String[] args) {
        SpringApplication.run(WeixinPayApplication.class,args);
    }

    @Autowired
    private Environment env;


    /***
     * 创建DirectExchange交换机
     * @return
     */
    @Bean
    public DirectExchange basicExchange(){
        return new DirectExchange(env.getProperty("mq.pay.exchange.order"), true,false);
    }

    /***
     * 创建队列
     * @return
     */
    @Bean(name = "queueOrder")
    public Queue queueOrder(){
        return new Queue(env.getProperty("mq.pay.queue.order"), true);
    }

    /***
     * 创建秒杀队列
     * @return
     */
    @Bean(name = "queueSeckillOrder")
    public Queue queueSeckillOrder(){
        return new Queue(env.getProperty("mq.pay.queue.seckillorder"), true);
    }

    /****
     * 队列绑定到交换机上
     * @return
     */
    @Bean
    public Binding basicBindingOrder(){
        return BindingBuilder
                .bind(queueOrder())
                .to(basicExchange())
                .with(env.getProperty("mq.pay.routing.orderkey"));
    }

    /****
     * 队列绑定到交换机上
     * @return
     */
    @Bean
    public Binding basicBindingSeckillOrder(){
        return BindingBuilder
                .bind(queueSeckillOrder())
                .to(basicExchange())
                .with(env.getProperty("mq.pay.routing.seckillorderkey"));
    }
}
```

修改application.yml，添加如下配置

```properties
#位置支付交换机和队列
mq:
  pay:
    exchange:
      order: exchange.order
      seckillorder: exchange.seckillorder
    queue:
      order: queue.order
      seckillorder: queue.seckillorder
    routing:
      key: queue.order
      seckillkey: queue.seckillorder
```



##### 3.4.1.3 改造支付回调方法

修改com.changgou.pay.controller.WeixinPayController的notifyUrl方法，获取自定义参数，并转成Map，获取queue地址，并将支付信息发送到绑定的queue中，代码如下：

![1567738035562](https://dylanguo.xyz/img/1567738035562.png)



#### 3.4.2 支付状态监听

支付状态通过回调地址发送给MQ之后，我们需要在秒杀系统中监听支付信息，如果用户已支付，则修改用户订单状态，如果支付失败，则直接删除订单，回滚库存。



在秒杀工程中创建com.changgou.seckill.consumer.SeckillOrderPayMessageListener,实现监听消息，代码如下:

```java
@Component
@RabbitListener(queues = "${mq.pay.queue.seckillorder}")
public class SeckillOrderPayMessageListener {


    /**
     * 监听消费消息
     * @param message
     */
    @RabbitHandler
    public void consumeMessage(@Payload String message){
        System.out.println(message);
        //将消息转换成Map对象
        Map<String,String> resultMap = JSON.parseObject(message,Map.class);
        System.out.println("监听到的消息:"+resultMap);
    }
}
```



修改SeckillApplication创建对应的队列以及绑定对应交换机。

```java
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@MapperScan(basePackages = {"com.changgou.seckill.dao"})
@EnableScheduling
@EnableAsync
public class SeckillApplication {


    public static void main(String[] args) {
        SpringApplication.run(SeckillApplication.class,args);
    }

    @Bean
    public IdWorker idWorker(){
        return new IdWorker(1,1);
    }

    @Autowired
    private Environment env;


    /***
     * 创建DirectExchange交换机
     * @return
     */
    @Bean
    public DirectExchange basicExchange(){
        return new DirectExchange(env.getProperty("mq.pay.exchange.order"), true,false);
    }

    /***
     * 创建队列
     * @return
     */
    @Bean(name = "queueOrder")
    public Queue queueOrder(){
        return new Queue(env.getProperty("mq.pay.queue.order"), true);
    }

    /***
     * 创建秒杀队列
     * @return
     */
    @Bean(name = "queueSeckillOrder")
    public Queue queueSeckillOrder(){
        return new Queue(env.getProperty("mq.pay.queue.seckillorder"), true);
    }

    /****
     * 队列绑定到交换机上
     * @return
     */
    @Bean
    public Binding basicBindingOrder(){
        return BindingBuilder
                .bind(queueOrder())
                .to(basicExchange())
                .with(env.getProperty("mq.pay.routing.orderkey"));
    }


    /****
     * 队列绑定到交换机上
     * @return
     */
    @Bean
    public Binding basicBindingSeckillOrder(){
        return BindingBuilder
                .bind(queueSeckillOrder())
                .to(basicExchange())
                .with(env.getProperty("mq.pay.routing.seckillorderkey"));
    }
}
```



修改application.yml文件，添加如下配置：

```properties
#位置支付交换机和队列
mq:
  pay:
    exchange:
      order: exchange.order
      seckillorder: exchange.seckillorder
    queue:
      order: queue.order
      seckillorder: queue.seckillorder
    routing:
      key: queue.order
      seckillkey: queue.seckillorder
```



#### 3.4.3 修改订单状态

监听到支付信息后，根据支付信息判断，如果用户支付成功，则修改订单信息，并将订单入库，删除用户排队信息，如果用户支付失败，则删除订单信息，回滚库存，删除用户排队信息。



##### 3.4.3.1 业务层

修改SeckillOrderService，添加修改订单方法，代码如下

```java
/***
 * 更新订单状态
 * @param out_trade_no
 * @param transaction_id
 * @param username
 */
void updatePayStatus(String out_trade_no, String transaction_id,String username);
```



修改SeckillOrderServiceImpl，添加修改订单方法实现，代码如下：

```java
/***
 * 更新订单状态
 * @param out_trade_no
 * @param transaction_id
 * @param username
 */
@Override
public void updatePayStatus(String out_trade_no, String transaction_id,String username) {
    //订单数据从Redis数据库查询出来
    SeckillOrder seckillOrder = (SeckillOrder) redisTemplate.boundHashOps("SeckillOrder").get(username);
    //修改状态
    seckillOrder.setStatus("1");

    //支付时间
    seckillOrder.setPayTime(new Date());
    //同步到MySQL中
    seckillOrderMapper.insertSelective(seckillOrder);

    //清空Redis缓存
    redisTemplate.boundHashOps("SeckillOrder").delete(username);

    //清空用户排队数据
    redisTemplate.boundHashOps("UserQueueCount").delete(username);

    //删除抢购状态信息
    redisTemplate.boundHashOps("UserQueueStatus").delete(username);
}
```



##### 3.4.3.2 修改订单对接

修改微信支付状态监听的代码，当用户支付成功后，修改订单状态，也就是调用上面的方法，代码如下：

![1558839807871](https://dylanguo.xyz/img/1558839807871.png)



#### 3.4.4 删除订单回滚库存

如果用户支付失败，我们需要删除用户订单数据，并回滚库存。



##### 3.4.4.1 业务层实现

修改SeckillOrderService，创建一个关闭订单方法，代码如下：

```java
/***
 * 关闭订单，回滚库存
 */
void closeOrder(String username);
```



修改SeckillOrderServiceImpl，创建一个关闭订单实现方法，代码如下：

```java
/***
 * 关闭订单，回滚库存
 * @param username
 */
@Override
public void closeOrder(String username) {
    //将消息转换成SeckillStatus
    SeckillStatus seckillStatus = (SeckillStatus) redisTemplate.boundHashOps("UserQueueStatus").get(username);
    //获取Redis中订单信息
    SeckillOrder seckillOrder = (SeckillOrder) redisTemplate.boundHashOps("SeckillOrder").get(username);

    //如果Redis中有订单信息，说明用户未支付
    if(seckillStatus!=null && seckillOrder!=null){
        //删除订单
        redisTemplate.boundHashOps("SeckillOrder").delete(username);
        //回滚库存
        //1)从Redis中获取该商品
        SeckillGoods seckillGoods = (SeckillGoods) redisTemplate.boundHashOps("SeckillGoods_"+seckillStatus.getTime()).get(seckillStatus.getGoodsId());

        //2)如果Redis中没有，则从数据库中加载
        if(seckillGoods==null){
            seckillGoods = seckillGoodsMapper.selectByPrimaryKey(seckillStatus.getGoodsId());
        }

        //3)数量+1  (递增数量+1，队列数量+1)
        Long surplusCount = redisTemplate.boundHashOps("SeckillGoodsCount").increment(seckillStatus.getGoodsId(), 1);
        seckillGoods.setStockCount(surplusCount.intValue());
        redisTemplate.boundListOps("SeckillGoodsCountList_" + seckillStatus.getGoodsId()).leftPush(seckillStatus.getGoodsId());

        //4)数据同步到Redis中
        redisTemplate.boundHashOps("SeckillGoods_"+seckillStatus.getTime()).put(seckillStatus.getGoodsId(),seckillGoods);

        //清理排队标示
        redisTemplate.boundHashOps("UserQueueCount").delete(seckillStatus.getUsername());

        //清理抢单标示
        redisTemplate.boundHashOps("UserQueueStatus").delete(seckillStatus.getUsername());
    }
}
```



##### 3.4.4.2 调用删除订单

修改SeckillOrderPayMessageListener，在用户支付失败后调用关闭订单方法，代码如下：

```java
//支付失败,删除订单
seckillOrderService.closeOrder(attachMap.get("username"));
```



##### 3.4.4.3 测试

使用Postman完整请求创建二维码下单测试一次。

商品ID：1131814854034853888

数量：49

![1558851734898](https://dylanguo.xyz/img/1558851734898.png)

下单：

http://localhost:18084/seckill/order/add?id=1131814854034853888&time=2019052614

下单后，Redis数据

![1558851403264](https://dylanguo.xyz/img/1558851403264.png)



下单查询：

http://localhost:18084/seckill/order/query



创建二维码：

http://localhost:9022/weixin/pay/create/native?username=szitheima&outtradeno=1132530879663575040&money=1&queue=queue.seckillorder



秒杀抢单后，商品数量变化：

![1558851165865](https://dylanguo.xyz/img/1558851165865.png)



支付微服务回调方法控制台：

```json
{
	nonce_str=Mnv06RIaIwxzg3bA, 
    code_url=weixin://wxpay/bizpayurl?pr=iTidd5h, 
    appid=wx8397f8696b538317, 
    sign=1436E43FBA8A171D79A9B78B61F0A7AB, 
    trade_type=NATIVE, 
    return_msg=OK, 
    result_code=SUCCESS, 
    mch_id=1473426802, 
    return_code=SUCCESS, 
    prepay_id=wx2614182102123859e3869a853739004200
}
{money=1, queue=queue.seckillorder, username=szitheima, outtradeno=1132530879663575040}
```



订单微服务控制台输出

```json
{
    transaction_id=4200000289201905268232990890,
    nonce_str=a1aefe00a9bc4e8bb66a892dba38eb42,
    bank_type=CMB_CREDIT,
    openid=oNpSGwUp-194-Svy3JnVlAxtdLkc,
    sign=56679BC02CC82204635434817C1FCA46,
    fee_type=CNY,
    mch_id=1473426802,
    cash_fee=1,
    out_trade_no=1132530879663575040,
    appid=wx8397f8696b538317,
    total_fee=1,
    trade_type=NATIVE,
    result_code=SUCCESS,
    attach={
    "username": "szitheima",
    "outtradeno": "1132530879663575040",
    "money": "1",
    "queue": "queue.seckillorder"
  }, time_end=20190526141849, is_subscribe=N, return_code=SUCCESS
}
```

附录：

支付微服务application.yml

```properties
server:
  port: 9022
spring:
  application:
    name: pay
  main:
    allow-bean-definition-overriding: true
  rabbitmq:
    host: 127.0.0.1 #mq的服务器地址
    username: guest #账号
    password: guest #密码
eureka:
  client:
    service-url:
      defaultZone: http://127.0.0.1:6868/eureka
  instance:
    prefer-ip-address: true
feign:
  hystrix:
    enabled: true
#hystrix 配置
hystrix:
  command:
    default:
      execution:
        timeout:
        #如果enabled设置为false，则请求超时交给ribbon控制
          enabled: true
        isolation:
          strategy: SEMAPHORE

#微信支付信息配置
weixin:
  appid: wx8397f8696b538317
  partner: 1473426802
  partnerkey: T6m9iK73b0kn9g5v426MKfHQH7X8rKwb
  notifyurl: http://2cw4969042.wicp.vip:36446/weixin/pay/notify/url

#位置支付交换机和队列
mq:
  pay:
    exchange:
      order: exchange.order
    queue:
      order: queue.order
      seckillorder: queue.seckillorder
    routing:
      orderkey: queue.order
      seckillorderkey: queue.seckillorder
```



秒杀微服务application.yml配置

```properties
server:
  port: 18084
spring:
  application:
    name: seckill
  datasource:
    driver-class-name: com.mysql.jdbc.Driver
    url: jdbc:mysql://127.0.0.1:3306/changgou_seckill?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC
    username: root
    password: itcast
  rabbitmq:
    host: 127.0.0.1 #mq的服务器地址
    username: guest #账号
    password: guest #密码
  main:
    allow-bean-definition-overriding: true
eureka:
  client:
    service-url:
      defaultZone: http://127.0.0.1:6868/eureka
  instance:
    prefer-ip-address: true
feign:
  hystrix:
    enabled: true
mybatis:
  configuration:
    map-underscore-to-camel-case: true
  mapper-locations: classpath:mapper/*Mapper.xml
  type-aliases-package: com.changgou.seckill.pojo

#hystrix 配置
hystrix:
  command:
    default:
      execution:
        timeout:
        #如果enabled设置为false，则请求超时交给ribbon控制
          enabled: true
        isolation:
          thread:
            timeoutInMilliseconds: 10000
          strategy: SEMAPHORE
#位置支付交换机和队列
mq:
  pay:
    exchange:
      order: exchange.order
    queue:
      order: queue.order
      seckillorder: queue.seckillorder
    routing:
      orderkey: queue.order
      seckillorderkey: queue.seckillorder
```



## 4 RabbitMQ延时消息队列

### 4.1 延时队列介绍

延时队列即放置在该队列里面的消息是不需要立即消费的，而是等待一段时间之后取出消费。
那么，为什么需要延迟消费呢？我们来看以下的场景

网上商城下订单后30分钟后没有完成支付，取消订单(如：淘宝、去哪儿网)
系统创建了预约之后，需要在预约时间到达前一小时提醒被预约的双方参会
系统中的业务失败之后，需要重试
这些场景都非常常见，我们可以思考，比如第二个需求，系统创建了预约之后，需要在预约时间到达前一小时提醒被预约的双方参会。那么一天之中肯定是会有很多个预约的，时间也是不一定的，假设现在有1点 2点 3点 三个预约，如何让系统知道在当前时间等于0点 1点 2点给用户发送信息呢，是不是需要一个轮询，一直去查看所有的预约，比对当前的系统时间和预约提前一小时的时间是否相等呢？这样做非常浪费资源而且轮询的时间间隔不好控制。如果我们使用延时消息队列呢，我们在创建时把需要通知的预约放入消息中间件中，并且设置该消息的过期时间，等过期时间到达时再取出消费即可。

Rabbitmq实现延时队列一般而言有两种形式：
第一种方式：利用两个特性： Time To Live(TTL)、Dead Letter Exchanges（DLX）[A队列过期->转发给B队列]

第二种方式：利用rabbitmq中的插件x-delay-message



### 4.2 TTL DLX实现延时队列

#### 4.2.1 TTL DLX介绍

**TTL**
RabbitMQ可以针对队列设置x-expires(则队列中所有的消息都有相同的过期时间)或者针对Message设置x-message-ttl(对消息进行单独设置，每条消息TTL可以不同)，来控制消息的生存时间，如果超时(两者同时设置以最先到期的时间为准)，则消息变为dead letter(死信)

**Dead Letter Exchanges（DLX）**
RabbitMQ的Queue可以配置x-dead-letter-exchange和x-dead-letter-routing-key（可选）两个参数，如果队列内出现了dead letter，则按照这两个参数重新路由转发到指定的队列。
x-dead-letter-exchange：出现dead letter之后将dead letter重新发送到指定exchange

x-dead-letter-routing-key：出现dead letter之后将dead letter重新按照指定的routing-key发送

![1557396863944](https://dylanguo.xyz/img/1557396863944.png)



#### 4.2.3 DLX延时队列实现

##### 4.2.3.1 创建工程

创建springboot_rabbitmq_delay工程，并引入相关依赖

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>changgou_parent</artifactId>
        <groupId>com.changgou</groupId>
        <version>1.0-SNAPSHOT</version>
    </parent>
    <modelVersion>4.0.0</modelVersion>
    <artifactId>springboot_rabbitmq_delay</artifactId>

    <dependencies>
        <!--starter-web-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <!--加入ampq-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-amqp</artifactId>
        </dependency>

        <!--测试-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
        </dependency>
    </dependencies>
</project>
```



application.yml配置

```properties
spring:
  application:
    name: springboot-demo
  rabbitmq:
    host: 127.0.0.1
    port: 5672
    password: guest
    username: guest
```



##### 4.2.3.2 队列创建

创建2个队列，用于接收消息的叫延时队列queue.message.delay，用于转发消息的队列叫queue.message，同时创建一个交换机，代码如下：

```java
@Configuration
public class QueueConfig {

    /** 短信发送队列 */
    public static final String QUEUE_MESSAGE = "queue.message";

    /** 交换机 */
    public static final String DLX_EXCHANGE = "dlx.exchange";

    /** 短信发送队列 延迟缓冲（按消息） */
    public static final String QUEUE_MESSAGE_DELAY = "queue.message.delay";

    /**
     * 短信发送队列
     * @return
     */
    @Bean
    public Queue messageQueue() {
        return new Queue(QUEUE_MESSAGE, true);
    }

    /**
     * 短信发送队列
     * @return
     */
    @Bean
    public Queue delayMessageQueue() {
        return QueueBuilder.durable(QUEUE_MESSAGE_DELAY)
                .withArgument("x-dead-letter-exchange", DLX_EXCHANGE)        // 消息超时进入死信队列，绑定死信队列交换机
                .withArgument("x-dead-letter-routing-key", QUEUE_MESSAGE)   // 绑定指定的routing-key
                .build();
    }

    /***
     * 创建交换机
     * @return
     */
    @Bean
    public DirectExchange directExchange(){
        return new DirectExchange(DLX_EXCHANGE);
    }


    /***
     * 交换机与队列绑定
     * @param messageQueue
     * @param directExchange
     * @return
     */
    @Bean
    public Binding basicBinding(Queue messageQueue, DirectExchange directExchange) {
        return BindingBuilder.bind(messageQueue)
                .to(directExchange)
                .with(QUEUE_MESSAGE);
    }
}
```



##### 4.2.3.3 消息监听

创建MessageListener用于监听消息，代码如下：

```java
@Component
@RabbitListener(queues = QueueConfig.QUEUE_MESSAGE)
public class MessageListener {


    /***
     * 监听消息
     * @param msg
     */
    @RabbitHandler
    public void msg(@Payload Object msg){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("当前时间:"+dateFormat.format(new Date()));
        System.out.println("收到信息:"+msg);
    }

}
```



##### 4.2.3.4 创建启动类

```java
@SpringBootApplication
@EnableRabbit
public class SpringRabbitMQApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringRabbitMQApplication.class,args);
    }
}
```



##### 4.2.3.5 测试

```java
@SpringBootTest
@RunWith(SpringRunner.class)
public class RabbitMQTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /***
     * 发送消息
     */
    @Test
    public void sendMessage() throws InterruptedException, IOException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("发送当前时间:"+dateFormat.format(new Date()));
        Map<String,String> message = new HashMap<>();
        message.put("name","szitheima");
        rabbitTemplate.convertAndSend(QueueConfig.QUEUE_MESSAGE_DELAY, message, new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                message.getMessageProperties().setExpiration("10000");
                return message;
            }
        });

        System.in.read();
    }
}
```

其中message.getMessageProperties().setExpiration("10000");设置消息超时时间,超时后，会将消息转入到另外一个队列。



## 5 库存回滚

### 5.1 秒杀流程回顾

![1558832314454](https://dylanguo.xyz/img/1558832314454.png)

如上图，步骤分析如下：

```properties
1.用户抢单，经过秒杀系统实现抢单，下单后会将向MQ发送一个延时队列消息，包含抢单信息，延时半小时后才能监听到
2.秒杀系统同时启用延时消息监听，一旦监听到订单抢单信息，判断Redis缓存中是否存在订单信息，如果存在，则回滚
3.秒杀系统还启动支付回调信息监听，如果支付完成，则将订单吃句话到MySQL，如果没完成，清理排队信息回滚库存
4.每次秒杀下单后调用支付系统，创建二维码，如果用户支付成功了，微信系统会将支付信息发送给支付系统指定的回调地址，支付系统收到信息后，将信息发送给MQ，第3个步骤就可以监听到消息了。
```



延时队列实现订单关闭回滚库存：

```
1.创建一个过期队列  Queue1
2.接收消息的队列    Queue2
3.中转交换机
4.监听Queue2
	1)SeckillStatus->检查Redis中是否有订单信息
	2)如果有订单信息，调用删除订单回滚库存->[需要先关闭微信支付]
	3)如果关闭订单时，用于已支付，修改订单状态即可
	4)如果关闭订单时，发生了别的错误，记录日志，人工处理
```



### 5.2 关闭支付

用户如果半个小时没有支付，我们会关闭支付订单，但在关闭之前，需要先关闭微信支付，防止中途用户支付。

修改支付微服务的WeixinPayService，添加关闭支付方法，代码如下：

```java
/***
 * 关闭支付
 * @param orderId
 * @return
 */
Map<String,String> closePay(Long orderId) throws Exception;
```



修改WeixinPayServiceImpl，实现关闭微信支付方法，代码如下：

```java
/***
 * 关闭微信支付
 * @param orderId
 * @return
 * @throws Exception
 */
@Override
public Map<String, String> closePay(Long orderId) throws Exception {
    //参数设置
    Map<String,String> paramMap = new HashMap<String,String>();
    paramMap.put("appid",appid); //应用ID
    paramMap.put("mch_id",partner);    //商户编号
    paramMap.put("nonce_str",WXPayUtil.generateNonceStr());//随机字符
    paramMap.put("out_trade_no",String.valueOf(orderId));   //商家的唯一编号

    //将Map数据转成XML字符
    String xmlParam = WXPayUtil.generateSignedXml(paramMap,partnerkey);

    //确定url
    String url = "https://api.mch.weixin.qq.com/pay/closeorder";

    //发送请求
    HttpClient httpClient = new HttpClient(url);
    //https
    httpClient.setHttps(true);
    //提交参数
    httpClient.setXmlParam(xmlParam);

    //提交
    httpClient.post();

    //获取返回数据
    String content = httpClient.getContent();

    //将返回数据解析成Map
    return  WXPayUtil.xmlToMap(content);
}
```



### 5.3 关闭订单回滚库存

#### 5.3.1 配置延时队列

在application.yml文件中引入队列信息配置，如下：

```properties
#位置支付交换机和队列
mq:
  pay:
    exchange:
      order: exchange.order
    queue:
      order: queue.order
      seckillorder: queue.seckillorder
      seckillordertimer: queue.seckillordertimer
      seckillordertimerdelay: queue.seckillordertimerdelay
    routing:
      orderkey: queue.order
      seckillorderkey: queue.seckillorder
```



配置队列与交换机,在SeckillApplication中添加如下方法

```java
/**
 * 到期数据队列
 * @return
 */
@Bean
public Queue seckillOrderTimerQueue() {
    return new Queue(env.getProperty("mq.pay.queue.seckillordertimer"), true);
}

/**
 * 超时数据队列
 * @return
 */
@Bean
public Queue delaySeckillOrderTimerQueue() {
    return QueueBuilder.durable(env.getProperty("mq.pay.queue.seckillordertimerdelay"))
            .withArgument("x-dead-letter-exchange", env.getProperty("mq.pay.exchange.order"))        // 消息超时进入死信队列，绑定死信队列交换机
            .withArgument("x-dead-letter-routing-key", env.getProperty("mq.pay.queue.seckillordertimer"))   // 绑定指定的routing-key
            .build();
}

/***
 * 交换机与队列绑定
 * @return
 */
@Bean
public Binding basicBinding() {
    return BindingBuilder.bind(seckillOrderTimerQueue())
            .to(basicExchange())
            .with(env.getProperty("mq.pay.queue.seckillordertimer"));
}
```



#### 5.3.2 发送延时消息

修改MultiThreadingCreateOrder，添加如下方法：

```java
/***
 * 发送延时消息到RabbitMQ中
 * @param seckillStatus
 */
public void sendTimerMessage(SeckillStatus seckillStatus){
    rabbitTemplate.convertAndSend(env.getProperty("mq.pay.queue.seckillordertimerdelay"), (Object) JSON.toJSONString(seckillStatus), new MessagePostProcessor() {
        @Override
        public Message postProcessMessage(Message message) throws AmqpException {
            message.getMessageProperties().setExpiration("10000");
            return message;
        }
    });
}
```

在createOrder方法中调用上面方法，如下代码：

```java
//发送延时消息到MQ中
sendTimerMessage(seckillStatus);
```



#### 5.3.3 库存回滚

创建SeckillOrderDelayMessageListener实现监听消息，并回滚库存，代码如下：

```java
@Component
@RabbitListener(queues = "${mq.pay.queue.seckillordertimer}")
public class SeckillOrderDelayMessageListener {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private SeckillOrderService seckillOrderService;

    @Autowired
    private WeixinPayFeign weixinPayFeign;

    /***
     * 读取消息
     * 判断Redis中是否存在对应的订单
     * 如果存在，则关闭支付，再关闭订单
     * @param message
     */
    @RabbitHandler
    public void consumeMessage(@Payload String message){
        //读取消息
        SeckillStatus seckillStatus = JSON.parseObject(message,SeckillStatus.class);

        //获取Redis中订单信息
        String username = seckillStatus.getUsername();
        SeckillOrder seckillOrder = (SeckillOrder) redisTemplate.boundHashOps("SeckillOrder").get(username);

        //如果Redis中有订单信息，说明用户未支付
        if(seckillOrder!=null){
            System.out.println("准备回滚---"+seckillStatus);
            //关闭支付
            Result closeResult = weixinPayFeign.closePay(seckillStatus.getOrderId());
            Map<String,String> closeMap = (Map<String, String>) closeResult.getData();

            if(closeMap!=null && closeMap.get("return_code").equalsIgnoreCase("success") &&
                    closeMap.get("result_code").equalsIgnoreCase("success") ){
                //关闭订单
                seckillOrderService.closeOrder(username);
            }
        }
    }
}
```