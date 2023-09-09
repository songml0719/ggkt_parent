package com.atguigu.ggkt.wechat.service;

import java.util.Map;

public interface MessageService {
    String receiveMessage(Map<String, String> param);

    void pushPayMessage(long id);
}
