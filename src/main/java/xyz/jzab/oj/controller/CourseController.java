package xyz.jzab.oj.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.jzab.oj.common.BaseResponse;
import xyz.jzab.oj.common.ResultUtils;
import xyz.jzab.oj.model.entity.Course;

/**
 * @author JZAB
 * @from http://vip.jzab.xyz
 */
@RestController
@RequestMapping("/course")
public class CourseController {
    // 增:添加课程
    public BaseResponse<Boolean> addCourse(){
        return ResultUtils.success(null);
    }
    // 删:删除课程
    public BaseResponse<Boolean> delCourse(){
        return ResultUtils.success(null);
    }

    // 改:修改课程信息
    public BaseResponse<Boolean> updateCourse(){
        return ResultUtils.success(null);
    }

    // 查:分页获取课程列表
    public BaseResponse<Course> pageCourse(){
        return ResultUtils.success(null);
    }

    // 课程内添加学生(传入班级号批量添加)
    public BaseResponse<Boolean> addStudentsByClazz(){
        return ResultUtils.success(null);
    }
}