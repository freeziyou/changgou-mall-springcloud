package com.changgou.goods.dao;

import com.changgou.goods.pojo.Brand;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author Dylan Guo
 * @date 9/17/2020 22:14
 * @description TODO
 */
@Repository
public interface BrandMapper extends Mapper<Brand> {

    /**
     * 根据分类查询品牌集合
     *
     * @param categoryId 分类 Id
     * @return
     */
    @Select("Select tb.* FROM tb_brand tb, tb_category_brand tcb WHERE tb.id=tcb.brand_id AND tcb.category_id = #{categoryId};")
    List<Brand> findByCategory(Integer categoryId);
}
