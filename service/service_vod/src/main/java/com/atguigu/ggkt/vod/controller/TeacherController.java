package com.atguigu.ggkt.vod.controller;


import com.atguigu.ggkt.exception.GgktException;
import com.atguigu.ggkt.model.vod.Teacher;
import com.atguigu.ggkt.result.Result;
import com.atguigu.ggkt.vo.vod.TeacherQueryVo;
import com.atguigu.ggkt.vod.service.TeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author songml
 * @since 20230401
 */
@Api(tags = "讲师管理接口")
@RestController
@RequestMapping(value = "/admin/vod/teacher")
//@CrossOrigin
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    //http://49.123.88.83:8301/admin/vod/teacher/findAll
//    @ApiOperation("查询所有讲师")
//    @GetMapping("findAll")
//    public List<Teacher> findAllTeacher() {
//        List<Teacher> list = teacherService.list();
//        return list;
//    }

    @ApiOperation("查询所有讲师")
    @GetMapping("findAll")
    public Result findAllTeacher() {
//        try {
//            int a = 10 / 0;
//        } catch (Exception e) {
//            throw new GgktException(201, "自定义异常！");
//        }
        List<Teacher> list = teacherService.list();
        return Result.ok(list).message("查询数据成功");
    }

//    @ApiOperation("逻辑删除讲师")
//    @DeleteMapping("remove/{id}")
//    public boolean removeTeacher(@ApiParam(name = "id", value = "ID", required = true)
//                                 @PathVariable Long id) {
//        boolean isSuccess = teacherService.removeById(id);
//        return isSuccess;
//    }

    @ApiOperation("逻辑删除讲师")
    @DeleteMapping("remove/{id}")
    public Result removeTeacher(@ApiParam(name = "id", value = "ID", required = true)
                                @PathVariable Long id) {
        boolean isSuccess = teacherService.removeById(id);
        return isSuccess ? Result.ok(null) : Result.fail(null);
    }

    @ApiOperation("条件查询分页")
    @PostMapping("findQueryPage/{current}/{limit}")
    public Result findPage(@PathVariable long current,
                           @PathVariable long limit,
                           @RequestBody(required = false) TeacherQueryVo teacherQueryVo) {
        Page<Teacher> pageParam = new Page<>(current, limit);
        if (teacherQueryVo == null) {
            IPage<Teacher> pageModel = teacherService.page(pageParam, null);
            return Result.ok(pageModel);
        } else {
            String name = teacherQueryVo.getName();
            Integer level = teacherQueryVo.getLevel();
            String joinDateBegin = teacherQueryVo.getJoinDateBegin();
            String joinDateEnd = teacherQueryVo.getJoinDateEnd();
            QueryWrapper<Teacher> wrapper = new QueryWrapper<>();
            if (!StringUtils.isEmpty(name)) {
                wrapper.like("name", name);
            }
            if (!StringUtils.isEmpty(level)) {
                wrapper.eq("level", level);
            }
            if (!StringUtils.isEmpty(joinDateBegin)) {
                wrapper.ge("join_date", joinDateBegin);
            }
            if (!StringUtils.isEmpty(joinDateEnd)) {
                wrapper.le("join_date", joinDateEnd);
            }
            IPage<Teacher> pageModel = teacherService.page(pageParam, wrapper);
            return Result.ok(pageModel);
        }
    }

    @ApiOperation("添加讲师")
    @PostMapping("saveTeacher")
    public Result saveTeacher(@RequestBody Teacher teacher) {
        boolean isSuccess = teacherService.save(teacher);
        return isSuccess ? Result.ok(null) : Result.fail(null);
    }

    @ApiOperation("根据id查询讲师")
    @GetMapping("getTeacher/{id}")
    public Result getTeacher(@PathVariable Long id) {
        Teacher teacher = teacherService.getById(id);
        return Result.ok(teacher);
    }

    @ApiOperation("修改讲师")
    @PostMapping("updateTeacher")
    public Result updateTeacher(@RequestBody Teacher teacher) {
        boolean isSuccess = teacherService.updateById(teacher);
        return isSuccess ? Result.ok(null) : Result.fail(null);
    }

    @ApiOperation("批量删除讲师")
    @DeleteMapping("removeBatch")
    public Result removeBatch(@RequestBody List<Long> idList) {
        boolean isSuccess = teacherService.removeByIds(idList);
        return isSuccess ? Result.ok(null) : Result.fail(null);
    }


}

