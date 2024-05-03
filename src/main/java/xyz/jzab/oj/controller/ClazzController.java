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
import xyz.jzab.oj.common.ErrorCode;
import xyz.jzab.oj.common.PageRequest;
import xyz.jzab.oj.common.ResultUtils;
import xyz.jzab.oj.exception.BusinessException;
import xyz.jzab.oj.model.dto.clazz.BatchStudentRequest;
import xyz.jzab.oj.model.dto.clazz.ClazzAddRequest;
import xyz.jzab.oj.model.dto.clazz.ClazzUpdateRequest;
import xyz.jzab.oj.model.entity.Clazz;
import xyz.jzab.oj.model.entity.Stuinclazz;
import xyz.jzab.oj.model.entity.User;
import xyz.jzab.oj.model.enums.UserRoleEnum;
import xyz.jzab.oj.model.vo.ClazzVo;
import xyz.jzab.oj.model.vo.StuInClazzVo;
import xyz.jzab.oj.model.vo.UserVo;
import xyz.jzab.oj.service.ClazzService;
import xyz.jzab.oj.service.StuinclazzService;
import xyz.jzab.oj.service.UserService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author JZAB
 * @from http://vip.jzab.xyz
 */
@RestController
@RequestMapping("/clazz")
public class ClazzController {
    @Resource
    ClazzService clazzService;

    @Resource
    UserService userService;

    @Resource
    StuinclazzService stuinClazzService;

    // 增
    @PostMapping("/add")
    public BaseResponse<Boolean> addClazz(@RequestBody ClazzAddRequest clazzAddRequest, HttpServletRequest request){
        return ResultUtils.success(clazzService.addClazz(clazzAddRequest, userService.getLoginUser(request)));
    }

    // 删
    @PostMapping("/del/{id}")
    public BaseResponse<Boolean> delClazz(@PathVariable Integer id, HttpServletRequest request){
        return ResultUtils.success(clazzService.delClazz(id, userService.getLoginUser(request)));
    }

    // 改
    @PostMapping("/update")
    public BaseResponse<Boolean> updateClazz(@RequestBody ClazzUpdateRequest clazzUpdateRequest, HttpServletRequest request){
        return ResultUtils.success(clazzService.updateClazz(clazzUpdateRequest, userService.getLoginUser(request)));
    }

    // 查
    @GetMapping("/list/page")
    public BaseResponse<Page<ClazzVo>> pageClazz(PageRequest pageRequest){
        return ResultUtils.success(clazzService.listPage(pageRequest));
    }

    @GetMapping("/list/page/byTeacher/{id}")
    public BaseResponse<Page<ClazzVo>> pageByTeacher(PageRequest pageRequest, @PathVariable Integer id){
        QueryWrapper<Clazz> queryWrapper = new QueryWrapper<>(  );
        queryWrapper.eq("teacherId",id);
        Page<Clazz> page = new Page<>(pageRequest.getCurrent(), pageRequest.getSize());
        clazzService.page(page,queryWrapper);
        Page<ClazzVo> voPage = new Page<>(pageRequest.getCurrent(), pageRequest.getSize(), page.getTotal());
        voPage.setRecords(clazzService.getVos(page.getRecords()));
        return ResultUtils.success(voPage);
    }

    // 获取学生所有班级

    // 获取班级内所有学生
    @GetMapping("/list/{id}/students")
    public BaseResponse<List<StuInClazzVo>> getStudents(@PathVariable("id") Integer id){
        LambdaQueryWrapper<Stuinclazz> wrapper = new LambdaQueryWrapper<>( );
        wrapper.eq(Stuinclazz::getClazzId,id);
        List<Stuinclazz> list = stuinClazzService.list(wrapper);
        List<StuInClazzVo> res = new ArrayList<>(  );
        for (Stuinclazz stuinclazz : list) {
            User user = userService.getById(stuinclazz.getStuId( ));
            StuInClazzVo stuInClazzVo = new StuInClazzVo( );
            BeanUtils.copyProperties(stuinclazz,stuInClazzVo);
            stuInClazzVo.setStuUserName(user.getUsername());
            stuInClazzVo.setStuName(user.getName());
            res.add(stuInClazzVo);
        }
        return ResultUtils.success(res);
    }

    // 班级内批量添加学生
    @PostMapping("/students/batchAdd")
    @AuthCheck(mustRole = {UserRoleEnum.ADMIN, UserRoleEnum.TEACHER})
    public BaseResponse<Integer> batchAddStudents(@RequestBody BatchStudentRequest batchStudentRequest, HttpServletRequest request){
        Clazz clazz = clazzService.getById(batchStudentRequest.getClazzId( ));
        User loginUser = userService.getLoginUser(request);
        if(clazz==null) throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        List<Stuinclazz> list = new ArrayList<>(  );
        for (Integer studentId : batchStudentRequest.getStudentIds( )) {
            Stuinclazz stuinclazz = new Stuinclazz( );
            stuinclazz.setClazzId(clazz.getId( ));
            stuinclazz.setStuId(studentId);
            stuinclazz.setCreateUser(loginUser.getId());
            stuinclazz.setUpdateUser(loginUser.getId());
            list.add(stuinclazz);
        }
        stuinClazzService.saveBatch(list);
        return ResultUtils.success(list.size());
    }

    // 班级内批量删除学生
    @PostMapping("/students/batchDel")
    @AuthCheck(mustRole = {UserRoleEnum.ADMIN, UserRoleEnum.TEACHER})
    public BaseResponse<Integer> batchDelStudents(@RequestBody List<Integer> ids) {
        stuinClazzService.removeBatchByIds(ids);
        return ResultUtils.success(ids.size());
    }
}
