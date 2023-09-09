package com.atguigu.ggkt.vod.service.impl;


import com.atguigu.ggkt.model.vod.Video;
import com.atguigu.ggkt.vod.mapper.VideoMapper;
import com.atguigu.ggkt.vod.service.VideoService;
import com.atguigu.ggkt.vod.service.VodService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author songml
 * @since 20230401
 */
@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video> implements VideoService {

    @Autowired
    private VodService vodService;

    @Override
    public void removeVideoById(Long id) {
        Video video = baseMapper.selectById(id);
        String videoSourceId = video.getVideoSourceId();
        if (!StringUtils.isEmpty(videoSourceId)) {
            vodService.removeVideo(videoSourceId);
        }
        baseMapper.deleteById(id);
    }

    @Override
    public void removeVideoByCourseId(Long id) {
        QueryWrapper<Video> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", id);
        List<Video> videoList = baseMapper.selectList(wrapper);
        for (Video video : videoList) {
            String videoSourceId = video.getVideoSourceId();
            if (!StringUtils.isEmpty(videoSourceId)) {
                vodService.removeVideo(videoSourceId);
            }
        }
        baseMapper.delete(wrapper);
    }
}
