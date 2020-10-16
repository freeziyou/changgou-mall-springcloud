package com.changgou.pay.controller;

import com.alibaba.fastjson.JSON;
import com.changgou.pay.service.WeixinPayService;
import com.github.wxpay.sdk.WXPayUtil;
import entity.Result;
import entity.StatusCode;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Dylan Guo
 * @date 10/16/2020 10:07
 * @description TODO
 */
@RestController
@RequestMapping("/weixin/pay")
public class WeixinPayController {

    @Autowired
    private WeixinPayService weixinPayService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RequestMapping("/notify/url")
    public String notifyUrl(HttpServletRequest request) throws Exception {
        // 获取网络输入流
        ServletInputStream is = request.getInputStream();

        // 创建一个 OutputStream, 输入文件中
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = is.read(buffer)) != -1) {
            baos.write(buffer, 0, len);
        }
        // 微信支付结果的字节数组
        byte[] bytes = baos.toByteArray();
        String xmlResult = new String(bytes, "UTF-8");
        System.out.println(xmlResult);

        // XML 字符串转换成 Map
        Map<String, String> resultMap = WXPayUtil.xmlToMap(xmlResult);
        System.out.println(resultMap);

        // 发送支付结果给 MQ
        rabbitTemplate.convertAndSend("exchange.order", "queue.order", JSON.toJSONString(resultMap));

        //响应数据设置
        Map<String, String> respMap = new HashMap<>();
        respMap.put("return_code", "SUCCESS");
        respMap.put("return_msg", "OK");
        return WXPayUtil.mapToXml(respMap);
    }

    /**
     * 查询微信支付状态
     *
     * @param outtradeno
     * @return
     */
    @GetMapping("/status/query")
    public Result queryStatus(String outtradeno) {
        // 查询支付状态
        Map map = weixinPayService.queryStatus(outtradeno);
        return new Result(true, StatusCode.OK, "查询支付状态成功!", map);
    }

    /**
     * 创建二维码
     *
     * @param parameterMap
     * @return
     */
    @RequestMapping("/create/native")
    public Result createNative(@RequestParam Map<String, String> parameterMap) {
        Map<String, String> resultMap = weixinPayService.createNative(parameterMap);
        return new Result(true, StatusCode.OK, "创建二维码预付订单成功！", resultMap);
    }
}
