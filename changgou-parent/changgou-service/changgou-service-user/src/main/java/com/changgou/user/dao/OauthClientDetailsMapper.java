package com.changgou.user.dao;
import com.changgou.user.pojo.OauthClientDetails;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author Dylan Guo
 * @date 9/17/2020 22:14
 * @description TODO
 */
@Repository
public interface OauthClientDetailsMapper extends Mapper<OauthClientDetails> {
}
