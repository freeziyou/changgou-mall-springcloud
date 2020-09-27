package com.changgou.item.service;

/**
 * @author Dylan Guo
 * @date 9/27/2020 09:55
 * @description TODO
 */
public interface PageService {

    /**
     * 根据商品的ID 生成静态页
     *
     * @param spuId
     */
    public void createPageHtml(Long spuId);

}
