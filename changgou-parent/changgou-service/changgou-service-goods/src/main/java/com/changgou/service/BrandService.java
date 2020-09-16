package com.changgou.service;

import com.changgou.goods.pojo.Brand;

import java.util.List;

/**
 * @author Dylan Guo
 * @date 9/16/2020 13:14
 * @description TODO
 */
public interface BrandService {

    /**
     * 查询所有
     * @return 所有结果
     */
    List<Brand> findAll();
}
