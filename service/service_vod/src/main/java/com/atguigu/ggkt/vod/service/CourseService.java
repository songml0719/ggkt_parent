package com.atguigu.ggkt.vod.service;


import com.atguigu.ggkt.model.vod.Course;
import com.atguigu.ggkt.vo.vod.CourseFormVo;
import com.atguigu.ggkt.vo.vod.CoursePublishVo;
import com.atguigu.ggkt.vo.vod.CourseQueryVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author songml
 * @since 20230401
 */
public interface CourseService extends IService<Course> {

    Map<String, Object> findPageCourse(Page<Course> pageParam, CourseQueryVo courseQueryVo);

    Long saveCourseInfo(CourseFormVo courseFormVo);

    CourseFormVo getCourseInfoById(Long id);

    void updateCourseId(CourseFormVo courseFormVo);

    CoursePublishVo getCoursePublishVo(Long id);

    void publishCourse(Long id);

    void removeCourseId(Long id);

    Map<String, Object> findPage(Page<Course> pageParam, CourseQueryVo courseQueryVo);

    Map<String, Object> getInfoById(Long courseId);

}
