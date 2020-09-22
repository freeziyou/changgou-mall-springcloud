package com.changgou.search.dao;

import com.changgou.search.pojo.SkuInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Dylan Guo
 * @date 9/22/2020 14:37
 * @description TODO
 */
@Repository
public interface SkuEsMapper extends ElasticsearchRepository<SkuInfo, Long> {
}
