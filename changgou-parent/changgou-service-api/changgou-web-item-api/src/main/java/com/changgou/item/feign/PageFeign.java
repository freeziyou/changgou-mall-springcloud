package com.changgou.item.feign;

import entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Dylan Guo
 * @date 9/27/2020 11:04
 * @description TODO
 */
@FeignClient(name = "item")
@RequestMapping("/page")
public interface PageFeign {

    /**
     * 根据 SpuID 生成静态页
     *
     * @param id
     * @return
     */
    @RequestMapping("/createHtml/{id}")
    Result createHtml(@PathVariable Long id);
}