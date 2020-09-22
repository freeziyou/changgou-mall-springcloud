

## 第四天 lua、Nginx、Canal 实现广告缓存

#### 广告缓存读取

-  使用 OpenResty，封装了 Nginx 和 lua，支持 10K 以上高并发
-  用户访问首页时，首先会查询 Nginx 缓存，如查不到广告缓存，则进一步通过 Lua 脚本查询 Redis 缓存，仍然未命中，则最后才查询 MySQL

![advertisement-cache-read](http://dylanguo.xyz/img/advertisement-cache-read.png)



#### 广告缓存同步

-  开发者在对广告进行 CRUD 时，调用 Content 微服务，会直接对 MySQL 进行操作
-  Canal 微服务会通过查询 binlog 来监听 MySQL（毫秒级），触发监听事件后调用 Feigh 去使用 Content 微服务中的方法查询，获取该分类下的广告信息，最后再存入 Redis 中

![advertisement-cache-syn](http://dylanguo.xyz/img/advertisement-cache-syn.png)
