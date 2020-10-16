package com.changgou.oauth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Dylan Guo
 * @date 10/11/2020 18:33
 * @description TODO
 */
@Controller
@RequestMapping(value = "/oauth")
public class LoginRedirect {

    /**
     * 跳转到登录页面
     *
     * @return
     */
    @GetMapping(value = "/login")
    public String login(@RequestParam(value = "FROM", required = false, defaultValue = "") String from, Model model) {
        // 存储 from
        model.addAttribute("from", from);
        return "login";
    }
}
