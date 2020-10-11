package com.changgou.user.dao;

import com.changgou.user.pojo.User;
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
public interface UserMapper extends Mapper<User> {

    /**
     * 添加积分
     *
     * @param username
     * @param points
     */
    @Update("update tb_user set points=points+#{points} where username=#{username}")
    void addPoints(@Param("username") String username, @Param("points") Integer points);
}
