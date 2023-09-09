package com.atguigu.ggkt.vod.service;


import com.atguigu.ggkt.model.vod.Video;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author songml
 * @since 20230401
 */
public interface VideoService extends IService<Video> {

    void removeVideoByCourseId(Long id);

    void removeVideoById(Long id);
}
