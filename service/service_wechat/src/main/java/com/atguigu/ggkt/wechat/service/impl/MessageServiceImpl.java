package com.atguigu.ggkt.wechat.service.impl;

import com.atguigu.ggkt.client.course.CourseFeignClient;
import com.atguigu.ggkt.model.vod.Course;
import com.atguigu.ggkt.wechat.service.MessageService;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private CourseFeignClient courseFeignClient;

    @Autowired
    private WxMpService wxMpService;

    @Override
    public String receiveMessage(Map<String, String> param) {
        String content = "";
        String msgType = param.get("MsgType");
        switch (msgType) {
            case "text":
                content = this.search(param);
                break;
            case "event":
                String event = param.get("Event");
                String eventKey = param.get("EventKey");
                if ("subscribe".equals(event)) {
                    content = this.subscribe(param);
                } else if ("unsubscribe".equals(event)) {
                    content = this.unsubscribe(param);
                } else if ("CLICK".equals(event) && "aboutUs".equals(eventKey)) {
                    content = this.aboutUs(param);
                } else {
                    content = "success";
                }
                break;
            default:
                content = "success";
        }
        return content;
    }

    @Override
    public void pushPayMessage(long id) {
        String openid = "oLR-T6sSWk_a2S_BkO02TAJI-3Ew";
        WxMpTemplateMessage templateMessage = WxMpTemplateMessage.builder()
                .toUser(openid)
                .templateId("85swdHjuW9k3tKoOtvsFlo6KZFtbWt2cFE6UNJpE-ho")
                .url("http://3f3bebed.r11.cpolar.top/#/pay/" + id)
                .build();
        templateMessage.addData(new WxMpTemplateData("first", "您有一笔订单支付成功。", "#272727"));
        templateMessage.addData(new WxMpTemplateData("keyword1", "1314520", "#272727"));
        templateMessage.addData(new WxMpTemplateData("keyword2", "java基础课程", "#272727"));
        templateMessage.addData(new WxMpTemplateData("keyword3", "2023-06-20", "#272727"));
        templateMessage.addData(new WxMpTemplateData("keyword4", "100", "#272727"));
        templateMessage.addData(new WxMpTemplateData("remark", "如有疑问，随时咨询！", "#272727"));
        try {
            String msg = wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
    }

    private String search(Map<String, String> param) {
        String fromusername = param.get("FromUserName");
        String tousername = param.get("ToUserName");
        String content = param.get("Content");
        Long createTime = new Date().getTime() / 1000;
        StringBuffer text = new StringBuffer();
        List<Course> courseList = courseFeignClient.findByKeyword(content);
        if (CollectionUtils.isEmpty(courseList)) {
            text = this.text(param, "请重新输入关键字，没有匹配到相关视频课程");
        } else {
            Random random = new Random();
            int num = random.nextInt(courseList.size());
            Course course = courseList.get(num);
            StringBuffer articles = new StringBuffer();
            articles.append("<item>");
            articles.append("<Title><![CDATA[" + course.getTitle() + "]]></Title>");
            articles.append("<Description><![CDATA[" + course.getTitle() + "]]></Description>");
            articles.append("<PicUrl><![CDATA[" + course.getCover() + "]]></PicUrl>");
            articles.append("<Url><![CDATA[http://glkt.atguigu.cn/#/liveInfo/" + course.getId() + "]]></Url>");
            articles.append("</item>");
            text.append("<xml>");
            text.append("<ToUserName><![CDATA[" + fromusername + "]]></ToUserName>");
            text.append("<FromUserName><![CDATA[" + tousername + "]]></FromUserName>");
            text.append("<CreateTime><![CDATA[" + createTime + "]]></CreateTime>");
            text.append("<MsgType><![CDATA[news]]></MsgType>");
            text.append("<ArticleCount><![CDATA[1]]></ArticleCount>");
            text.append("<Articles>");
            text.append(articles);
            text.append("</Articles>");
            text.append("</xml>");
        }
        return text.toString();
    }

    private StringBuffer text(Map<String, String> param, String content) {
        String fromusername = param.get("FromUserName");
        String tousername = param.get("ToUserName");
        Long createTime = new Date().getTime() / 1000;
        StringBuffer text = new StringBuffer();
        text.append("<xml>");
        text.append("<ToUserName><![CDATA[" + fromusername + "]]></ToUserName>");
        text.append("<FromUserName><![CDATA[" + tousername + "]]></FromUserName>");
        text.append("<CreateTime><![CDATA[" + createTime + "]]></CreateTime>");
        text.append("<MsgType><![CDATA[text]]></MsgType>");
        text.append("<Content><![CDATA[" + content + "]]></Content>");
        text.append("</xml>");
        return text;
    }

    private String subscribe(Map<String, String> param) {
        return this.text(param, "感谢你关注“硅谷课堂”，可以根据关键字搜索您想看的视频教程，如：JAVA基础、Spring boot、大数据等").toString();
    }

    private String unsubscribe(Map<String, String> param) {
        return "success";
    }

    private String aboutUs(Map<String, String> param) {
        StringBuffer msg = this.text(param, "硅谷课堂现开设Java、HTML5前端+全栈、大数据、" +
                "全链路UI/UE设计、人工智能、大数据运维+Python自动化、" +
                "Android+HTML5混合开发等多门课程；同时，通过视频分享、" +
                "谷粒学苑在线课堂、大厂学苑直播课堂等多种方式，" +
                "满足了全国编程爱好者对多样化学习场景的需求，" +
                "已经为行业输送了大量IT技术人才。");
        return msg.toString();
    }
}
