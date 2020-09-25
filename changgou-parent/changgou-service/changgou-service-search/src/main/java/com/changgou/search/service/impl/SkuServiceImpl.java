package com.changgou.search.service.impl;

import com.alibaba.fastjson.JSON;
import com.changgou.search.dao.SkuEsMapper;
import com.changgou.goods.feign.SkuFeign;
import com.changgou.goods.pojo.Sku;
import com.changgou.search.pojo.SkuInfo;
import com.changgou.search.service.SkuService;
import entity.Result;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * @author Dylan Guo
 * @date 9/22/2020 14:38
 * @description TODO
 */
@Service
public class SkuServiceImpl implements SkuService {

    @Autowired
    private SkuFeign skuFeign;

    @Autowired
    private SkuEsMapper skuEsMapper;

    @Autowired
    private ElasticsearchTemplate esTemplate;

    @Override
    public Map<String, Object> search(Map<String, String> searchMap) throws Exception {
        NativeSearchQueryBuilder builder = buildBasicQuery(searchMap);

        // 集合搜索
        Map<String, Object> resultMap = searchList(builder);

        // 当用户选择了分类，将分类作为搜索条件，则不需要对分类进行分组搜索，因为分组搜索的数据是用于显示分类搜索条件
        if (searchMap == null || StringUtils.isEmpty(searchMap.get("category"))) {
            // 分类分组搜索
            List<String> categoryList = searchCategoryList(builder);
            resultMap.put("categoryList", categoryList);
        }

        // 当用户选择了品牌，将品牌作为搜索条件，则不需要对品牌进行分组搜索，因为分组搜索的数据是用于显示品牌搜索条件
        if (searchMap == null || StringUtils.isEmpty(searchMap.get("brand"))) {
            // 查询品牌集合
            List<String> brandList = searchBrandList(builder);
            resultMap.put("brandList", brandList);
        }

        // 规格查询
        Map<String, Set<String>> specList = searchSpecList(builder);
        resultMap.put("specList", specList);

        return resultMap;
    }

    private NativeSearchQueryBuilder buildBasicQuery(Map<String, String> searchMap) {
        // 创建查询对象的构件对象, 用于封装各种搜索条件
        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();

        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

        if (searchMap != null && searchMap.size() > 0) {
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
        }

        // 将 boolQueryBuilder 对象填充给 nativeSearchQueryBuilder
        builder.withQuery(boolQueryBuilder);
        return builder;
    }

    /**
     * 集合搜索
     *
     * @param builder 查询对象的构件对象
     * @return resultMap
     */
    private Map<String, Object> searchList(NativeSearchQueryBuilder builder) {

        // 执行搜索, 返回搜索结果集的封装, SkuInfo.class 为搜索的结果集需要转换的类型
        AggregatedPage<SkuInfo> skuPage = esTemplate.queryForPage(builder.build(), SkuInfo.class);

        // 返回结果
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("rows", skuPage.getContent());
        resultMap.put("total", skuPage.getTotalElements());
        resultMap.put("totalPages", skuPage.getTotalPages());
        return resultMap;
    }

    /**
     * 规格分组查询
     *
     * @param builder 查询对象的构件对象
     * @return brandList
     */
    private Map<String, Set<String>> searchSpecList(NativeSearchQueryBuilder builder) {
        // 分组查询规格集合
        builder.addAggregation(AggregationBuilders.terms("skuSpec").field("spec.keyword").size(10000));
        AggregatedPage<SkuInfo> aggregatedPage = esTemplate.queryForPage(builder.build(), SkuInfo.class);

        // 获取分组数据
        StringTerms stringTerms = aggregatedPage.getAggregations().get("skuSpec");

        List<String> specList = new ArrayList<>();
        for (StringTerms.Bucket bucket : stringTerms.getBuckets()) {
            // bucket 为其中一个规格名字
            specList.add(bucket.getKeyAsString());
        }

        Map<String, Set<String>> allSpec = putAllSpec(specList);

        return allSpec;
    }

    /**
     * 规格汇总合并
     *
     * @param specList
     * @return
     */
    private Map<String, Set<String>> putAllSpec(List<String> specList) {
        // 合并后的 Map 对象
        Map<String, Set<String>> allSpec = new HashMap<>();

        // 1.循环 specList
        for (String spec : specList) {
            // 2.将每个 JSON 字符串转成 Map
            Map<String, String> specMap = JSON.parseObject(spec, Map.class);
            // 4.合并流程
            for (Map.Entry<String, String> entry : specMap.entrySet()) {
                // 4.1取出当前 Map, 并且获取对应的 Key 以及对应 value
                String key = entry.getKey();
                String value = entry.getValue();
                // 4.2将当前循环的数据合并到一个 Map<String, Set<String>> 中
                // 从 allSpec 中获取当前规格对应的 Set 集合数据
                Set<String> specSet = allSpec.get(key);
                if (specSet == null) {
                    // 如果之前的 allSpec 中没有该规格
                    specSet = new HashSet<>();
                }
                specSet.add(value);
                allSpec.put(key, specSet);
            }
        }
        return allSpec;
    }

    /**
     * 品牌分组查询
     *
     * @param builder 查询对象的构件对象
     * @return brandList
     */
    private List<String> searchBrandList(NativeSearchQueryBuilder builder) {
        // 分组查询品牌集合
        builder.addAggregation(AggregationBuilders.terms("skuBrand").field("brandName"));
        AggregatedPage<SkuInfo> aggregatedPage = esTemplate.queryForPage(builder.build(), SkuInfo.class);

        // 获取分组数据
        StringTerms stringTerms = aggregatedPage.getAggregations().get("skuBrand");

        List<String> brandList = new ArrayList<>();
        for (StringTerms.Bucket bucket : stringTerms.getBuckets()) {
            // bucket 为其中一个品牌名字
            brandList.add(bucket.getKeyAsString());
        }
        return brandList;
    }

    /**
     * 分组查询 分类集合
     *
     * @param builder 查询对象的构件对象
     * @return categoryList
     */
    private List<String> searchCategoryList(NativeSearchQueryBuilder builder) {
        // addAggregation(): 添加一个聚合操作
        // terms("skuCategory"): 取别名
        // field("categoryName"): 表示根据哪个字段进行分组查询
        builder.addAggregation(AggregationBuilders.terms("skuCategory").field("categoryName"));
        AggregatedPage<SkuInfo> aggregatedPage = esTemplate.queryForPage(builder.build(), SkuInfo.class);

        // 获取分组数据
        // aggregatedPage.getAggregations(): 获取的是集合, 可以根据多个字段进行分组
        // get("skuCategory"): 获取指定字段的集合数
        StringTerms stringTerms = aggregatedPage.getAggregations().get("skuCategory");

        List<String> categoryList = new ArrayList<>();
        for (StringTerms.Bucket bucket : stringTerms.getBuckets()) {
            // bucket 为其中一个分类
            categoryList.add(bucket.getKeyAsString());
        }
        return categoryList;
    }

    /**
     * 从数据库导入数据到 ES
     */
    @Override
    public void importSku() {
        // Feign 调用, 查询List<Sku>
        Result<List<Sku>> skuListResult = skuFeign.findByStatus("1");
        // 将 List<Sku> 转成 List<SkuInfo>
        List<SkuInfo> skuInfos = JSON.parseArray(JSON.toJSONString(skuListResult.getData()), SkuInfo.class);
        // 调用 Dao 实现数据批量导入
        for (SkuInfo skuInfo : skuInfos) {
            Map<String, Object> specMap = JSON.parseObject(skuInfo.getSpec());
            skuInfo.setSpecMap(specMap);
        }
        skuEsMapper.saveAll(skuInfos);
    }
}
