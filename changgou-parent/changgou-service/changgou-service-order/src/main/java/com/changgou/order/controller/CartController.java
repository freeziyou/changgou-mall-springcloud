package com.changgou.order.controller;

import com.changgou.order.pojo.OrderItem;
import com.changgou.order.service.CartService;
import entity.Result;
import entity.StatusCode;
import entity.TokenDecode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author Dylan Guo
 * @date 10/10/2020 13:27
 * @description 购物车操作
 */
@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    /**
     * 购物车列表
     *
     * @return result
     */
    @GetMapping("/list")
    public Result<List<OrderItem>> list() {
        // 用户的令牌信息->解析令牌信息->username
        Map<String, String> userInfo = TokenDecode.getUserInfo();
        System.out.println(userInfo);
        String username = userInfo.get("username");

        // 查询购物车列表
        List<OrderItem> orderItems = cartService.list(username);
        return new Result<List<OrderItem>>(true, StatusCode.OK, "购物车列表查询成功!", orderItems);
    }

    @GetMapping("/add")
    public Result add(Integer num, Long id) {
        cartService.add(num, id, "szitheima");
        return new Result(true, StatusCode.OK, "加入购物车成功!");
    }

}
