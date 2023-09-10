package com.atguigu.ggkt.order.service.impl;

import com.atguigu.ggkt.exception.GgktException;
import com.atguigu.ggkt.order.service.WXPayService;
import com.atguigu.ggkt.utils.HttpClientUtils;
import com.github.wxpay.sdk.WXPayUtil;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class WXPayServiceImpl implements WXPayService {


//    @Override
//    public Map<String, Object> createJsapi(String orderNo) {
//        Map<String, String> paramMap = new HashMap<>();
//        paramMap.put("appid", "wxf913bfa3a2c7eeeb");
//        paramMap.put("mch_id", "1481962542");
//        paramMap.put("nonce_str", WXPayUtil.generateNonceStr());
//        paramMap.put("body", "test");
//        paramMap.put("out_trade_no", orderNo);
//        paramMap.put("total_fee", "1");
//        paramMap.put("spbill_create_ip", "49.123.88.83");
//        paramMap.put("notify_url", "http://glkt.atguigu.cn/api/order/wxPay/notify");
//        paramMap.put("trade_type", "JSAPI");
//        paramMap.put("openid", "oQTXC56lAy3xMOCkKCImHtHoLLN4");
//
//        try {
//            HttpClientUtils client = new HttpClientUtils("https://api.mch.weixin.qq.com/pay/unifiedorder");
//            String paramXml = WXPayUtil.generateSignedXml(paramMap, "MXb72b9RfshXZD4FRGV5KLqmv5bx9LT9");
//            client.setXmlParam(paramXml);
//            client.setHttps(true);
//            client.post();
//            String xml = client.getContent();
//            System.out.println("xml:" + xml);
//
//            Map<String, String> resultMap = WXPayUtil.xmlToMap(xml);
//            if (null != resultMap.get("result_code") && !"SUCCESS".equals(resultMap.get("result_code"))) {
//                throw new GgktException(20001, "支付失败");
//            }
//            Map<String, String> parameterMap = new HashMap<>();
//            String prepayId = String.valueOf(resultMap.get("prepay_id"));
//            String packages = "prepay_id=" + prepayId;
//            parameterMap.put("appId", "wxf913bfa3a2c7eeeb");
//            parameterMap.put("nonceStr", resultMap.get("nonce_str"));
//            parameterMap.put("package", packages);
//            parameterMap.put("signType", "MD5");
//            parameterMap.put("timeStamp", String.valueOf(new Date().getTime()));
//            String sign = WXPayUtil.generateSignature(parameterMap, "MXb72b9RfshXZD4FRGV5KLqmv5bx9LT9");
//
//            Map<String, Object> result = new HashMap();
//            result.put("appId", "wxf913bfa3a2c7eeeb");
//            result.put("timeStamp", parameterMap.get("timeStamp"));
//            result.put("nonceStr", parameterMap.get("nonceStr"));
//            result.put("signType", "MD5");
//            result.put("paySign", sign);
//            result.put("package", packages);
//            System.out.println(result);
//            return result;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

    @Override
    public Map<String, Object> createJsapi(String orderNo) {
        return null;
    }

//    @Override
//    public Map<String, String> queryPayStatus(String orderNo) {
//        Map paramMap = new HashMap<>();
//        paramMap.put("appid", "wxf913bfa3a2c7eeeb");
//        paramMap.put("mch_id", "1481962542");
//        paramMap.put("out_trade_no", orderNo);
//        paramMap.put("nonce_str", WXPayUtil.generateNonceStr());
//        try {
//            HttpClientUtils client = new HttpClientUtils("https://api.mch.weixin.qq.com/pay/orderquery");
//            client.setXmlParam(WXPayUtil.generateSignedXml(paramMap, "MXb72b9RfshXZD4FRGV5KLqmv5bx9LT9"));
//            client.setHttps(true);
//            client.post();
//            String xml = client.getContent();
//            System.out.println(xml);
//            Map<String, String> resultMap = WXPayUtil.xmlToMap(xml);
//            return resultMap;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

    @Override
    public Map<String, String> queryPayStatus(String orderNo) {
        return null;
    }
}
