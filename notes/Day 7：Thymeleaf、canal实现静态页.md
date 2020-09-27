## Day 7:  Thymeleaf、canal实现静态页

- Thymeleaf

- 畅购商品详情页

  - ![1566736163765](http://dylanguo.xyz/img/1566736163765.png)

  执行步骤解释：

  + 系统管理员（商家运维人员）修改或者审核商品的时候，会触发 canal 监控数据
  + canal 微服务获取修改数据后，调用静态页微服务的方法进行生成静态页 
  + 静态页微服务只负责使用 thymeleaf 的模板技术生成静态页