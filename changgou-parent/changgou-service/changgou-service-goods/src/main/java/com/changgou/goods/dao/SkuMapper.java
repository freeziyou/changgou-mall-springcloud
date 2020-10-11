package com.changgou.goods.dao;

import com.changgou.goods.pojo.Sku;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author Dylan Guo
 * @date 9/17/2020 22:14
 * @description TODO
 */
@Repository
public interface SkuMapper extends Mapper<Sku> {
    /**
     * 库存递减
     *
     * @param id
     * @param num
     * @return
     */
    @Update("update tb_sku set num=num-#{num} where id=#{id} and num >= #{num}")
    int decrCount(@Param("id") Long id, @Param("num") Integer num);
}
