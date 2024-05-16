package xyz.jzab.oj.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;
import xyz.jzab.oj.annotation.AuthCheck;
import xyz.jzab.oj.common.BaseResponse;
import xyz.jzab.oj.common.PageRequest;
import xyz.jzab.oj.common.ResultUtils;
import xyz.jzab.oj.model.dto.exam.ExamAddRequest;
import xyz.jzab.oj.model.dto.exam.ExamUpdateRequest;
import xyz.jzab.oj.model.entity.Course;
import xyz.jzab.oj.model.entity.Exam;
import xyz.jzab.oj.model.entity.Stuexamrecord;
import xyz.jzab.oj.model.entity.User;
import xyz.jzab.oj.model.enums.ExamStatusEnums;
import xyz.jzab.oj.model.enums.UserRoleEnum;
import xyz.jzab.oj.model.vo.CourseVo;
import xyz.jzab.oj.model.vo.ExamVo;
import xyz.jzab.oj.service.ExamService;
import xyz.jzab.oj.service.StuexamrecordService;
import xyz.jzab.oj.service.UserService;

import java.util.ArrayList;
import java.util.List;

/**
 * @author JZAB
 * @from http://vip.jzab.xyz
 */
@RestController
@RequestMapping("/exam")
public class ExamController {

    @Resource
    UserService userService;
    @Resource
    ExamService examService;
    @Resource
    StuexamrecordService stuexamrecordService;

    // 创建考试
    @PostMapping("/create")
    public BaseResponse<Boolean> createExam(@RequestBody ExamAddRequest examAddRequest, HttpServletRequest request){
        User loginUser = userService.getLoginUser(request);
        Exam exam = new Exam( );
        BeanUtils.copyProperties(examAddRequest,exam);
        exam.setStatus(ExamStatusEnums.CREATE.getStatus( ));
        exam.setCreateUser(loginUser.getId());
        exam.setUpdateUser(loginUser.getId());
        return ResultUtils.success(examService.save(exam));
    }
    // 修改考试信息
    @PostMapping("/update")
    public BaseResponse<Boolean> updateExam(@RequestBody ExamUpdateRequest examUpdateRequest, HttpServletRequest request){
        User loginUser = userService.getLoginUser(request);
        Exam exam = new Exam( );
        BeanUtils.copyProperties(examUpdateRequest,exam);
        exam.setUpdateUser(loginUser.getId());
        return ResultUtils.success(examService.updateById(exam));
    }

    // 删除考试
    @PostMapping("/del/{id}")
    public BaseResponse<Boolean> delExam(@PathVariable Integer id){
        return ResultUtils.success(examService.removeById(id));
    }

    // 分页获取考试信息
    @GetMapping("/list/page/byTeacher/{id}")
    public BaseResponse<Page<ExamVo>> pageExam(@PathVariable Integer id, PageRequest pageRequest){
        LambdaQueryWrapper<Exam> wrapper = new LambdaQueryWrapper<>(  );
        wrapper.eq( Exam::getCreateUser,id);
        Page<Exam> page = new Page<>(pageRequest.getCurrent(),pageRequest.getSize());
        examService.page(page, wrapper);
        Page<ExamVo> voPage = new Page<>(pageRequest.getCurrent(), pageRequest.getSize(), page.getTotal());
        voPage.setRecords(getVos(page.getRecords()));
        return ResultUtils.success(voPage);
    }

    @GetMapping("/list/page/byCourse/{id}")
    public BaseResponse<Page<ExamVo>> pageExamByCourse(@PathVariable Integer id, PageRequest pageRequest){
        LambdaQueryWrapper<Exam> wrapper = new LambdaQueryWrapper<>(  );
        wrapper.eq( Exam::getCourseId,id);
        Page<Exam> page = new Page<>(pageRequest.getCurrent(),pageRequest.getSize());
        examService.page(page, wrapper);
        Page<ExamVo> voPage = new Page<>(pageRequest.getCurrent(), pageRequest.getSize(), page.getTotal());
        voPage.setRecords(getVos(page.getRecords()));
        return ResultUtils.success(voPage);
    }

    public List<ExamVo> getVos(List<Exam> examList){
        ArrayList<ExamVo> voList = new ArrayList<>( );
        for (Exam exam : examList) {
            ExamVo examVo = new ExamVo(  );
            BeanUtils.copyProperties(exam,examVo);
            voList.add(examVo);
        }
        return voList;
    }


    // 学生开始考试: 生成学生考试记录表
    @PostMapping("/start/{id}")
    @AuthCheck(mustRole = {UserRoleEnum.STUDENT})
    public BaseResponse<Integer> startExam(@PathVariable Integer id,HttpServletRequest request){
        User loginUser = userService.getLoginUser(request);
        LambdaQueryWrapper<Stuexamrecord> wrapper = new LambdaQueryWrapper<>( );
        wrapper.eq(Stuexamrecord::getExamId,id);
        wrapper.eq(Stuexamrecord::getStuId,loginUser.getId());
        if(stuexamrecordService.count(wrapper)!=0){
            return ResultUtils.success(stuexamrecordService.getOne(wrapper).getId());
        }
        Stuexamrecord stuexamrecord = new Stuexamrecord( );
        stuexamrecord.setCreateUser(loginUser.getId());
        stuexamrecord.setUpdateUser(loginUser.getId());
        stuexamrecord.setExamId(id);
        stuexamrecord.setStuId(loginUser.getId());
        stuexamrecord.setAnswerList(new ArrayList<>(  ));
        stuexamrecord.setScore(0);
        stuexamrecordService.save(stuexamrecord);
        return ResultUtils.success(stuexamrecord.getId());
    }
    // 学生答题
    /* 答案列表: 题目下标,学生答案,是否被阅
    * [
    *   {index:1,answer:1,isReview:true}
    * ]
    * */

    // 自动阅卷接口(选择自动判断)

    // 手动阅卷接口

}