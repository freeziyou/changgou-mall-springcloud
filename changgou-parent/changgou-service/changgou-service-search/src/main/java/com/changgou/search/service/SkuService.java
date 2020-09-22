package com.changgou.search.service;

import java.util.Map;

/**
 * @author Dylan Guo
 * @date 9/22/2020 14:38
 * @description TODO
 */
public interface SkuService {

    /**
     * 搜索
     * @param searchMap
     * @return
     */
    Map search(Map<String, String> searchMap) throws Exception;

    /**
     * 导入 SKU 数据
     */
    void importSku();
}
