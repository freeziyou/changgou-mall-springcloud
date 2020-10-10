# 第10章 购物车

## 学习目标

- 资源服务器授权配置
- 掌握OAuth认证微服务动态加载数据
- 掌握购物车流程
- 掌握购物车渲染流程
- OAuth2.0认证并获取用户令牌数据
- 微服务与微服务之间的认证



## 1 资源服务器授权配置

### 1.1 资源服务授权配置

![1562406193809](https://dylanguo.xyz/img/1562406193809.png)

基本上所有微服务都是资源服务



(1)配置公钥 认证服务生成令牌采用非对称加密算法，认证服务采用私钥加密生成令牌，对外向资源服务提供公钥，资源服务使 用公钥 来校验令牌的合法性。 将公钥拷贝到 public.key文件中，将此文件拷贝到每一个需要的资源服务工程的classpath下 ,例如:用户微服务.   



(2)添加依赖

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-oauth2</artifactId>
</dependency>
```



(3)配置每个系统的Http请求路径安全控制策略以及读取公钥信息识别令牌，如下：

```java
@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)//激活方法上的PreAuthorize注解
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    //公钥
    private static final String PUBLIC_KEY = "public.key";

    /***
     * 定义JwtTokenStore
     * @param jwtAccessTokenConverter
     * @return
     */
    @Bean
    public TokenStore tokenStore(JwtAccessTokenConverter jwtAccessTokenConverter) {
        return new JwtTokenStore(jwtAccessTokenConverter);
    }

    /***
     * 定义JJwtAccessTokenConverter
     * @return
     */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setVerifierKey(getPubKey());
        return converter;
    }
    /**
     * 获取非对称加密公钥 Key
     * @return 公钥 Key
     */
    private String getPubKey() {
        Resource resource = new ClassPathResource(PUBLIC_KEY);
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(resource.getInputStream());
            BufferedReader br = new BufferedReader(inputStreamReader);
            return br.lines().collect(Collectors.joining("\n"));
        } catch (IOException ioe) {
            return null;
        }
    }

    /***
     * Http安全配置，对每个到达系统的http请求链接进行校验
     * @param http
     * @throws Exception
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        //所有请求必须认证通过
        http.authorizeRequests()
                //下边的路径放行
                .antMatchers(
                        "/user/add"). //配置地址放行
                permitAll()
                .anyRequest().
                authenticated();    //其他地址需要认证授权
    }
}
```



#### 1.2 用户微服务资源授权

将上面生成的公钥public.key拷贝到changgou-service-user微服务工程的resources目录下，如下图：

![1562476672902](https://dylanguo.xyz/img/1562476672902.png)

(1)引入依赖

在changgou-service-user微服务工程pom.xml中引入oauth依赖

```xml
<!--oauth依赖-->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-oauth2</artifactId>
</dependency>
```

(2)资源授权配置

在changgou-service-user工程中创建com.changgou.user.config.ResourceServerConfig，代码如下：

```java
@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)//激活方法上的PreAuthorize注解
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    //公钥
    private static final String PUBLIC_KEY = "public.key";

    /***
     * 定义JwtTokenStore
     * @param jwtAccessTokenConverter
     * @return
     */
    @Bean
    public TokenStore tokenStore(JwtAccessTokenConverter jwtAccessTokenConverter) {
        return new JwtTokenStore(jwtAccessTokenConverter);
    }

    /***
     * 定义JJwtAccessTokenConverter
     * @return
     */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setVerifierKey(getPubKey());
        return converter;
    }
    /**
     * 获取非对称加密公钥 Key
     * @return 公钥 Key
     */
    private String getPubKey() {
        Resource resource = new ClassPathResource(PUBLIC_KEY);
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(resource.getInputStream());
            BufferedReader br = new BufferedReader(inputStreamReader);
            return br.lines().collect(Collectors.joining("\n"));
        } catch (IOException ioe) {
            return null;
        }
    }

    /***
     * Http安全配置，对每个到达系统的http请求链接进行校验
     * @param http
     * @throws Exception
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        //所有请求必须认证通过
        http.authorizeRequests()
                //下边的路径放行
                .antMatchers(
                        "/user/add"). //配置地址放行
                permitAll()
                .anyRequest().
                authenticated();    //其他地址需要认证授权
    }
}
```



#### 1.3 授权测试

![1562406193809](https://dylanguo.xyz/img/1562406193809.png)

用户每次访问微服务的时候，需要先申请令牌，令牌申请后，每次将令牌放到头文件中，才能访问微服务。

头文件中每次需要添加一个`Authorization`头信息，头的结果为`bearer token`。



## 2 OAuth对接微服务

![1562729873478](https://dylanguo.xyz/img/1562729873478.png)

用户每次访问微服务的时候，先去oauth2.0服务登录，登录后再访问微服务网关，微服务网关将请求转发给其他微服务处理。

```properties
1.用户登录成功后，会将令牌信息存入到cookie中(一般建议存入到头文件中)
2.用户携带Cookie中的令牌访问微服务网关
3.微服务网关先获取头文件中的令牌信息，如果Header中没有Authorization令牌信息，则取参数中找，参数中如果没有，则取Cookie中找Authorization，最后将令牌信息封装到Header中，并调用其他微服务
4.其他微服务会获取头文件中的Authorization令牌信息，然后匹配令牌数据是否能使用公钥解密，如果解密成功说明用户已登录，解密失败，说明用户未登录
```



### 1.1 令牌加入到Header中

修改changgou-gateway-web的全局过滤器com.changgou.filter.AuthorizeFilter，实现将令牌信息添加到头文件中，代码如下：

![1562730359189](https://dylanguo.xyz/img/1562730359189.png)



### 1.2 SpringSecurity权限控制

由于我们项目使用了微服务，任何用户都有可能使用任意微服务，此时我们需要控制相关权限，例如：普通用户角色不能使用用户的删除操作，只有管理员才可以使用,那么这个时候就需要使用到SpringSecurity的权限控制功能了。



#### 1.2.1 角色加载

在changgou-user-oauth服务中，com.changgou.oauth.config.UserDetailsServiceImpl该类实现了加载用户相关信息，如下代码：

![1562735467555](https://dylanguo.xyz/img/1562735467555.png)

上述代码给登录用户定义了三个角色，分别为`salesman`,`accountant`,`user`，这一块我们目前使用的是硬编码方式将角色写死了，后面会从数据库加载。



#### 1.2.2 角色权限控制

在每个微服务中，需要获取用户的角色，然后根据角色识别是否允许操作指定的方法，Spring Security中定义了四个支持权限控制的表达式注解，分别是`@PreAuthorize`、`@PostAuthorize`、`@PreFilter`和`@PostFilter`。其中前两者可以用来在方法调用前或者调用后进行权限检查，后两者可以用来对集合类型的参数或者返回值进行过滤。在需要控制权限的方法上，我们可以添加`@PreAuthorize`注解，用于方法执行前进行权限检查，校验用户当前角色是否能访问该方法。

(1)开启@PreAuthorize

在`changgou-user-service`的`ResourceServerConfig`类上添加`@EnableGlobalMethodSecurity`注解，用于开启@PreAuthorize的支持，代码如下：

![1562736198378](https://dylanguo.xyz/img/1562736198378.png)

(2)方法权限控制

在`changgoug-service-user`微服务的`com.changgou.user.controller.UserController`类的delete()方法上添加权限控制注解`@PreAuthorize`，代码如下：

![1562736271600](https://dylanguo.xyz/img/1562736271600.png)

知识点说明：

如果希望一个方法能被多个角色访问，配置:`@PreAuthorize("hasAnyAuthority('admin','user')")`

如果希望一个类都能被多个角色访问，在类上配置:`@PreAuthorize("hasAnyAuthority('admin','user')")`



## 3 OAuth动态加载数据

前面OAuth我们用的数据都是静态的，在现实工作中，数据都是从数据库加载的，所以我们需要调整一下OAuth服务，从数据库加载相关数据。

- 客户端数据[生成令牌相关数据]
- 用户登录账号密码从数据库加载



### 3.1 客户端数据加载

#### 3.1.1 数据介绍

(1)客户端静态数据

在`changgou-user-oauth`的com.changgou.oauth.config.AuthorizationServerConfig类中配置了客户端静态数据，主要用于配置客户端数据，代码如下：

![1562739922920](https://dylanguo.xyz/img/1562739922920.png)



(2)客户端表结构介绍

创建一个数据库`changgou_oauth`,并在数据库中创建一张表，表主要用于记录客户端相关信息，表结构如下：

```sql
CREATE TABLE `oauth_client_details` (
  `client_id` varchar(48) NOT NULL COMMENT '客户端ID，主要用于标识对应的应用',
  `resource_ids` varchar(256) DEFAULT NULL,
  `client_secret` varchar(256) DEFAULT NULL COMMENT '客户端秘钥，BCryptPasswordEncoder加密算法加密',
  `scope` varchar(256) DEFAULT NULL COMMENT '对应的范围',
  `authorized_grant_types` varchar(256) DEFAULT NULL COMMENT '认证模式',
  `web_server_redirect_uri` varchar(256) DEFAULT NULL COMMENT '认证后重定向地址',
  `authorities` varchar(256) DEFAULT NULL,
  `access_token_validity` int(11) DEFAULT NULL COMMENT '令牌有效期',
  `refresh_token_validity` int(11) DEFAULT NULL COMMENT '令牌刷新周期',
  `additional_information` varchar(4096) DEFAULT NULL,
  `autoapprove` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`client_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```

字段说明：

```properties
client_id：客户端id 
resource_ids：资源id（暂时不用） 
client_secret：客户端秘钥 
scope：范围 
access_token_validity：访问token的有效期（秒） 
refresh_token_validity：刷新token的有效期（秒） 
authorized_grant_type：授权类型:authorization_code,password,refresh_token,client_credentials    
```



#### 3.1.2 加载数据改造

(1)修改连接配置

从数据库加载数据，我们需要先配置数据库连接，在changgou-user-oauth的application.yml中配置连接信息，如下代码：

```properties
  datasource:
    driver-class-name: com.mysql.jdbc.Driver
    url: jdbc:mysql://192.168.211.132:3306/changgou_oauth?useUnicode=true&characterEncoding=utf-8&useSSL=false&allowMultiQueries=true&serverTimezone=UTC
    username: root
    password: 123456
```

(2)修改客户端加载源

修改changgou-user-oauth的com.changgou.oauth.config.AuthorizationServerConfig类的configure方法，将之前静态的客户端数据变成从数据库加载，修改如下：

**修改前：**

![1562740967631](https://dylanguo.xyz/img/1562740967631.png)

**修改后：**

![1562741724579](https://dylanguo.xyz/img/1562741724579.png)

(3)UserDetailsServiceImpl修改

将之前的加密方式去掉即可，代码如下：

**修改前：**

![1562741950179](https://dylanguo.xyz/img/1562741950179.png)

**修改后：**

![1562742077131](https://dylanguo.xyz/img/1562742077131.png)



### 3.2 用户数据加载

![1562748464379](https://dylanguo.xyz/img/1562748464379.png)

因为我们目前整套系统是对内提供登录访问，所以每次用户登录的时候oauth需要调用用户微服务查询用户信息，如上图：

我们需要在用户微服务中提供用户信息查询的方法，并在oauth中使用feign调用即可。

在真实工作中，用户和管理员对应的oauth认证服务器会分开，网关也会分开，我们今天的课堂案例只实现用户相关的认证即可。



(1)Feign创建

在changgou-service-user-api中创建com.changgou.user.feign.UserFeign，代码如下：

```java
@FeignClient(name="user")
@RequestMapping("/user")
public interface UserFeign {

    /***
     * 根据ID查询用户信息
     * @param id
     * @return
     */
    @GetMapping("/load/{id}")
    Result<User> findById(@PathVariable String id);
}
```

(2)修改UserController

修改changgou-service-user的UserController的findById方法，添加一个新的地址，用于加载用户信息，代码如下：

![1562747605788](https://dylanguo.xyz/img/1562747605788.png)

(3)放行查询用户方法

因为oauth需要调用查询用户信息，需要在changgou-service-user中放行`/user/load/{id}`方法,修改ResourceServerConfig，添加对`/user/load/{id}`的放行操作，代码如下：

![1562747747800](https://dylanguo.xyz/img/1562747747800.png)

(4)oauth调用查询用户信息

oauth引入对user-api的依赖

```xml
<!--依赖用户api-->
<dependency>
    <groupId>com.changgou</groupId>
    <artifactId>changgou-service-user-api</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```

修改oauth的`com.changgou.oauth.config.UserDetailsServiceImpl`的`loadUserByUsername`方法，调用UserFeign查询用户信息，代码如下：

![1562747859764](https://dylanguo.xyz/img/1562747859764.png)

(5)feign开启

修改`com.changgou.OAuthApplication`开启Feign客户端功能

```java
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.changgou.user.feign"})
@MapperScan(basePackages = "com.changgou.auth.dao")
public class OAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(OAuthApplication.class,args);
    }

    @Bean(name = "restTemplate")
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
```



## 4 购物车

购物车分为用户登录购物车和未登录购物车操作，国内知名电商京东用户登录和不登录都可以操作购物车，如果用户不登录，操作购物车可以将数据存储到Cookie或者WebSQL或者SessionStorage中，用户登录后购物车数据可以存储到Redis中，再将之前未登录加入的购物车合并到Redis中即可。

淘宝天猫则采用了另外一种实现方案，用户要想将商品加入购物车，必须先登录才能操作购物车。

我们今天实现的购物车是天猫解决方案，即用户必须先登录才能使用购物车功能。



### 4.1 购物车分析

(1)需求分析

用户在商品详细页点击加入购物车，提交商品SKU编号和购买数量，添加到购物车。购物车展示页面如下：

![1558235718643](https://dylanguo.xyz/img/1558235718643.png)

(2)购物车实现思路

![1558259384800](https://dylanguo.xyz/img/1558259384800.png)

我们实现的是用户登录后的购物车，用户将商品加入购物车的时候，直接将要加入购物车的详情存入到Redis即可。每次查看购物车的时候直接从Redis中获取。

(3)表结构分析

用户登录后将商品加入购物车，需要存储商品详情以及购买数量，购物车详情表如下：

changgou_order数据中tb_order_item表：

```sql
CREATE TABLE `tb_order_item` (
  `id` varchar(20) COLLATE utf8_bin NOT NULL COMMENT 'ID',
  `category_id1` int(11) DEFAULT NULL COMMENT '1级分类',
  `category_id2` int(11) DEFAULT NULL COMMENT '2级分类',
  `category_id3` int(11) DEFAULT NULL COMMENT '3级分类',
  `spu_id` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT 'SPU_ID',
  `sku_id` bigint(20) NOT NULL COMMENT 'SKU_ID',
  `order_id` bigint(20) NOT NULL COMMENT '订单ID',
  `name` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '商品名称',
  `price` int(20) DEFAULT NULL COMMENT '单价',
  `num` int(10) DEFAULT NULL COMMENT '数量',
  `money` int(20) DEFAULT NULL COMMENT '总金额',
  `pay_money` int(11) DEFAULT NULL COMMENT '实付金额',
  `image` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '图片地址',
  `weight` int(11) DEFAULT NULL COMMENT '重量',
  `post_fee` int(11) DEFAULT NULL COMMENT '运费',
  `is_return` char(1) COLLATE utf8_bin DEFAULT NULL COMMENT '是否退货',
  PRIMARY KEY (`id`),
  KEY `item_id` (`sku_id`),
  KEY `order_id` (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
```

购物车详情表其实就是订单详情表结构，只是目前临时存储数据到Redis，等用户下单后才将数据从Redis取出存入到MySQL中。

在商城中一般都会有不同分类商品做折扣活动，下面有一张表记录了对应分类商品的折扣活动，每类商品只允许参与一次折扣活动。

changgou_goods数据中tb_pref表：

```sql
CREATE TABLE `tb_pref` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `cate_id` int(11) DEFAULT NULL COMMENT '分类ID',
  `buy_money` int(11) DEFAULT NULL COMMENT '消费金额',
  `pre_money` int(11) DEFAULT NULL COMMENT '优惠金额',
  `start_time` date DEFAULT NULL COMMENT '活动开始日期',
  `end_time` date DEFAULT NULL COMMENT '活动截至日期',
  `type` char(1) DEFAULT NULL COMMENT '类型,1:普通订单，2：限时活动',
  `state` char(1) DEFAULT NULL COMMENT '状态,1:有效，0：无效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
```



### 4.2 订单购物车微服务

我们先搭建一个订单购物车微服务工程，按照如下步骤实现即可。

(1)导入资源

搭建订单购物车微服务，工程名字changgou-service-order并搭建对应的api工程changgou-service-order-api,将生成好的dao和相关文件拷贝到工程中，以及生成好的Pojo拷贝到API工程中。同时在changgou-service-order中引入changgou-service-order-api,如下图：

依赖引入：

```xml
<dependency>
    <groupId>com.changgou</groupId>
    <artifactId>changgou-service-order-api</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```



changgou-service-order:

![1562749854542](https://dylanguo.xyz/img/1562749854542.png)

changgou-service-order-api:

![1562749956113](https://dylanguo.xyz/img/1562749956113.png)

(2)application.yml配置

在changgou-service-order的resources中添加application.yml配置文件，代码如下：

```properties
server:
  port: 18090
spring:
  application:
    name: order
  datasource:
    driver-class-name: com.mysql.jdbc.Driver
    url: jdbc:mysql://192.168.211.132:3306/changgou_order?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC
    username: root
    password: 123456
  redis:
    host: 192.168.211.132
    port: 6379
  main:
    allow-bean-definition-overriding: true

eureka:
  client:
    service-url:
      defaultZone: http://127.0.0.1:7001/eureka
  instance:
    prefer-ip-address: true
feign:
  hystrix:
    enabled: true
```

(3)创建启动类

在changgou-service-order的resources中创建启动类，代码如下：

```java
@SpringBootApplication
@EnableEurekaClient
@MapperScan(basePackages = {"com.changgou.order.dao"})
public class OrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class,args);
    }
}
```



### 4.3 添加购物车

#### 4.3.1 思路分析

![1558235343539](https://dylanguo.xyz/img/1558235343539.png)

用户添加购物车，只需要将要加入购物车的商品存入到Redis中即可。一个用户可以将多件商品加入购物车，存储到Redis中的数据可以采用Hash类型。

选Hash类型可以将用户的用户名作为namespace的一部分，将指定商品加入购物车，则往对应的namespace中增加一个key和value，key是商品ID，value是加入购物车的商品详情，如下图：

![1558260256362](https://dylanguo.xyz/img/1558260256362.png)



#### 4.3.2 代码实现

(1)feign创建

下订单需要调用feign查看商品信息，我们先创建feign分别根据ID查询Sku和Spu信息，在changgou-service-goods-api工程中的SkuFeign和SpuFeign根据ID查询方法如下：

com.changgou.goods.feign.SkuFeign

```java
/***
 * 根据ID查询SKU信息
 * @param id : sku的ID
 */
@GetMapping(value = "/{id}")
public Result<Sku> findById(@PathVariable(value = "id", required = true) Long id);
```



com.changgou.goods.feign.SpuFeign

```java
/***
 * 根据SpuID查询Spu信息
 * @param id
 * @return
 */
@GetMapping("/{id}")
public Result<Spu> findById(@PathVariable(name = "id") Long id);
```





(2)业务层

业务层接口

在changgou-service-order微服务中创建com.changgou.order.service.CartService接口，代码如下：

```java
public interface CartService {

    /***
     * 添加购物车
     * @param num:购买商品数量
     * @param id：购买ID
     * @param username：购买用户
     * @return
     */
    void add(Integer num, Long id, String username);
}
```



业务层接口实现类

在changgou-service-order微服务中创建接口实现类com.changgou.order.service.impl.CartServiceImpl,代码如下：

```java
@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private SkuFeign skuFeign;

    @Autowired
    private SpuFeign spuFeign;


    /***
     * 加入购物车
     * @param num:购买商品数量
     * @param id：购买ID
     * @param username：购买用户
     * @return
     */
    @Override
    public void add(Integer num, Long id, String username) {
        //查询SKU
        Result<Sku> resultSku = skuFeign.findById(id);
        if(resultSku!=null && resultSku.isFlag()){
            //获取SKU
            Sku sku = resultSku.getData();
            //获取SPU
            Result<Spu> resultSpu = spuFeign.findById(sku.getSpuId());

            //将SKU转换成OrderItem
            OrderItem orderItem = sku2OrderItem(sku,resultSpu.getData(), num);

            /******
             * 购物车数据存入到Redis
             * namespace = Cart_[username]
             * key=id(sku)
             * value=OrderItem
             */
            redisTemplate.boundHashOps("Cart_"+username).put(id,orderItem);
        }
    }

    /***
     * SKU转成OrderItem
     * @param sku
     * @param num
     * @return
     */
    private OrderItem sku2OrderItem(Sku sku,Spu spu,Integer num){
        OrderItem orderItem = new OrderItem();
        orderItem.setSpuId(sku.getSpuId());
        orderItem.setSkuId(sku.getId());
        orderItem.setName(sku.getName());
        orderItem.setPrice(sku.getPrice());
        orderItem.setNum(num);
        orderItem.setMoney(num*orderItem.getPrice());       //单价*数量
        orderItem.setPayMoney(num*orderItem.getPrice());    //实付金额
        orderItem.setImage(sku.getImage());
        orderItem.setWeight(sku.getWeight()*num);           //重量=单个重量*数量

        //分类ID设置
        orderItem.setCategoryId1(spu.getCategory1Id());
        orderItem.setCategoryId2(spu.getCategory2Id());
        orderItem.setCategoryId3(spu.getCategory3Id());
        return orderItem;
    }
}
```



(3)控制层

在changgou-service-order微服务中创建com.changgou.order.controller.CartController，代码如下：

```java
@RestController
@CrossOrigin
@RequestMapping(value = "/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    /***
     * 加入购物车
     * @param num:购买的数量
     * @param id：购买的商品(SKU)ID
     * @return
     */
    @RequestMapping(value = "/add")
    public Result add(Integer num, Long id){
        //用户名
        String username="szitheima";
        //将商品加入购物车
        cartService.add(num,id,username);
        return new Result(true, StatusCode.OK,"加入购物车成功！");
    }
}
```



(4)feign配置

修改`com.changgou.OrderApplication`开启Feign客户端：

```java
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients(basePackages = {"com.changgou.goods.feign"})
@MapperScan(basePackages = {"com.changgou.order.dao"})
public class OrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class,args);
    }
}
```



### 4.4 购物车列表

#### 4.4.1 思路分析

![1558260759149](https://dylanguo.xyz/img/1558260759149.png)

接着我们实现一次购物车列表操作。因为存的时候是根据用户名往Redis中存储用户的购物车数据的，所以我们这里可以将用户的名字作为key去Redis中查询对应的数据。



#### 4.4.2 代码实现

(1)业务层

业务层接口

修改changgou-service-order微服务的com.changgou.order.service.CartService接口，添加购物车列表方法，代码如下：

```java
/***
 * 查询用户的购物车数据
 * @param username
 * @return
 */
List<OrderItem> list(String username);
```



业务层接口实现类

修改changgou-service-order微服务的com.changgou.order.service.impl.CartServiceImpl类，添加购物车列表实现方法，代码如下：

```java
/***
 * 查询用户购物车数据
 * @param username
 * @return
 */
@Override
public List<OrderItem> list(String username) {
    //查询所有购物车数据
    List<OrderItem> orderItems = redisTemplate.boundHashOps("Cart_"+username).values();
    return orderItems;
}
```



(2)控制层

修改changgou-service-order微服务的com.changgou.order.controller.CartController类，添加购物车列表查询方法，代码如下：

```java
/***
 * 查询用户购物车列表
 * @return
 */
@GetMapping(value = "/list")
public Result list(){
    //用户名
    String username="szitheima";
    List<OrderItem> orderItems = cartService.list(username);
    return new Result(true,StatusCode.OK,"购物车列表查询成功！",orderItems);
}
```



#### 4.4.3 问题处理

(1)删除商品购物车

![1562835328074](https://dylanguo.xyz/img/1562835328074.png)

我们发现个问题，就是用户将商品加入购物车，无论数量是正负，都会执行添加购物车，如果数量如果<=0，应该移除该商品的。

修改changgou-service-order的com.changgou.order.service.impl.CartServiceImpl的add方法，添加如下代码：

![1562835767995](https://dylanguo.xyz/img/1562835767995.png)



## 5 用户身份识别

### 5.1 购物车需求分析

![1558232917323](https://dylanguo.xyz/img/1558232917323.png)

购物车功能已经做完了，但用户我们都是硬编码写死的。用户要想将商品加入购物车，必须得先登录授权，登录授权后再经过微服务网关，微服务网关需要过滤判断用户请求是否存在令牌，如果存在令牌，才能再次访问微服务，此时网关会通过过滤器将令牌数据再次存入到头文件中，然后访问模板渲染服务，模板渲染服务再调用订单购物车微服务，此时也需要将令牌数据存入到头文件中，将令牌数据传递给购物车订单微服务，到了购物车订单微服务的时候，此时微服务需要校验令牌数据，如果令牌正确，才能使用购物车功能，并解析令牌数据获取用户信息。



### 5.2 微服务之间认证(令牌传递)

![1558269362528](https://dylanguo.xyz/img/1558269362528.png)

如上图：因为微服务之间并没有传递头文件，所以我们可以定义一个拦截器，每次微服务调用之前都先检查下头文件，将请求的头文件中的令牌数据再放入到header中，再调用其他微服务即可。

(1)创建拦截器

在changgou-web-order服务中创建一个com.changgou.order.interceptor.FeignInterceptor拦截器，并将所有头文件数据再次加入到Feign请求的微服务头文件中，代码如下：

```java
public class FeignInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        try {
            //使用RequestContextHolder工具获取request相关变量
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attributes != null) {
                //取出request
                HttpServletRequest request = attributes.getRequest();
                //获取所有头文件信息的key
                Enumeration<String> headerNames = request.getHeaderNames();
                if (headerNames != null) {
                    while (headerNames.hasMoreElements()) {
                        //头文件的key
                        String name = headerNames.nextElement();
                        //头文件的value
                        String values = request.getHeader(name);
                        //将令牌数据添加到头文件中
                        requestTemplate.header(name, values);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```



(2)创建拦截器Bean

在changgou-web-order服务中启动类里创建对象实例

```java
/***
 * 创建拦截器Bean对象
 * @return
 */
@Bean
public FeignInterceptor feignInterceptor(){
    return new FeignInterceptor();
}
```



(3)测试

![1562839507997](E:/%25E7%25BD%2591%25E8%25AF%25BE/Java/%25E7%2595%2585%25E8%25B4%25AD%25E5%2595%2586%25E5%259F%258E/changgou/day10/%25E8%25AE%25B2%25E4%25B9%2589/images/1562839507997.png)

**我们发现这块的ServletRequestAttributes始终为空，RequestContextHolder.getRequestAttributes()该方法是从ThreadLocal变量里面取得相应信息的，当hystrix断路器的隔离策略为THREAD时，是无法取得ThreadLocal中的值。**

解决方案：hystrix隔离策略换为SEMAPHORE

修改changgou-web-order的application.yml配置文件，在application.yml中添加如下代码，代码如下：

```properties
#hystrix 配置
hystrix:
  command:
    default:
      execution:
        isolation:
          thread:
            timeoutInMilliseconds: 10000
          strategy: SEMAPHORE
```

再次测试，效果如下：

![1562839848861](E:/%25E7%25BD%2591%25E8%25AF%25BE/Java/%25E7%2595%2585%25E8%25B4%25AD%25E5%2595%2586%25E5%259F%258E/changgou/day10/%25E8%25AE%25B2%25E4%25B9%2589/images/1562839848861.png)

(4)工具类抽取

微服务之间相互认证的情况非常多，我们可以把上面的拦截器抽取出去，放到changgou-common的entity包中，其他工程需要用，直接创建一个@Bean对象即可。



### 5.3 网关过滤

为了不给微服务带来一些无效的请求，我们可以在网关中过滤用户请求，先看看头文件中是否有Authorization，如果有再看看cookie中是否有Authorization，如果都通过了才允许请求到达微服务。



### 5.4 订单对接网关+oauth

(1)application.yml配置

修改微服务网关`changgou-gateway-web`的application.yml配置文件，添加order的路由过滤配置，配置如下：

```properties
            #订单微服务
            - id: changgou_order_route
              uri: lb://order
              predicates:
              - Path=/api/cart/**,/api/categoryReport/**,/api/orderConfig/**,/api/order/**,/api/orderItem/**,/api/orderLog/**,/api/preferential/**,/api/returnCause/**,/api/returnOrder/**,/api/returnOrderItem/**
              filters:
              - StripPrefix=1    
```

这里注意使用的是yml格式，所以上面代码中的空格也一并记得拷贝到application.yml文件中。

(2)过滤配置

在微服务网关`changgou-gateway-web`中添加`com.changgou.filter.URLFilter`过滤类，用于过滤需要用户登录的地址，代码如下：

```java
public class URLFilter {

    /**
     * 要放行的路径
     */
    private static final String noAuthorizeurls = "/api/user/add,/api/user/login";


    /**
     * 判断 当前的请求的地址中是否在已有的不拦截的地址中存在,如果存在 则返回true  表示  不拦截   false表示拦截
     *
     * @param uri 获取到的当前的请求的地址
     * @return
     */
    public static boolean hasAuthorize(String uri) {
        String[] split = noAuthorizeurls.split(",");

        for (String s : split) {
            if (s.equals(uri)) {
                return true;
            }
        }
        return false;
    }


}
```

(3)全局过滤器修改

修改之前的`com.changgou.filter.AuthorizeFilter`的过滤方法，将是否需要用户登录过滤也加入其中，代码如下：

![1567221901000](https://dylanguo.xyz/img/1567221901000.png)

(4)集成OAuth进行安全校验

修改changgou-service-order的pom.xml，添加oauth的依赖

```xml
<!--oauth依赖-->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-oauth2</artifactId>
</dependency>
```



将公钥拷贝到changgou-service-order工程的resources中



在changgou-service-order工程中创建com.changgou.order.config.ResourceServerConfig,配置需要拦截的路径，这里需要拦截所有请求路径，代码如下：

```java
@Configuration
@EnableResourceServer
//开启方法上的PreAuthorize注解
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    //公钥
    private static final String PUBLIC_KEY = "public.key";

    /***
     * 定义JwtTokenStore
     * @param jwtAccessTokenConverter
     * @return
     */
    @Bean
    public TokenStore tokenStore(JwtAccessTokenConverter jwtAccessTokenConverter) {
        return new JwtTokenStore(jwtAccessTokenConverter);
    }

    /***
     * 定义JJwtAccessTokenConverter
     * @return
     */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setVerifierKey(getPubKey());
        return converter;
    }
    /**
     * 获取非对称加密公钥 Key
     * @return 公钥 Key
     */
    private String getPubKey() {
        Resource resource = new ClassPathResource(PUBLIC_KEY);
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(resource.getInputStream());
            BufferedReader br = new BufferedReader(inputStreamReader);
            return br.lines().collect(Collectors.joining("\n"));
        } catch (IOException ioe) {
            return null;
        }
    }

    /***
     * Http安全配置，对每个到达系统的http请求链接进行校验
     * @param http
     * @throws Exception
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        //所有请求必须认证通过
        http.authorizeRequests()
                .anyRequest().
                authenticated();    //其他地址需要认证授权
    }
}
```



### 5.5 获取用户数据

#### 5.5.1 数据分析

用户登录后，数据会封装到`SecurityContextHolder.getContext().getAuthentication()`里面，我们可以将数据从这里面取出，然后转换成`OAuth2AuthenticationDetails`,在这里面可以获取到令牌信息、令牌类型等，代码如下：

![1562801050157](https://dylanguo.xyz/img/1562801050157.png)

这里的tokenValue是加密之后的令牌数据，remoteAddress是用户的IP信息，tokenType是令牌类型。

我们可以获取令牌加密数据后，使用公钥对它进行解密，如果能解密说明说句无误，如果不能解密用户也没法执行到这一步。解密后可以从明文中获取用户信息。



#### 5.5.2 代码实现

(1)读取公钥

在changgou-service-order微服务中创建com.changgou.order.config.TokenDecode类，用于解密令牌信息，在类中读取公钥信息，代码如下：

```java
@Component
public class TokenDecode {
    //公钥
    private static final String PUBLIC_KEY = "public.key";

    private static String publickey="";


    /**
     * 获取非对称加密公钥 Key
     * @return 公钥 Key
     */
    public String getPubKey() {
        if(!StringUtils.isEmpty(publickey)){
            return publickey;
        }
        Resource resource = new ClassPathResource(PUBLIC_KEY);
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(resource.getInputStream());
            BufferedReader br = new BufferedReader(inputStreamReader);
            publickey = br.lines().collect(Collectors.joining("\n"));
            return publickey;
        } catch (IOException ioe) {
            return null;
        }
    }
}
```



(2)校验解析令牌数据

在TokenDecode类中添加校验解析令牌数据的方法，这里用到了JwtHelper实现。

```java
/***
 * 读取令牌数据
 */
public Map<String,String> dcodeToken(String token){
    //校验Jwt
    Jwt jwt = JwtHelper.decodeAndVerify(token, new RsaVerifier(getPubKey()));

    //获取Jwt原始内容
    String claims = jwt.getClaims();
    return JSON.parseObject(claims,Map.class);
}
```



(3)获取令牌数据

在TokenDecode类中添加一个getUserInfo方法，用于从容器中获取令牌信息，代码如下：

```java
/***
 * 获取用户信息
 * @return
 */
public Map<String,String> getUserInfo(){
    //获取授权信息
    OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();
    //令牌解码
    return dcodeToken(details.getTokenValue());
}
```



(4)控制层获取用户数据

在CartController中注入TokenDecode，并调用TokenDecode的getUserInfo方法获取用户信息，代码如下：

注入TokenDecode：

```java
@Autowired
private TokenDecode tokenDecode;
```



### 5.6 代码抽取

以后很有可能在很多微服务中都会用到该对象来获取用户信息，我们可以把它抽取出去。

在changgou-common工程中引入鉴权包

```xml
<!--oauth依赖-->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-oauth2</artifactId>
    <scope>provided</scope>
</dependency>

<!--鉴权-->
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt</artifactId>
    <version>0.9.0</version>
    <scope>provided</scope>
</dependency>
```
