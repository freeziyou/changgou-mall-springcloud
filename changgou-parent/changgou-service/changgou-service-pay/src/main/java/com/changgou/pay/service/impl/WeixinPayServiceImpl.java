package com.changgou.pay.service.impl;

import com.changgou.pay.service.WeixinPayService;
import com.github.wxpay.sdk.WXPayUtil;
import entity.HttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Dylan Guo
 * @date 10/16/2020 09:41
 * @description TODO
 */
@Service
public class WeixinPayServiceImpl implements WeixinPayService {

    /**
     * 应用ID
     */
    @Value("${weixin.appid}")
    private String appid;

    /**
     * 商户号
     */
    @Value("${weixin.partner}")
    private String partner;

    /**
     * 秘钥
     */
    @Value("${weixin.partnerkey}")
    private String partnerkey;

    /**
     * 支付回调地址
     */
    @Value("${weixin.notifyurl}")
    private String notifyurl;

    /**
     * 查询支付状态
     *
     * @param outtradeno
     * @return
     */
    @Override
    public Map queryStatus(String outtradeno) {
        try {
            // 参数
            Map<String, String> paramMap = new HashMap<>();
            // 应用ID
            paramMap.put("appid", appid);
            // 商户号
            paramMap.put("mch_id", partner);
            // 随机字符
            paramMap.put("nonce_str", WXPayUtil.generateNonceStr());
            // 商户订单号
            paramMap.put("out_trade_no", outtradeno);

            // Map 转成 xml 字符串, 可以携带签名
            String xmlParameters = null;
            xmlParameters = WXPayUtil.generateSignedXml(paramMap, partnerkey);

            // URL 地址
            String url = "https://api.mch.weixin.qq.com/pay/orderquery";
            // 提交方式
            HttpClient httpClient = new HttpClient(url);
            // 提交参数
            httpClient.setXmlParam(xmlParameters);
            // 执行请求
            httpClient.post();

            // 获取返回的数据
            String result = httpClient.getContent();

            // 返回数据转成 Map
            Map<String, String> resultMap = WXPayUtil.xmlToMap(result);
            return resultMap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 创建二维码
     *
     * @param parameterMap
     * @return
     */
    @Override
    public Map createNative(Map<String, String> parameterMap) {
        try {
            // 参数
            Map<String, String> paramMap = new HashMap<>();
            // 应用ID
            paramMap.put("appid", appid);
            // 商户号
            paramMap.put("mch_id", partner);
            // 随机字符
            paramMap.put("nonce_str", WXPayUtil.generateNonceStr());
            // 订单描述
            paramMap.put("body", "畅购");
            // 商户订单号
            paramMap.put("out_trade_no", parameterMap.get("outtradeno"));
            // 交易金额
            paramMap.put("total_fee", parameterMap.get("totalfee"));
            // 终端IP
            paramMap.put("spbill_create_ip", "127.0.0.1");
            // 回调地址
            paramMap.put("notify_url", notifyurl);
            // 交易类型
            paramMap.put("trade_type", "NATIVE");

            // Map 转成 xml 字符串, 可以携带签名
            String xmlParameters = null;
            xmlParameters = WXPayUtil.generateSignedXml(paramMap, partnerkey);

            // URL 地址
            String url = "https://api.mch.weixin.qq.com/pay/unifiedorder";
            // 提交方式
            HttpClient httpClient = new HttpClient(url);
            // 提交参数
            httpClient.setXmlParam(xmlParameters);
            // 执行请求
            httpClient.post();

            // 获取返回的数据
            String result = httpClient.getContent();

            // 返回数据转成 Map
            Map<String, String> resultMap = WXPayUtil.xmlToMap(result);
            return resultMap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
