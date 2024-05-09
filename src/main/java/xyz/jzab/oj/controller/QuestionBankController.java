package xyz.jzab.oj.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;
import xyz.jzab.oj.common.BaseResponse;
import xyz.jzab.oj.common.PageRequest;
import xyz.jzab.oj.common.ResultUtils;
import xyz.jzab.oj.model.dto.questionBank.QuestionBankAddRequest;
import xyz.jzab.oj.model.dto.questionBank.QuestionBankUpdateRequest;
import xyz.jzab.oj.model.entity.Questionbank;
import xyz.jzab.oj.model.entity.User;
import xyz.jzab.oj.model.vo.QuestionBankVo;
import xyz.jzab.oj.service.QuestionbankService;
import xyz.jzab.oj.service.UserService;

import java.util.ArrayList;
import java.util.List;

/**
 * @author JZAB
 * @from http://vip.jzab.xyz
 */
@RestController
@RequestMapping("/qBank")
public class QuestionBankController {
    @Resource
    QuestionbankService questionbankService;
    @Resource
    UserService userService;
    // 增
    @PostMapping("/add")
    public BaseResponse<Boolean> addQuestionBank(@RequestBody QuestionBankAddRequest questionBankAddRequest, HttpServletRequest request){
        Questionbank questionbank = new Questionbank( );
        User loginUser = userService.getLoginUser(request);
        BeanUtils.copyProperties(questionBankAddRequest,questionbank);
        questionbank.setCreateUser(loginUser.getId());
        questionbank.setUpdateUser(loginUser.getId());
        questionbank.setTeacherId(loginUser.getId());
        return ResultUtils.success(questionbankService.save(questionbank));
    }
    // 删
    @PostMapping("/del/{id}")
    public BaseResponse<Boolean> delQuestionBank(@PathVariable Integer id){
        return ResultUtils.success(questionbankService.removeById(id));
    }
    // 改
    @PostMapping("/update")
    public BaseResponse<Boolean> updateQuestionBank(@RequestBody QuestionBankUpdateRequest questionBankUpdateRequest, HttpServletRequest request){
        Questionbank questionbank = new Questionbank();
        User loginUser = userService.getLoginUser(request);
        BeanUtils.copyProperties(questionBankUpdateRequest,questionbank);
        questionbank.setUpdateUser(loginUser.getId());
        return ResultUtils.success(questionbankService.updateById(questionbank));
    }

    // 查
    @GetMapping("/list/page/byTeacher/{id}")
    public BaseResponse<Page<QuestionBankVo>> pageBank(PageRequest pageRequest, @PathVariable Integer id){
        LambdaQueryWrapper<Questionbank> wrapper = new LambdaQueryWrapper<>(  );
        wrapper.eq( Questionbank::getTeacherId,id);
        Page<Questionbank> page = new Page<>(pageRequest.getCurrent(),pageRequest.getSize());
        questionbankService.page(page, wrapper);
        Page<QuestionBankVo> voPage = new Page<>(pageRequest.getCurrent(), pageRequest.getSize(), page.getTotal());
        voPage.setRecords(getVos(page.getRecords()));
        return ResultUtils.success(voPage);
    }

    public List<QuestionBankVo> getVos(List<Questionbank> list){
        List<QuestionBankVo> vos = new ArrayList<>();
        for (Questionbank questionbank : list) {
            QuestionBankVo vo = new QuestionBankVo( );
            BeanUtils.copyProperties( questionbank,vo);
            vos.add(vo);
        }
        return vos;
    }
}