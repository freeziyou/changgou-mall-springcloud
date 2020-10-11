package com.changgou.user.feign;

import com.changgou.user.pojo.User;
import entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Dylan Guo
 * @date 10/10/2020 11:22
 * @description TODO
 */
@FeignClient(name = "user")
@RequestMapping("/user")
public interface UserFeign {

    /**
     * 添加用户积分
     *
     * @param points
     * @return
     */
    @GetMapping("/points/add")
    public Result addPoints(@RequestParam Integer points);

    /**
     * 根据 ID 查询用户信息
     *
     * @param id
     * @return
     */
    @GetMapping("/load/{id}")
    public Result<User> findById(@PathVariable String id);
}
