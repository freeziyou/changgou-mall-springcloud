package com.changgou.goods.feign;

import com.changgou.goods.pojo.Spu;
import entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * @author Dylan Guo
 * @date 9/27/2020 09:50
 * @description TODO
 */
@FeignClient(name = "goods")
@RequestMapping("/spu")
public interface SpuFeign {

    /**
     * 根据 SpuID 查询 Spu 信息
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<Spu> findById(@PathVariable Long id);

}
