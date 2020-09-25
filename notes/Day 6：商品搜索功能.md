## Day 6：商品搜索功能

- 条件筛选

- 多条件搜索[品牌、规格条件搜索]

  ```java
  // 获取关键字
  String keywords = searchMap.get("keywords");
  // 如果关键词不为空, 则搜索关键词数据
  if (!StringUtils.isEmpty(keywords)) {
      // 设置查询条件
      boolQueryBuilder.must(QueryBuilders.queryStringQuery(keywords).field("name"));
  }
  
  // 输入了分类过滤 -> category
  if (!StringUtils.isEmpty(searchMap.get("category"))) {
      // 设置查询条件
      boolQueryBuilder.must(QueryBuilders.termQuery("categoryName", searchMap.get("category")));
  }
  
  // 输入了品牌过滤 -> brand
  if (!StringUtils.isEmpty(searchMap.get("brand"))) {
      // 设置查询条件
      boolQueryBuilder.must(QueryBuilders.termQuery("brandName", searchMap.get("brand")));
  }
  ```

  

- 规格过滤

  ```java
  // 规格过滤
  for (Map.Entry<String, String> entry : searchMap.entrySet()) {
      String key = entry.getKey();
      // 如果 key 以 spec_ 开始，则表示规格筛选查询
      if (key.startsWith("spec_")) {
          // 规格条件的值
          String value = entry.getValue();
          // spec_网络，前 5 个去掉 = 网络
          boolQueryBuilder.must(QueryBuilders.termQuery("specMap." + key.substring(5) + ".keyword", value));
      }
  }
  ```

  

- 价格区间搜索

  ```java
  // 价格区间过滤
  String price = searchMap.get("price");
  if (!StringUtils.isEmpty(price)) {
      // 0-500元 500-1000元 1000-5000元 5000元以上
      // 去掉中文 元 和 以上
      price = price.replace("元", "").replace("以上", "");
      // prices[] 根据 - 分割
      String[] prices = price.split("-");
      // prices[x, y] x 一定不为空，y 有可能为 null
      if (prices.length > 0) {
          // price > prices[0]
          boolQueryBuilder.must(QueryBuilders.rangeQuery("price").gt(Integer.parseInt(prices[0])));
          // price <= prices[1]
          if (prices.length == 2) {
              boolQueryBuilder.must(QueryBuilders.rangeQuery("price").lte(Integer.parseInt(prices[1])));
          }
      }
  }
  ```

  

- 搜索分页

- 搜索排序

  ```java
  // 排序
  String sortField = searchMap.get("sortField");
  String sortRule = searchMap.get("sortRule");
  if (!StringUtils.isEmpty(sortField) && !StringUtils.isEmpty(sortRule)) {
      builder.withSort(
          // 指定排序字段
          new FieldSortBuilder(sortField)
          // 指定排序规则
          .order(SortOrder.valueOf(sortRule)));
  }
  ```

  

- 搜索高亮

  - 获取执行查询后的所有数据
  - 获取非高亮数据
  - 获取某个高亮字段的数据
  - 取出高亮数据
  - 将非高亮数据的指定属性替换成高亮数据
  
- 优化代码

![分组查询优化代码](http://dylanguo.xyz/img/分组查询优化代码.png)

  - 分组查询的时候查询了很多次，可以合并为一次分组查询

    ```java
    	/**
         * 分组查询: 分类分组，规格分组，品牌分组
         *
         * @param builder 查询对象的构件对象
         * @return categoryList
         */
        private Map<String, Object> searchGroupList(NativeSearchQueryBuilder builder, Map<String, String> searchMap) {
            // 存储所有分组树结果
            HashMap<String, Object> groupMapResult = new HashMap<>();
    
            // 当用户选择了分类，将分类作为搜索条件，则不需要对分类进行分组搜索，因为分组搜索的数据是用于显示分类搜索条件
            if (searchMap == null || StringUtils.isEmpty(searchMap.get("category"))) {
                builder.addAggregation(AggregationBuilders.terms("skuCategory").field("categoryName"));
            }
    
            // 当用户选择了品牌，将品牌作为搜索条件，则不需要对品牌进行分组搜索，因为分组搜索的数据是用于显示品牌搜索条件
            if (searchMap == null || StringUtils.isEmpty(searchMap.get("brand"))) {
                builder.addAggregation(AggregationBuilders.terms("skuBrand").field("brandName"));
            }
    
            // 规格查询
            builder.addAggregation(AggregationBuilders.terms("skuSpec").field("spec.keyword"));
            AggregatedPage<SkuInfo> aggregatedPage = esTemplate.queryForPage(builder.build(), SkuInfo.class);
    
            // 获取分类分组数据
            if (searchMap == null || StringUtils.isEmpty(searchMap.get("category"))) {
                StringTerms categoryTerms = aggregatedPage.getAggregations().get("skuCategory");
                List<String> categoryList = getGroupList(categoryTerms);
                groupMapResult.put("categoryList", categoryList);
            }
    
            // 获取品牌分组数据
            if (searchMap == null || StringUtils.isEmpty(searchMap.get("brand"))) {
                StringTerms brandTerms = aggregatedPage.getAggregations().get("skuBrand");
                List<String> brandList = getGroupList(brandTerms);
                groupMapResult.put("brandList", brandList);
            }
    
            // 获取规格分组集合数据
            StringTerms specTerms = aggregatedPage.getAggregations().get("skuSpec");
            List<String> specList = getGroupList(specTerms);
            // 实现合并操作
            Map<String, Set<String>> specMap = putAllSpec(specList);
            groupMapResult.put("specList", specMap);
    
            return groupMapResult;
        }
    ```

    