package com.atguigu.ggkt.live.service;

import com.atguigu.ggkt.model.live.LiveCourseConfig;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 直播课程配置表 服务类
 * </p>
 *
 * @author songml
 * @since 20230401
 */
public interface LiveCourseConfigService extends IService<LiveCourseConfig> {

    //根据课程id查询配置信息
    LiveCourseConfig getCourseConfigCourseId(Long id);
}
