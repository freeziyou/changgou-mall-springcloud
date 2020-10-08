# Day 5：Elasticsearch、es导入数据、搜索	

- Elasticsearch安装
  - docker安装Elasticsearch
  - 系统参数问题
  - 跨域操作
  
- IK分词器配置

- Kibana的使用->==DSL语句==
  
  - Kibana->DSL语句操作->Elasticsearch
  
- ==ES导入商品搜索数据==

    1. 创建一个 JavaBean，在 JavaBean（SkuInfo）中添加索引库映射配置
    2. 创建 Feign，实现查询所有 Sku 集合
    3. 在搜索微服务中调用 Feign，查询所有 Sku 集合，并将 Sku 集合转换成 SkuInfo 集合
    4. Controller--->Service--->调用 Dao（继承 ElasticsearchRepository）实现数据导入到 ES 中
    
- ==关键词搜索->实现搜索流程代码的编写==
![商场规格](http://dylanguo.xyz/img/商场规格.png)
  
    - 我们先使用SpringDataElasticsearch实现一个简单的搜索功能，先实现根据关键字搜索，从上面搜索图片可以看得到，每次搜索的时候，除了关键字外，还有可能有品牌、分类、规格等，后台接收搜索条件使用Map接收比较合适。
  
- ==分类统计搜索==

    - 看下面的SQL语句，我们在执行搜索的时候，第1条SQL语句是执行搜，第2条语句是根据分类名字分组查看有多少分类，大概执行了2个步骤就可以获取数据结果以及分类统计，我们可以发现他们的搜索条件完全一样。

    ```
    -- 查询所有
    SELECT * FROM tb_sku WHERE name LIKE '%手机%';
    -- 根据分类名字分组查询
    SELECT category_name FROM  tb_sku WHERE name LIKE '%手机%' GROUP BY category_name;
    ```

​    