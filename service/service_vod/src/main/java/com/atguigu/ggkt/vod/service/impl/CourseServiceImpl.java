package com.atguigu.ggkt.vod.service.impl;


import com.atguigu.ggkt.model.vod.Course;
import com.atguigu.ggkt.model.vod.CourseDescription;
import com.atguigu.ggkt.model.vod.Subject;
import com.atguigu.ggkt.model.vod.Teacher;
import com.atguigu.ggkt.vo.vod.*;
import com.atguigu.ggkt.vod.mapper.CourseMapper;
import com.atguigu.ggkt.vod.service.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author songml
 * @since 20230401
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private CourseDescriptionService descriptionService;

    @Autowired
    private VideoService videoService;

    @Autowired
    private ChapterService chapterService;

    @Override
    public Map<String, Object> findPageCourse(Page<Course> pageParam, CourseQueryVo courseQueryVo) {

        String title = courseQueryVo.getTitle();
        Long subjectId = courseQueryVo.getSubjectId();
        Long subjectParentId = courseQueryVo.getSubjectParentId();
        Long teacherId = courseQueryVo.getTeacherId();

        QueryWrapper<Course> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(title)) {
            wrapper.like("title", title);
        }
        if (!StringUtils.isEmpty(subjectId)) {
            wrapper.eq("subject_id", subjectId);
        }
        if (!StringUtils.isEmpty(subjectParentId)) {
            wrapper.eq("subject_parent_id", subjectParentId);
        }
        if (!StringUtils.isEmpty(teacherId)) {
            wrapper.eq("teacher_id", teacherId);
        }

        Page<Course> pages = baseMapper.selectPage(pageParam, wrapper);
        long totalCount = pages.getTotal();
        long totalPage = pages.getPages();
        List<Course> list = pages.getRecords();

        list.stream().forEach(item -> {
            this.getNameById(item);
        });

        Map<String, Object> map = new HashMap<>();
        map.put("totalCount", totalCount);
        map.put("totalPage", totalPage);
        map.put("records", list);
        return map;
    }

    @Override
    public Long saveCourseInfo(CourseFormVo courseFormVo) {
        Course course = new Course();
        BeanUtils.copyProperties(courseFormVo, course);
        baseMapper.insert(course);

        CourseDescription courseDescription = new CourseDescription();
        courseDescription.setDescription(courseFormVo.getDescription());
        courseDescription.setId(course.getId());
        descriptionService.save(courseDescription);

        return course.getId();
    }

    @Override
    public CourseFormVo getCourseInfoById(Long id) {
        Course course = baseMapper.selectById(id);
        if (course == null) {
            return null;
        }
        CourseFormVo courseFormVo = new CourseFormVo();
        BeanUtils.copyProperties(course, courseFormVo);
        CourseDescription courseDescription = descriptionService.getById(id);
        if (courseDescription != null) {
            courseFormVo.setDescription(courseDescription.getDescription());
        }
        return courseFormVo;
    }

    @Override
    public void updateCourseId(CourseFormVo courseFormVo) {
        Course course = new Course();
        BeanUtils.copyProperties(courseFormVo, course);
        baseMapper.updateById(course);

        CourseDescription description = new CourseDescription();
        description.setId(course.getId());
        description.setDescription(courseFormVo.getDescription());
        descriptionService.updateById(description);
    }

    @Override
    public CoursePublishVo getCoursePublishVo(Long id) {
        return baseMapper.selectCoursePublishVoById(id);
    }

    @Override
    public void publishCourse(Long id) {
        Course course = baseMapper.selectById(id);
        course.setStatus(1);
        course.setPublishTime(new Date());
        baseMapper.updateById(course);
    }

    @Override
    public void removeCourseId(Long id) {
        videoService.removeVideoByCourseId(id);
        chapterService.removeChapterByCourseId(id);
        descriptionService.removeById(id);
        baseMapper.deleteById(id);
    }

    @Override
    public Map<String, Object> findPage(Page<Course> pageParam, CourseQueryVo courseQueryVo) {
        String title = courseQueryVo.getTitle();
        Long subjectId = courseQueryVo.getSubjectId();
        Long subjectParentId = courseQueryVo.getSubjectParentId();
        Long teacherId = courseQueryVo.getTeacherId();
        QueryWrapper<Course> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(title)) {
            wrapper.like("title", title);
        }
        if (!StringUtils.isEmpty(subjectId)) {
            wrapper.eq("subject_id", subjectId);
        }
        if (!StringUtils.isEmpty(subjectParentId)) {
            wrapper.eq("subject_parent_id", subjectParentId);
        }
        if (!StringUtils.isEmpty(teacherId)) {
            wrapper.eq("teacher_id", teacherId);
        }

        Page<Course> pages = baseMapper.selectPage(pageParam, wrapper);
        long totalCount = pages.getTotal();
        long totalPage = pages.getPages();
        long currentPage = pages.getCurrent();
        long size = pages.getSize();
        List<Course> records = pages.getRecords();
        records.stream().forEach(item -> {
            this.getTeacherAndSubjectName(item);
        });

        Map<String, Object> map = new HashMap<>();
        map.put("totalCount", totalCount);
        map.put("totalPage", totalPage);
        map.put("records", records);
        return map;
    }

    private Course getTeacherAndSubjectName(Course course) {
        Long teacherId = course.getTeacherId();
        Teacher teacher = teacherService.getById(teacherId);
        if (teacher != null) {
            course.getParam().put("teacherName", teacher.getName());
        }
        Long subjectParentId = course.getSubjectParentId();
        Subject oneSubject = subjectService.getById(subjectParentId);
        if (oneSubject != null) {
            course.getParam().put("subjectParentTitle", oneSubject.getTitle());
        }
        Long subjectId = course.getSubjectId();
        Subject twoSubject = subjectService.getById(subjectId);
        if (twoSubject != null) {
            course.getParam().put("subjectTitle", twoSubject.getTitle());
        }
        return course;
    }

    @Override
    public Map<String, Object> getInfoById(Long courseId) {
        Course course = baseMapper.selectById(courseId);
        course.setViewCount(course.getViewCount() + 1);
        baseMapper.updateById(course);

        CourseVo courseVo = baseMapper.selectCourseVoById(courseId);
        List<ChapterVo> chapterVoList = chapterService.getTreeList(courseId);
        CourseDescription courseDescription = descriptionService.getById(courseId);
        Teacher teacher = teacherService.getById(course.getTeacherId());

        Map<String, Object> map = new HashMap();
        map.put("courseVo", courseVo);
        map.put("chapterVoList", chapterVoList);
        map.put("description", null != courseDescription ? courseDescription.getDescription() : "");
        map.put("teacher", teacher);
        map.put("isBuy", false);
        return map;
    }

    private Course getNameById(Course course) {
        Teacher teacher = teacherService.getById(course.getTeacherId());
        if (teacher != null) {
            String name = teacher.getName();
            course.getParam().put("teacherName", name);
        }

        Subject subjectOne = subjectService.getById(course.getSubjectParentId());
        if (subjectOne != null) {
            course.getParam().put("subjectParentTitle", subjectOne.getTitle());
        }
        Subject subjectTwo = subjectService.getById(course.getSubjectId());
        if (subjectTwo != null) {
            course.getParam().put("subjectTitle", subjectTwo.getTitle());
        }
        return course;
    }
}
