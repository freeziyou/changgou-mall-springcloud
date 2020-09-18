package com.changgou.dao;

import com.changgou.goods.pojo.Category;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author Dylan Guo
 * @date 9/18/2020 13:32
 * @description TODO
 */
@Repository
public interface CategoryMapper extends Mapper<Category> {
}
