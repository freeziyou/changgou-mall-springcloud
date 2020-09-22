package com.changgou.search.controller;

import com.changgou.search.service.SkuService;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author Dylan Guo
 * @date 9/22/2020 15:29
 * @description TODO
 */
@RestController
@CrossOrigin
@RequestMapping("/search")
public class SkuController {

    @Autowired
    private SkuService skuService;

    /**
     * 搜索
     *
     * @param searchMap
     * @return
     */
    @GetMapping
    public Map search(@RequestParam(required = false) Map<String, String> searchMap) throws Exception {
        return skuService.search(searchMap);
    }

    /**
     * 导入数据
     *
     * @return
     */
    @GetMapping("/import")
    public Result importData() {
        skuService.importSku();
        return new Result(true, StatusCode.OK, "导入数据到索引库中成功!");
    }
}
