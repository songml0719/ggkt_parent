package com.atguigu.ggkt.wechat.controller;

import com.alibaba.fastjson.JSONObject;
import com.atguigu.ggkt.result.Result;
import com.atguigu.ggkt.wechat.service.MessageService;
import com.atguigu.ggkt.wechat.utils.SHA1;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/wechat/message")
public class MessageController {

    private static final String token = "ggkt";

    @Autowired
    private MessageService messageService;

    @ApiOperation("服务器有效性验证")
    @GetMapping
    public String verifyToken(HttpServletRequest request) {
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");
        if (this.checkSignature(signature, timestamp, nonce)) {
            return echostr;
        }
        return echostr;
    }

    private boolean checkSignature(String signature, String timestamp, String nonce) {
        String[] str = new String[]{token, timestamp, nonce};
        Arrays.sort(str);
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < str.length; i++) {
            buffer.append(str[i]);
        }
        String temp = SHA1.encode(buffer.toString());
        return signature.equals(temp);
    }

    @ApiOperation("接收微信服务器发送来的消息")
    @PostMapping
    public String receiveMessage(HttpServletRequest request) throws Exception {
//        WxMpXmlMessage wxMpXmlMessage = WxMpXmlMessage.fromXml(request.getInputStream());
//        System.out.println(JSONObject.toJSONString(wxMpXmlMessage));
//        return "success";
        Map<String, String> param = this.parseXml(request);
        String message = messageService.receiveMessage(param);
        return message;
    }

    private Map<String, String> parseXml(HttpServletRequest request) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        InputStream inputStream = request.getInputStream();
        SAXReader reader = new SAXReader();
        Document document = reader.read(inputStream);
        Element root = document.getRootElement();
        List<Element> elementList = root.elements();
        for (Element e : elementList) {
            map.put(e.getName(), e.getText());
        }
        inputStream.close();
        inputStream = null;
        return map;
    }

    @ApiOperation("订单支付成功消息")
    @GetMapping("/pushPayMessage")
    public Result pushPayMessage() throws WxErrorException {
        messageService.pushPayMessage(1L);
        return Result.ok(null);
    }
}
