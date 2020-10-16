package com.changgou.pay.service;

import java.util.Map;

/**
 * @author Dylan Guo
 * @date 10/16/2020 09:42
 * @description TODO
 */
public interface WeixinPayService {

    /**
     * 关闭支付
     *
     * @param orderId
     * @return
     */
    Map<String, String> closePay(Long orderId) throws Exception;

    /**
     * 查询微信支付状态
     *
     * @param outtradeno
     * @return
     */
    Map queryStatus(String outtradeno);

    /**
     * 创建二维码
     *
     * @param parameterMap
     * @return
     */
    Map createNative(Map<String, String> parameterMap);
}
