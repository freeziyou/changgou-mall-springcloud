package com.changgou.user.feign;

import com.changgou.user.pojo.User;
import entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Dylan Guo
 * @date 10/10/2020 11:22
 * @description TODO
 */
@FeignClient(name = "user")
@RequestMapping("/user")
public interface UserFeign {

    /**
     * 根据 ID 查询用户信息
     * @param id
     * @return
     */
    @GetMapping("/load/{id}")
    public Result<User> findById(@PathVariable String id);
}
