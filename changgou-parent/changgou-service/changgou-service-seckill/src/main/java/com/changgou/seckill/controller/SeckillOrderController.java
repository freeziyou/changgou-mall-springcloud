package com.changgou.seckill.controller;

import com.changgou.seckill.service.SeckillOrderService;
import entity.Result;
import entity.SeckillStatus;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Dylan Guo
 * @date 9/17/2020 22:14
 * @description TODO
 */

@RestController
@RequestMapping("/seckillOrder")
@CrossOrigin
public class SeckillOrderController {

    @Autowired
    private SeckillOrderService seckillOrderService;

    /**
     * 抢单状态查询
     *
     * @return
     */
    @GetMapping("/query")
    public Result queryStatus() {
        String username = "szitheima";
        SeckillStatus seckillStatus = seckillOrderService.queryStatus(username);

        // 查询成功
        if (seckillStatus != null) {
            return new Result(true,StatusCode.OK,"查询状态成功!",seckillStatus);
        }

        return new Result(false,StatusCode.NOTFOUNDERROR, "抢单失败!");
    }

    /**
     * 添加秒杀订单
     *
     * @param time
     * @param id
     * @return
     */
    @RequestMapping("/add")
    public Result add(String time, Long id) {
        String username = "szitheima";
        seckillOrderService.add(time, id, username);
        return new Result(true, StatusCode.OK, "正在排队...");
    }
}
