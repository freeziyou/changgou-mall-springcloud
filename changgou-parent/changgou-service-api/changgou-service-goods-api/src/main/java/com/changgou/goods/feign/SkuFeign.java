package com.changgou.goods.feign;

import com.changgou.goods.pojo.Sku;
import entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Dylan Guo
 * @date 9/22/2020 14:32
 * @description TODO
 */
@FeignClient(name = "goods")
@RequestMapping("/sku")
public interface SkuFeign {

    /**
     * 查询符合条件状态的 SKU 的列表
     *
     * @param status
     * @return
     */
    @GetMapping("/status/{status}")
    Result<List<Sku>> findByStatus(@PathVariable String status);

    /**
     * 根据条件搜索
     *
     * @param sku
     * @return
     */
    @PostMapping("/search")
    public Result<List<Sku>> findList(@RequestBody(required = false) Sku sku);

    /**
     * 根据 ID 查询 Sku 数据
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<Sku> findById(@PathVariable Long id);
}
