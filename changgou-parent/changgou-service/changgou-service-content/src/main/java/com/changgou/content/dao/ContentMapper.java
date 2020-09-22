package com.changgou.content.dao;
import com.changgou.content.pojo.Content;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author Dylan Guo
 * @date 9/17/2020 22:14
 * @description TODO
 */
@Repository
public interface ContentMapper extends Mapper<Content> {
}
