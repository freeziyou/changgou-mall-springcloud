package com.changgou.dao;

import com.changgou.goods.pojo.Album;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author Dylan Guo
 * @date 9/17/2020 22:00
 * @description TODO
 */
@Repository
public interface AlbumMapper extends Mapper<Album> {
}
