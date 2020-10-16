package com.changgou.order.mq.listener;

import com.alibaba.fastjson.JSON;
import com.changgou.order.service.OrderService;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author Dylan Guo
 * @date 10/16/2020 14:02
 * @description TODO
 */
@Component
@RabbitListener(queues = "${mq.pay.queue.order}")
public class OrderMessageListener {

    @Autowired
    private OrderService orderService;

    /**
     * 支付结果监听
     *
     * @param message
     */
    @RabbitHandler
    public void getMessage(String message) throws Exception {
        // 支付结果
        Map<String, String> resultMap = JSON.parseObject(message, Map.class);
        System.out.println("监听到的支付结果：" + resultMap);

        // 通信标识 return_code
        String return_code = resultMap.get("return_code");

        if (return_code.equals("SUCCESS")) {
            // 业务结果 result_code
            String result_code = resultMap.get("result_code");
            // 订单号 out_trade_no
            String out_trade_no = resultMap.get("out_trade_no");

            // 支付成功，修改订单状态
            if (result_code.equals("SUCCESS")) {
                // 修改订单状态
                orderService.updateStatus(out_trade_no, resultMap.get("time_end"), resultMap.get("transaction_id"));
            } else {
                // 支付失败，关闭支付，取消订单，回滚库存
                orderService.delete(out_trade_no);
            }
        }
    }
}
