package xyz.jzab.oj.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;
import xyz.jzab.oj.common.BaseResponse;
import xyz.jzab.oj.common.PageRequest;
import xyz.jzab.oj.common.ResultUtils;
import xyz.jzab.oj.constant.SysConstant;
import xyz.jzab.oj.model.dto.course.AddByClazzRequest;
import xyz.jzab.oj.model.dto.course.CourseAddRequest;
import xyz.jzab.oj.model.dto.course.CourseUpdateRequest;
import xyz.jzab.oj.model.entity.*;
import xyz.jzab.oj.model.enums.FileTypeEnums;
import xyz.jzab.oj.model.vo.CourseVo;
import xyz.jzab.oj.model.vo.StuInCourseVo;
import xyz.jzab.oj.service.*;
import xyz.jzab.oj.utils.FileUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author JZAB
 * @from http://vip.jzab.xyz
 */
@RestController
@RequestMapping("/course")
public class CourseController {

    @Resource
    CourseService courseService;
    @Resource
    StuincourseService stuincourseService;
    @Resource
    ClazzService clazzService;
    @Resource
    StuinclazzService stuinclazzService;
    @Resource
    UserService userService;
    @Resource
    FileUtils fileUtils;
    // 增:添加课程
    @PostMapping("/add")
    public BaseResponse<Boolean> addCourse(@RequestBody CourseAddRequest courseAddRequest, HttpServletRequest req){
        Course course = new Course( );
        User loginUser  = userService.getLoginUser(req);
        BeanUtils.copyProperties(courseAddRequest,course);
        course.setCreateUser(loginUser.getId());
        course.setUpdateUser(loginUser.getId());
        courseService.save(course);
        if(StringUtils.isNotBlank(courseAddRequest.getImg())){
            course.setImg(fileUtils.StoreTemp(req.getSession( ), FileTypeEnums.COURSE_IMG,course.getId()));
        }else{
            course.setImg(SysConstant.DEFAULT_IMG);
        }
        return ResultUtils.success(courseService.updateById(course));
    }
    // 删:删除课程(根据id)
    @PostMapping("/del/{id}")
    public BaseResponse<Boolean> delCourse(@PathVariable Integer id){
        return ResultUtils.success(courseService.removeById(id));
    }

    // 改:修改课程信息
    @PostMapping("/update")
    public BaseResponse<Boolean> updateCourse(@RequestBody CourseUpdateRequest courseUpdateRequest, HttpServletRequest request){
        if(StringUtils.isNotBlank(courseUpdateRequest.getImg())){
            String path = fileUtils.StoreTemp(request.getSession(), FileTypeEnums.COURSE_IMG, courseUpdateRequest.getId());
            courseUpdateRequest.setImg(path);
        }else{
            courseUpdateRequest.setImg(null);
        }
        Course course = new Course( );
        BeanUtils.copyProperties(courseUpdateRequest,course);
        return ResultUtils.success(courseService.updateById(course));
    }

    // 查:分页获取课程列表
    @GetMapping("/list/page/byTeacher/{id}")
    public BaseResponse<Page<CourseVo>> pageCourse(PageRequest pageRequest, @PathVariable Integer id){
        LambdaQueryWrapper<Course> wrapper = new LambdaQueryWrapper<>(  );
        wrapper.eq( Course::getTeacherId,id);
        Page<Course> page = new Page<>(pageRequest.getCurrent(),pageRequest.getSize());
        courseService.page(page, wrapper);
        Page<CourseVo> voPage = new Page<>(pageRequest.getCurrent(), pageRequest.getSize(), page.getTotal());
        voPage.setRecords(getVos(page.getRecords()));
        return ResultUtils.success(voPage);
    }

    // 获取课程内的学生
    @GetMapping("/list/{id}/students")
    public BaseResponse<List<StuInCourseVo>> getStudentsInCourse(@PathVariable Integer id){
        LambdaQueryWrapper<Stuincourse> wrapper = new LambdaQueryWrapper<>( );
        wrapper.eq(Stuincourse::getCourseId,id);
        List<Stuincourse> list = stuincourseService.list(wrapper);
        List<StuInCourseVo> res = new ArrayList<>(  );
        for (Stuincourse stuincourse : list) {
            StuInCourseVo stuInCourseVo = new StuInCourseVo( );
            BeanUtils.copyProperties(stuincourse,stuInCourseVo);
            User stu = userService.getById(stuincourse.getStuId( ));
            stuInCourseVo.setStuName(stu.getName());
            stuInCourseVo.setStuUserName(stu.getUsername());
            res.add(stuInCourseVo);
        }
        return ResultUtils.success(res);
    }
    // 批量删除学生
    @PostMapping("/students/batchDel")
    public BaseResponse<Integer> batchDelStudents(@RequestBody List<Integer> ids) {
        stuincourseService.removeBatchByIds(ids);
        return ResultUtils.success(ids.size());
    }

    // 课程内添加学生(传入班级号批量添加)
    @PostMapping("/students/addByClazz")
    public BaseResponse<Integer> addStudentsByClazz(@RequestBody AddByClazzRequest addByClazzRequest, HttpServletRequest request){
        User loginUser = userService.getLoginUser(request);
        // 找到对应的课程,批量找出班级内的学生
        Course course = courseService.getById(addByClazzRequest.getCourseId());
        List<Stuincourse> list = new ArrayList<>(  );
        for (Clazz clazz : clazzService.listByIds(addByClazzRequest.getClazzIds( ))) {

            LambdaQueryWrapper<Stuinclazz> wrapper = new LambdaQueryWrapper<>( );
            wrapper.eq(Stuinclazz::getClazzId,clazz.getId());
            List<Stuinclazz> stuInClazzList = stuinclazzService.list(wrapper);
            for (Stuinclazz stuinclazz : stuInClazzList) {
                Stuincourse stuincourse = new Stuincourse( );
                stuincourse.setStuId(stuinclazz.getStuId());
                stuincourse.setCourseId(course.getId());
                stuincourse.setCreateUser(loginUser.getId());
                stuincourse.setUpdateUser(loginUser.getId());
                list.add(stuincourse);
            }
        }
        stuincourseService.saveBatch(list);
        return ResultUtils.success(list.size());
    }

    public List<CourseVo> getVos(List<Course> courseList){
        ArrayList<CourseVo> vos = new ArrayList<>( );
        for (Course course : courseList) {
            CourseVo courseVo = new CourseVo( );
            BeanUtils.copyProperties(course,courseVo);
            vos.add(courseVo);
        }
        return vos;
    }
}