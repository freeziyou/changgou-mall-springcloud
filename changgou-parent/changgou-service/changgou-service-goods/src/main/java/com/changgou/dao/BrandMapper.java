package com.changgou.dao;

import com.changgou.goods.pojo.Brand;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author Dylan Guo
 * @date 9/16/2020 12:51
 * @description DAO 使用通用 Mapper
 *          增加数据, Mapper.insert()
 *          增加数据, Mapper.insertSelective()
 *
 *          修改数据, Mapper.update()
 *          修改数据, Mapper.updateByPrimeKey(T)
 *          ...
 */
public interface BrandMapper extends Mapper<Brand> {

}
