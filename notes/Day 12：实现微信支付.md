# 第12章 微信支付

## 学习目标

- **能够说出微信支付开发的整体思路**

- **生成支付二维码**

- **查询支付状态**

- 实现支付日志的生成与订单状态的修改、删除订单

- **支付状态回查**

- MQ处理支付回调状态

- 定时处理订单状态



## 1 微信支付二维码生成

### 1.1需求分析与实现思路

在支付页面上生成支付二维码，并显示订单号和金额

用户拿出手机,打开微信扫描页面上的二维码,然后在微信中完成支付

![1558475798785](https://dylanguo.xyz/img/1558475798785.png)



### 1.2 实现思路

我们通过 HttpClient 工具类实现对远程支付接口的调用。

接口链接：https://api.mch.weixin.qq.com/pay/unifiedorder

具体参数参见“统一下单”API, 构建参数发送给统一下单的url ，返回的信息中有支付url，根据url生成二维码，显示的订单号和金额也在返回的信息中。

 

### 2.3 代码实现

(1)业务层

新增`com.changgou.service.WeixinPayService`接口，代码如下：

```java
public interface WeixinPayService {
    /*****
     * 创建二维码
     * @param out_trade_no : 客户端自定义订单编号
     * @param total_fee    : 交易金额,单位：分
     * @return
     */
    public Map createNative(String out_trade_no, String total_fee);
}
```



创建`com.changgou.service.impl.WeixinPayServiceImpl`类,并发送Post请求获取预支付信息，包含二维码扫码支付地址。代码如下：

```java
@Service
public class WeixinPayServiceImpl implements WeixinPayService {

    @Value("${weixin.appid}")
    private String appid;

    @Value("${weixin.partner}")
    private String partner;

    @Value("${weixin.partnerkey}")
    private String partnerkey;

    @Value("${weixin.notifyurl}")
    private String notifyurl;

    /****
     * 创建二维码
     * @param out_trade_no : 客户端自定义订单编号
     * @param total_fee    : 交易金额,单位：分
     * @return
     */
    @Override
    public Map createNative(String out_trade_no, String total_fee){
        try {
            //1、封装参数
            Map param = new HashMap();
            param.put("appid", appid);                              //应用ID
            param.put("mch_id", partner);                           //商户ID号
            param.put("nonce_str", WXPayUtil.generateNonceStr());   //随机数
            param.put("body", "畅购");                            	//订单描述
            param.put("out_trade_no",out_trade_no);                 //商户订单号
            param.put("total_fee", total_fee);                      //交易金额
            param.put("spbill_create_ip", "127.0.0.1");           //终端IP
            param.put("notify_url", notifyurl);                    //回调地址
            param.put("trade_type", "NATIVE");                     //交易类型

            //2、将参数转成xml字符，并携带签名
            String paramXml = WXPayUtil.generateSignedXml(param, partnerkey);

            ///3、执行请求
            HttpClient httpClient = new HttpClient("https://api.mch.weixin.qq.com/pay/unifiedorder");
            httpClient.setHttps(true);
            httpClient.setXmlParam(paramXml);
            httpClient.post();

            //4、获取参数
            String content = httpClient.getContent();
            Map<String, String> stringMap = WXPayUtil.xmlToMap(content);
            System.out.println("stringMap:"+stringMap);

            //5、获取部分页面所需参数
            Map<String,String> dataMap = new HashMap<String,String>();
            dataMap.put("code_url",stringMap.get("code_url"));
            dataMap.put("out_trade_no",out_trade_no);
            dataMap.put("total_fee",total_fee);

            return dataMap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
```



(2) 控制层

创建`com.changgou.controller.WeixinPayController`,主要调用WeixinPayService的方法获取创建二维码的信息，代码如下：

```java
@RestController
@RequestMapping(value = "/weixin/pay")
@CrossOrigin
public class WeixinPayController {

    @Autowired
    private WeixinPayService weixinPayService;

    /***
     * 创建二维码
     * @return
     */
    @RequestMapping(value = "/create/native")
    public Result createNative(String outtradeno, String money){
        Map<String,String> resultMap = weixinPayService.createNative(outtradeno,money);
        return new Result(true, StatusCode.OK,"创建二维码预付订单成功！",resultMap);
    }
}
```

这里我们订单号通过随机数生成，金额暂时写死，后续开发我们再对接业务系统得到订单号和金额

Postman测试`http://localhost:18092/weixin/pay/create/native?outtradeno=No000000001&money=1`

![1563241115226](https://dylanguo.xyz/img/1563241115226.png)



打开支付页面/pay.html，修改value路径，然后打开，会出现二维码，可以扫码试试

![1558476420961](https://dylanguo.xyz/img/1558476420961.png)



## 2 检测支付状态

### 2.1 需求分析

当用户支付成功后跳转到成功页面

![1558476664135](https://dylanguo.xyz/img/1558476664135.png)



当返回异常时跳转到错误页面

![1558476701485](https://dylanguo.xyz/img/1558476701485.png)



### 2.2 实现思路

我们通过HttpClient工具类实现对远程支付接口的调用。

接口链接：https://api.mch.weixin.qq.com/pay/orderquery

具体参数参见“查询订单”API, 我们在controller方法中轮询调用查询订单（间隔3秒），当返回状态为success时，我们会在controller方法返回结果。前端代码收到结果后跳转到成功页面。



### 2.3 代码实现

(1)业务层

修改`com.changgou.service.WeixinPayService`，新增方法定义

```java
/***
 * 查询订单状态
 * @param out_trade_no : 客户端自定义订单编号
 * @return
 */
public Map queryPayStatus(String out_trade_no);
```



在 com.changgou.pay.service.impl.WeixinPayServiceImpl 中增加实现方法

```java
/***
 * 查询订单状态
 * @param out_trade_no : 客户端自定义订单编号
 * @return
 */
@Override
public Map queryPayStatus(String out_trade_no) {
    try {
        //1.封装参数
        Map param = new HashMap();
        param.put("appid",appid);                            //应用ID
        param.put("mch_id",partner);                         //商户号
        param.put("out_trade_no",out_trade_no);              //商户订单编号
        param.put("nonce_str",WXPayUtil.generateNonceStr()); //随机字符

        //2、将参数转成xml字符，并携带签名
        String paramXml = WXPayUtil.generateSignedXml(param,partnerkey);

        //3、发送请求
        HttpClient httpClient = new HttpClient("https://api.mch.weixin.qq.com/pay/orderquery");
        httpClient.setHttps(true);
        httpClient.setXmlParam(paramXml);
        httpClient.post();

        //4、获取返回值，并将返回值转成Map
        String content = httpClient.getContent();
        return WXPayUtil.xmlToMap(content);
    } catch (Exception e) {
        e.printStackTrace();
    }
    return null;
}
```



(2)控制层

在`com.changgou.controller.WeixinPayController`新增方法，用于查询支付状态，代码如下：

上图代码如下：

```java
/***
 * 查询支付状态
 * @param outtradeno
 * @return
 */
@GetMapping(value = "/status/query")
public Result queryStatus(String outtradeno){
    Map<String,String> resultMap = weixinPayService.queryPayStatus(outtradeno);
    return new Result(true,StatusCode.OK,"查询状态成功！",resultMap);
}
```



## 3 订单状态操作准备工作

### 3.1 需求分析

![1558490059984](https://dylanguo.xyz/img/1558490059984.png)

我们现在系统还有个问题需要解决：支付后订单状态没有改变



流程回顾：

```properties
1.用户下单之后，订单数据会存入到MySQL中，同时会将订单对应的支付日志存入到Redis，以List+Hash的方式存储。
2.用户下单后，进入支付页面，支付页面调用支付系统，从微信支付获取二维码数据，并在页面生成支付二维码。
3.用户扫码支付后，微信支付服务器会通调用前预留的回调地址，并携带支付状态信息。
4.支付系统接到支付状态信息后，将支付状态信息发送给RabbitMQ
5.订单系统监听RabbitMQ中的消息获取支付状态，并根据支付状态修改订单状态
6.为了防止网络问题导致notifyurl没有接到对应数据，定时任务定时获取Redis中队列数据去微信支付接口查询状态，并定时更新对应状态。
```

需要做的工作：

```properties
1.创建订单时，同时将订单信息放到Redis中，以List和Hash各存一份
2.实现回调地址接收支付状态信息
3.将订单支付状态信息发送给RabbitMQ
4.订单系统中监听支付状态信息，如果是支付成功，修改订单状态，如果是支付失败，删除订单(或者改成支付失败)
5.防止网络异常无法接收到回调地址的支付信息，定时任务从Redis List中读取数据判断是否支付，如果支付了，修改订单状态，如果未支付，将支付信息放入队列，下次再检测，如果支付失败删除订单(或者改成支付失败)。
```



### 3.2 Redis存储订单信息

每次添加订单后，会根据订单检查用户是否是否支付成功，我们不建议每次都操作数据库，每次操作数据库会增加数据库的负载，我们可以选择将用户的订单信息存入一份到Redis中，提升读取速度。

修改`changgou-service-order`微服务的`com.changgou.order.service.impl.OrderServiceImpl`类中的`add`方法，如果是线上支付，将用户订单数据存入到Redis中,由于每次创建二维码，需要用到订单编号 ，所以也需要将添加的订单信息返回。

```java
/**
 * 增加Order
 * 金额校验:后台校验
 * @param order
 */
@Override
public Order add(Order order){
    //...略

    //修改库存
    skuFeign.decrCount(order.getUsername());

    //添加用户积分
    userFeign.addPoints(2);

    //线上支付，记录订单
    if(order.getPayType().equalsIgnoreCase("1")){
        //将支付记录存入到Reids namespace  key  value
        redisTemplate.boundHashOps("Order").put(order.getId(),order);
    }

    //删除购物车信息
    //redisTemplate.delete("Cart_" + order.getUsername());

    return order;
}
```



修改`com.changgou.order.controller.OrderController`的add方法，将订单对象返回，因为页面需要获取订单的金额和订单号用于创建二维码，代码如下：

![1566107006010](https://dylanguo.xyz/img/1566107006010.png)



### 3.3 修改订单状态

订单支付成功后，需要修改订单状态并持久化到数据库，修改订单的同时，需要将Redis中的订单删除，所以修改订单状态需要将订单日志也传过来，实现代码如下：

修改com.changgou.order.service.OrderService，添加修改订单状态方法，代码如下：

```java
/***
 * 根据订单ID修改订单状态
 * @param transactionid 交易流水号
 * @param orderId
 */
void updateStatus(String orderId,String transactionid);
```



修改com.changgou.order.service.impl.OrderServiceImpl，添加修改订单状态实现方法，代码如下：

```java
/***
 * 订单修改
 * @param orderId
 * @param transactionid  微信支付的交易流水号
 */
@Override
public void updateStatus(String orderId,String transactionid) {
    //1.修改订单
    Order order = orderMapper.selectByPrimaryKey(orderId);
    order.setUpdateTime(new Date());    //时间也可以从微信接口返回过来，这里为了方便，我们就直接使用当前时间了
    order.setPayTime(order.getUpdateTime());    //不允许这么写
    order.setTransactionId(transactionid);  //交易流水号
    order.setPayStatus("1");    //已支付
    orderMapper.updateByPrimaryKeySelective(order);

    //2.删除Redis中的订单记录
    redisTemplate.boundHashOps("Order").delete(orderId);
}
```



### 3.4 删除订单

如果用户订单支付失败了，或者支付超时了，我们需要删除用户订单，删除订单的同时需要回滚库存，这里回滚库存我们就不实现了，作为同学们的作业。实现如下：

修改`changgou-service-order`的com.changgou.order.service.OrderService，添加删除订单方法，我们只需要将订单id传入进来即可实现，代码如下：

```java
/***
 * 删除订单操作
 * @param id
 */
void deleteOrder(String id);
```



修改`changgou-service-order`的com.changgou.order.service.impl.OrderServiceImpl，添加删除订单实现方法，代码如下：

```java
/***
 * 订单的删除操作
 */
@Override
public void deleteOrder(String id) {
    //改状态
    Order order = (Order) redisTemplate.boundHashOps("Order").get(id);
    order.setUpdateTime(new Date());
    order.setPayStatus("2");    //支付失败
    orderMapper.updateByPrimaryKeySelective(order);

    //删除缓存
    redisTemplate.boundHashOps("Order").delete(id);
}
```



## 4 支付信息回调

### 4.1 接口分析

每次实现支付之后，微信支付都会将用户支付结果返回到指定路径，而指定路径是指创建二维码的时候填写的`notifyurl`参数,响应的数据以及相关文档参考一下地址：`https://pay.weixin.qq.com/wiki/doc/api/native.php?chapter=9_7&index=8`



#### 4.1.1 返回参数分析

通知参数如下：

| 字段名     | 变量名      | 必填 | 类型        | 示例值  | 描述    |
| :--------- | :---------- | :--- | :---------- | :------ | :------ |
| 返回状态码 | return_code | 是   | String(16)  | SUCCESS | SUCCESS |
| 返回信息   | return_msg  | 是   | String(128) | OK      | OK      |

以下字段在return_code为SUCCESS的时候有返回

| 字段名         | 变量名         | 必填 | 类型       | 示例值                       | 描述                                            |
| :------------- | :------------- | :--- | :--------- | :--------------------------- | :---------------------------------------------- |
| 公众账号ID     | appid          | 是   | String(32) | wx8888888888888888           | 微信分配的公众账号ID（企业号corpid即为此appId） |
| 业务结果       | result_code    | 是   | String(16) | SUCCESS                      | SUCCESS/FAIL                                    |
| 商户订单号     | out_trade_no   | 是   | String(32) | 1212321211201407033568112322 | 商户系统内部订单号                              |
| 微信支付订单号 | transaction_id | 是   | String(32) | 1217752501201407033233368018 | 微信支付订单号                                  |



#### 4.1.2 响应分析

回调地址接收到数据后，需要响应信息给微信服务器，告知已经收到数据，不然微信服务器会再次发送4次请求推送支付信息。

| 字段名     | 变量名      | 必填 | 类型        | 示例值  | 描述           |
| :--------- | :---------- | :--- | :---------- | :------ | :------------- |
| 返回状态码 | return_code | 是   | String(16)  | SUCCESS | 请按示例值填写 |
| 返回信息   | return_msg  | 是   | String(128) | OK      | 请按示例值填写 |

举例如下：

```xml
<xml>
  <return_code><![CDATA[SUCCESS]]></return_code>
  <return_msg><![CDATA[OK]]></return_msg>
</xml>
```



### 4.2 回调接收数据实现

修改`changgou-service-pay`微服务的com.changgou.pay.controller.WeixinPayController,添加回调方法，代码如下：

```java
/***
 * 支付回调
 * @param request
 * @return
 */
@RequestMapping(value = "/notify/url")
public String notifyUrl(HttpServletRequest request){
    InputStream inStream;
    try {
        //读取支付回调数据
        inStream = request.getInputStream();
        ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outSteam.write(buffer, 0, len);
        }
        outSteam.close();
        inStream.close();
        // 将支付回调数据转换成xml字符串
        String result = new String(outSteam.toByteArray(), "utf-8");
        //将xml字符串转换成Map结构
        Map<String, String> map = WXPayUtil.xmlToMap(result);

        //响应数据设置
        Map respMap = new HashMap();
        respMap.put("return_code","SUCCESS");
        respMap.put("return_msg","OK");
        return WXPayUtil.mapToXml(respMap);
    } catch (Exception e) {
        e.printStackTrace();
        //记录错误日志
    }
    return null;
}
```



## 5 MQ处理支付回调状态

### 5.1 业务分析

![1558490059984](https://dylanguo.xyz/img/1558490059984.png)

支付系统是独立于其他系统的服务，不做相关业务逻辑操作，只做支付处理，所以回调地址接收微信服务返回的支付状态后，立即将消息发送给RabbitMQ，订单系统再监听支付状态数据，根据状态数据做出修改订单状态或者删除订单操作。



### 5.2 发送支付状态

(1)集成RabbitMQ

修改支付微服务，集成RabbitMQ，添加如下依赖：

```xml
<!--加入ampq-->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-amqp</artifactId>
</dependency>
```



这里我们建议在后台手动创建队列，并绑定队列。如果使用程序创建队列，可以按照如下方式实现。

修改application.yml，配置支付队列和交换机信息，代码如下：

```properties
#位置支付交换机和队列
mq:
  pay:
    exchange:
      order: exchange.order
    queue:
      order: queue.order
    routing:
      key: queue.order
```



创建队列以及交换机并让队列和交换机绑定，修改com.changgou.WeixinPayApplication,添加如下代码：

```java
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

/****
 * 队列绑定到交换机上
 * @return
 */
@Bean
public Binding basicBinding(){
    return BindingBuilder.bind(queueOrder()).to(basicExchange()).with(env.getProperty("mq.pay.routing.key"));
}
```



#### 5.2.2 发送MQ消息

修改回调方法，在接到支付信息后，立即将支付信息发送给RabbitMQ，代码如下：

```java
@Value("${mq.pay.exchange.order}")
private String exchange;
@Value("${mq.pay.queue.order}")
private String queue;
@Value("${mq.pay.routing.key}")
private String routing;

@Autowired
private WeixinPayService weixinPayService;

@Autowired
private RabbitTemplate rabbitTemplate;

/***
 * 支付回调
 * @param request
 * @return
 */
@RequestMapping(value = "/notify/url")
public String notifyUrl(HttpServletRequest request){
    InputStream inStream;
    try {
        //读取支付回调数据
        inStream = request.getInputStream();
        ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outSteam.write(buffer, 0, len);
        }
        outSteam.close();
        inStream.close();
        // 将支付回调数据转换成xml字符串
        String result = new String(outSteam.toByteArray(), "utf-8");
        //将xml字符串转换成Map结构
        Map<String, String> map = WXPayUtil.xmlToMap(result);
        //将消息发送给RabbitMQ
        rabbitTemplate.convertAndSend(exchange,routing, JSON.toJSONString(map));

        //响应数据设置
        Map respMap = new HashMap();
        respMap.put("return_code","SUCCESS");
        respMap.put("return_msg","OK");
        return WXPayUtil.mapToXml(respMap);
    } catch (Exception e) {
        e.printStackTrace();
        //记录错误日志
    }
    return null;
}
```



### 5.3 监听MQ消息处理订单

在订单微服务中，我们需要监听MQ支付状态消息，并实现订单数据操作。

#### 5.3.1 集成RabbitMQ

在订单微服务中，先集成RabbitMQ，再监听队列消息。

在pom.xml中引入如下依赖：

```xml
<!--加入ampq-->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-amqp</artifactId>
</dependency>
```



在application.yml中配置rabbitmq配置，代码如下：

```properties
#位置支付交换机和队列
mq:
  pay:
    queue:
      order: queue.order
```



#### 5.3.2 监听消息修改订单

在订单微服务于中创建com.changgou.order.consumer.OrderPayMessageListener，并在该类中consumeMessage方法，用于监听消息，并根据支付状态处理订单，代码如下：

```java
@Component
@RabbitListener(queues = {"${mq.pay.queue.order}"})
public class OrderPayMessageListener {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private OrderService orderService;

    /***
     * 接收消息
     */
    @RabbitHandler
    public void consumeMessage(String msg){
        //将数据转成Map
        Map<String,String> result = JSON.parseObject(msg,Map.class);

        //return_code=SUCCESS
        String return_code = result.get("return_code");
        //业务结果
        String result_code = result.get("result_code");

        //业务结果 result_code=SUCCESS/FAIL，修改订单状态
        if(return_code.equalsIgnoreCase("success") ){
            //获取订单号
            String outtradeno = result.get("out_trade_no");
            //业务结果
            if(result_code.equalsIgnoreCase("success")){
                if(outtradeno!=null){
                    //修改订单状态  out_trade_no
                    orderService.updateStatus(outtradeno,result.get("transaction_id"));
                }
            }else{
                //订单删除
                orderService.deleteOrder(outtradeno);
            }
        }

    }
}
```



## 6 定时处理订单状态

### 6.1 业务分析

在现实场景中，可能会出现这么种情况，就是用户支付后，有可能畅购服务网络不通或者服务器挂了，此时会导致回调地址无法接收到用户支付状态，这时候我们需要取微信服务器查询。所以我们之前订单信息的ID存入到了Redis队列，主要用于解决这种网络不可达造成支付状态无法回调获取的问题。

实现思路如下：

```properties
1.每次下单，都将订单存入到Reids List队列中
2.定时每5秒检查一次Redis 队列中是否有数据，如果有，则再去查询微信服务器支付状态
3.如果已支付，则修改订单状态
4.如果没有支付，是等待支付，则再将订单存入到Redis队列中，等会再次检查
5.如果是支付失败，直接删除订单信息并修改订单状态
```



## 7 RabbitMQ延时消息队列

### 7.1 延时队列介绍

延时队列即放置在该队列里面的消息是不需要立即消费的，而是等待一段时间之后取出消费。
那么，为什么需要延迟消费呢？我们来看以下的场景

网上商城下订单后30分钟后没有完成支付，取消订单(如：淘宝、去哪儿网)，系统创建了预约之后，需要在预约时间到达前一小时提醒被预约的双方参会，系统中的业务失败之后，需要重试。这些场景都非常常见，我们可以思考，比如第二个需求，系统创建了预约之后，需要在预约时间到达前一小时提醒被预约的双方参会。那么一天之中肯定是会有很多个预约的，时间也是不一定的，假设现在有1点 2点 3点 三个预约，如何让系统知道在当前时间等于0点 1点 2点给用户发送信息呢，是不是需要一个轮询，一直去查看所有的预约，比对当前的系统时间和预约提前一小时的时间是否相等呢？这样做非常浪费资源而且轮询的时间间隔不好控制。如果我们使用延时消息队列呢，我们在创建时把需要通知的预约放入消息中间件中，并且设置该消息的过期时间，等过期时间到达时再取出消费即可。

Rabbitmq 实现延时队列一般而言有两种形式：

第一种方式：利用两个特性： Time To Live(TTL)、Dead Letter Exchanges（DLX）[A队列过期->转发给B队列]

第二种方式：利用rabbitmq中的插件x-delay-message



### 7.2 TTL DLX实现延时队列

#### 7.2.1 TTL DLX介绍

**TTL**
RabbitMQ可以针对队列设置x-expires(则队列中所有的消息都有相同的过期时间)或者针对Message设置x-message-ttl(对消息进行单独设置，每条消息TTL可以不同)，来控制消息的生存时间，如果超时(两者同时设置以最先到期的时间为准)，则消息变为dead letter(死信)

**Dead Letter Exchanges（DLX）**
RabbitMQ的Queue可以配置x-dead-letter-exchange和x-dead-letter-routing-key（可选）两个参数，如果队列内出现了dead letter，则按照这两个参数重新路由转发到指定的队列。
x-dead-letter-exchange：出现dead letter之后将dead letter重新发送到指定exchange

x-dead-letter-routing-key：出现dead letter之后将dead letter重新按照指定的routing-key发送

![1557396863944](https://dylanguo.xyz/img/1557396863944.png)



#### 7.2.3 DLX延时队列实现

##### 7.2.3.1 创建工程

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



##### 7.2.3.2 队列创建

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



##### 7.2.3.3 消息监听

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



##### 7.2.3.4 创建启动类

```java
@SpringBootApplication
@EnableRabbit
public class SpringRabbitMQApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringRabbitMQApplication.class,args);
    }
}
```



##### 7.2.3.5 测试

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



## 8 库存回滚

### 8.1 流程回顾

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



### 8.2 关闭支付

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

