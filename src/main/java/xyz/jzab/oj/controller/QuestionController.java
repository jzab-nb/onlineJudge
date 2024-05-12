package xyz.jzab.oj.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;
import xyz.jzab.oj.common.BaseResponse;
import xyz.jzab.oj.common.PageRequest;
import xyz.jzab.oj.common.ResultUtils;
import xyz.jzab.oj.model.dto.question.QuestionAddRequest;
import xyz.jzab.oj.model.entity.Clazz;
import xyz.jzab.oj.model.entity.Question;
import xyz.jzab.oj.model.entity.Stuinclazz;
import xyz.jzab.oj.model.entity.User;
import xyz.jzab.oj.model.vo.ClazzVo;
import xyz.jzab.oj.model.vo.QuestionVo;
import xyz.jzab.oj.model.vo.StuInClazzVo;
import xyz.jzab.oj.service.QuestionService;
import xyz.jzab.oj.service.UserService;

import java.util.ArrayList;
import java.util.List;

/**
 * @author JZAB
 * @from http://vip.jzab.xyz
 */
@RestController
@RequestMapping("/question")
public class QuestionController {
    @Resource
    UserService userService;
    @Resource
    QuestionService questionService;
    // 添加题目
    @PostMapping("/add")
    public BaseResponse<Boolean> addQuestion(@RequestBody QuestionAddRequest questionAddRequest, HttpServletRequest request){
        User loginUser = userService.getLoginUser(request);
        Question question = new Question( );
        BeanUtils.copyProperties(questionAddRequest, question);
        question.setCreateUser(loginUser.getId());
        question.setUpdateUser(loginUser.getId());
        return ResultUtils.success(questionService.save(question));
    }

    // 分页获取题目
    @GetMapping("/list/page/byBank/{id}")
    public BaseResponse<Page<QuestionVo>> pageByBankId(PageRequest pageRequest, @PathVariable Integer id){
        QueryWrapper<Question> queryWrapper = new QueryWrapper<>(  );
        queryWrapper.eq("questionBankId",id);
        Page<Question> page = new Page<>(pageRequest.getCurrent(), pageRequest.getSize());
        questionService.page(page,queryWrapper);
        Page<QuestionVo> voPage = new Page<>(pageRequest.getCurrent(), pageRequest.getSize(), page.getTotal());
        voPage.setRecords(getVos(page.getRecords()));
        return ResultUtils.success(voPage);
    }

    // 删除题目
    @PostMapping("/del/{id}")
    public BaseResponse<Boolean> delQuestion(@PathVariable Integer id){
        return ResultUtils.success(questionService.removeById(id));
    }

    // 通过id列表获取题目列表
    @PostMapping("/list/byIds")
    public BaseResponse<List<QuestionVo>> getQuestionListByIds(@RequestBody List<Integer> questionIds){
        return ResultUtils.success(getVos(questionService.listByIds(questionIds)));
    }

    public List<QuestionVo> getVos(List<Question> questionList){
        List<QuestionVo> voList = new ArrayList<>();
        for (Question question : questionList) {
            QuestionVo vo = new QuestionVo();
            BeanUtils.copyProperties(question,vo);
            voList.add(vo);
        }
        return voList;
    }
}
