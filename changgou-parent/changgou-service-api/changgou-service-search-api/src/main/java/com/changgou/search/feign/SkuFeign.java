package com.changgou.search.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @author Dylan Guo
 * @date 9/26/2020 15:17
 * @description TODO
 */
@FeignClient("search")
@RequestMapping("/search")
public interface SkuFeign {

    /**
     * 搜索
     */
    @GetMapping
    Map search(@RequestParam(required = false) Map<String, String> searchMap) throws Exception;

}
