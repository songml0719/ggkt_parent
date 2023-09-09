package com.atguigu.ggkt.vod.service.impl;

import com.atguigu.ggkt.exception.GgktException;
import com.atguigu.ggkt.model.vod.Video;
import com.atguigu.ggkt.vod.service.VideoService;
import com.atguigu.ggkt.vod.service.VodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class VodServiceImpl implements VodService {

    @Autowired
    private VideoService videoService;

    private String appId = "";

    @Override
    public String uploadVideo() {
        try {
            return "123456789";
        } catch (Exception e) {
            throw new GgktException(20001, "上传视频失败");
        }
    }

    @Override
    public void removeVideo(String fileId) {
        try {

        } catch (Exception e) {
            throw new GgktException(20001, "删除视频失败");
        }
    }

    @Override
    public Map<String, Object> getPlayAuth(Long courseId, Long videoId) {
        Video video = videoService.getById(videoId);
        if (video == null) {
            throw new GgktException(20001, "小节信息不存在");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("videoSourceId", video.getVideoSourceId());
        map.put("appId", appId);
        return map;
    }
}
