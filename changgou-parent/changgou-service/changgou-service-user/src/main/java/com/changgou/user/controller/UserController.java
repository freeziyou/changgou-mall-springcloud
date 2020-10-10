package com.changgou.user.controller;

import com.alibaba.fastjson.JSON;
import com.changgou.user.pojo.User;
import com.changgou.user.service.UserService;
import com.github.pagehelper.PageInfo;
import entity.BCrypt;
import entity.JwtUtil;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author Dylan Guo
 * @date 9/17/2020 22:14
 * @description TODO
 */

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public Result login(String username, String password, HttpServletResponse response, HttpServletRequest request) {
        // 查询用户信息
        User user = userService.findById(username);

        // 对比密码, 将明文密码加密后对比数据库中的加密密码
        if (BCrypt.checkpw(password, user.getPassword())) {
            // 创建用户令牌信息
            Map<String, Object> tokenMap = new HashMap<>();
            tokenMap.put("role","USER");
            tokenMap.put("success","SUCCESS");
            tokenMap.put("username",username);
            // 生成令牌
            String token = JwtUtil.createJWT(UUID.randomUUID().toString(), JSON.toJSONString(tokenMap), null);

            // 将令牌信息存入 Cookie 中
            Cookie cookie = new Cookie("Authorization", token);
            cookie.setDomain("localhost");
            cookie.setPath("/");
            response.addCookie(cookie);

            // 密码匹配，登陆成功
            return new Result(true, StatusCode.OK, "登陆成功!", token);
        }

        // 密码匹配失败，登陆失败
        return new Result(false, StatusCode.LOGINERROR, "账号或密码有误!");
    }

    /**
     * User 分页条件搜索实现
     *
     * @param user
     * @param page 当前页
     * @param size 每页显示多少条
     * @return
     */
    @PostMapping("/search/{page}/{size}")
    public Result<PageInfo> findPage(@RequestBody(required = false) User user, @PathVariable int page, @PathVariable int size) {
        // 调用 UserService 实现分页条件查询 User
        PageInfo<User> pageInfo = userService.findPage(user, page, size);
        return new Result(true, StatusCode.OK, "分页条件查询成功!", pageInfo);
    }

    /**
     * User 分页搜索实现
     *
     * @param page 当前页
     * @param size 每页显示多少条
     * @return
     */
    @GetMapping("/search/{page}/{size}")
    public Result<PageInfo> findPage(@PathVariable int page, @PathVariable int size) {
        // 调用 UserService 实现分页查询 User
        PageInfo<User> pageInfo = userService.findPage(page, size);
        return new Result<PageInfo>(true, StatusCode.OK, "分页查询成功!", pageInfo);
    }

    /**
     * 多条件搜索品牌数据
     *
     * @param user
     * @return
     */
    @PostMapping("/search")
    public Result<List<User>> findList(@RequestBody(required = false) User user) {
        //调用 UserService 实现条件查询 User
        List<User> list = userService.findList(user);
        return new Result<List<User>>(true, StatusCode.OK, "条件查询成功!", list);
    }

    /**
     * 根据 ID 删除品牌数据
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable String id) {
        // 调用 UserService 实现根据主键删除
        userService.delete(id);
        return new Result(true, StatusCode.OK, "删除成功!");
    }

    /**
     * 修改 User数据
     *
     * @param user
     * @param id
     * @return
     */
    @PutMapping("/{id}")
    public Result update(@RequestBody User user, @PathVariable String id) {
        // 设置主键值
        user.setUsername(id);
        // 调用 UserService 实现修改 User
        userService.update(user);
        return new Result(true, StatusCode.OK, "修改成功!");
    }

    /**
     * 新增 User 数据
     *
     * @param user
     * @return
     */
    @PostMapping
    public Result add(@RequestBody User user) {
        // 调用 UserService 实现添加 User
        userService.add(user);
        return new Result(true, StatusCode.OK, "添加成功!");
    }

    /**
     * 根据 ID 查询 User 数据
     *
     * @param id
     * @return
     */
    @GetMapping({"/{id}", "/load/{id}"})
    public Result<User> findById(@PathVariable String id) {
        // 调用 UserService 实现根据主键查询 User
        User user = userService.findById(id);
        return new Result<User>(true, StatusCode.OK, "ID 查询成功!", user);
    }

    /**
     * 查询 User 全部数据
     * 只允许 admin 访问
     * @return
     */
    @PreAuthorize("hasAnyRole('admin')")
    @GetMapping
    public Result<List<User>> findAll() {
        // 调用 UserService 实现查询所有 User
        List<User> list = userService.findAll();
        return new Result<List<User>>(true, StatusCode.OK, "查询成功!", list);
    }
}
