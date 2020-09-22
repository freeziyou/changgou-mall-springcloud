package com.changgou.search.service.impl;

import com.alibaba.fastjson.JSON;
import com.changgou.search.dao.SkuEsMapper;
import com.changgou.goods.feign.SkuFeign;
import com.changgou.goods.pojo.Sku;
import com.changgou.search.pojo.SkuInfo;
import com.changgou.search.service.SkuService;
import entity.Result;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        // 创建查询对象的构件对象, 用于封装各种搜索条件
        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();

        // 集合搜索
        Map<String, Object> resultMap = searchList(searchMap, builder);

        // 分类分组搜索
        List<String> categoryList = searchCategoryList(builder);
        resultMap.put("categoryList", categoryList);

        return resultMap;
    }

    /**
     * 集合搜索
     *
     * @param builder 查询对象的构件对象
     * @return resultMap
     */
    private Map<String, Object> searchList(Map<String, String> searchMap, NativeSearchQueryBuilder builder) {
        if (searchMap != null && searchMap.size() > 0) {
            // 获取关键字
            String keywords = searchMap.get("keywords");
            // 如果关键词不为空, 则搜索关键词数据
            if (!StringUtils.isEmpty(keywords)) {
                // 设置查询条件
                builder.withQuery(QueryBuilders.queryStringQuery(keywords).field("name"));
            }
        }

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
     * 导入数据
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
