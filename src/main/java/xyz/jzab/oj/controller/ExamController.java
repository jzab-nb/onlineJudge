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
import xyz.jzab.oj.model.dto.exam.ExamAddRequest;
import xyz.jzab.oj.model.dto.exam.ExamUpdateRequest;
import xyz.jzab.oj.model.dto.exam.ScoreQuestionRequest;
import xyz.jzab.oj.model.entity.*;
import xyz.jzab.oj.model.enums.ExamStatusEnums;
import xyz.jzab.oj.model.enums.QuestionTypeEnums;
import xyz.jzab.oj.model.enums.UserRoleEnum;
import xyz.jzab.oj.model.vo.ExamDetailVo;
import xyz.jzab.oj.model.vo.ExamVo;
import xyz.jzab.oj.model.vo.QuestionVo;
import xyz.jzab.oj.model.vo.StuExamVo;
import xyz.jzab.oj.service.*;

import java.util.*;
import java.util.stream.Collectors;

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
    @Resource
    QuestionService questionService;
    @Resource
    PaperService paperService;
    @Resource
    QuestionController questionController;
    @Resource
    CourseService courseService;
    @Resource
    StuincourseService stuincourseService;


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

    @GetMapping("/list/byStudent/{id}")
    public BaseResponse<List<ExamVo>> listExamByStudent(@PathVariable Integer id){
        LambdaQueryWrapper<Stuexamrecord> wrapper = new LambdaQueryWrapper<>(  );
        wrapper.eq(Stuexamrecord::getStuId,id);
        List<Stuexamrecord> list = stuexamrecordService.list(wrapper);
        List<Integer> examIds = list.stream( ).map(Stuexamrecord::getExamId).collect(Collectors.toList( ));
        return ResultUtils.success(getVos(examService.listByIds(examIds)));
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
    public BaseResponse<StuExamVo> startExam(@PathVariable Integer id, HttpServletRequest request){
        User loginUser = userService.getLoginUser(request);
        LambdaQueryWrapper<Stuexamrecord> wrapper = new LambdaQueryWrapper<>( );
        wrapper.eq(Stuexamrecord::getExamId,id);
        wrapper.eq(Stuexamrecord::getStuId,loginUser.getId());
        if(stuexamrecordService.count(wrapper)!=0){
            return ResultUtils.success(getStuExamVo(stuexamrecordService.getOne(wrapper).getId(),false));
        }
        Stuexamrecord stuexamrecord = new Stuexamrecord( );
        stuexamrecord.setCreateUser(loginUser.getId());
        stuexamrecord.setUpdateUser(loginUser.getId());
        stuexamrecord.setExamId(id);
        stuexamrecord.setStuId(loginUser.getId());
        stuexamrecord.setAnswerList(new HashMap<>(  ));
        stuexamrecord.setScore(0);
        stuexamrecordService.save(stuexamrecord);
        return ResultUtils.success(getStuExamVo(stuexamrecord.getId(),false));
    }

    StuExamVo getStuExamVo(int id,boolean showAnswer){
        Stuexamrecord stuexamrecord = stuexamrecordService.getById(id);
        StuExamVo vo = new StuExamVo( );
        BeanUtils.copyProperties(stuexamrecord,vo);
        vo.setAnswerList(stuexamrecord.getAnswerList());
        // 获取题目列表
        Exam exam = examService.getById(stuexamrecord.getExamId( ));
        Paper paper = paperService.getById(exam.getPaperId());
        List<Question> questions = questionService.listByIds(paper.getQuestionList( ));
        List<QuestionVo> questionVos = questionController.getVos(questions);
        questionVos.forEach(question -> {
            Map<String,Object> data = (Map<String, Object>) question.getContent( ).get("data");
            if(!showAnswer){
                data.put("answer",null);
            }
        });
        vo.setQuestionList(questionVos);
        return vo;
    }

    // 学生答题
    // { 答案,得分,是否正确,是否已经判完
    //  0:{answer:"", score:0, isRight:"YES|NO|HALF",isJudge:false}
    // }
    @PostMapping("/answer/{id}")
    @AuthCheck(mustRole = {UserRoleEnum.STUDENT})
    public BaseResponse<Boolean> answerExam(@PathVariable Integer id,@RequestBody Map<Integer,Object> answerMap,HttpServletRequest request){
        // 获取到题目列表
        User loginUser = userService.getLoginUser(request);
        Exam exam = examService.getById(id);
        Paper paper = paperService.getById(exam.getPaperId());
        LambdaQueryWrapper<Stuexamrecord> wrapper = new LambdaQueryWrapper<>( );
        wrapper.eq(Stuexamrecord::getExamId,id);
        wrapper.eq(Stuexamrecord::getStuId,loginUser.getId());
        Stuexamrecord stuExam = stuexamrecordService.getOne(wrapper);
        List<Question> questions = questionService.listByIds(paper.getQuestionList( ));
        if(questions==null || questions.size()!=answerMap.size()){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"题目答案列表长度不一致");
        }
        Map<String,Map<String,Object>> transAnswerMap = new HashMap<>(  );
        // 遍历题目列表,对单选多选进行判分
        Integer totalScore = 0;
        for (int i = 0; i < questions.size( ); i++) {
            Question question = questions.get(i);
            // 获取题型
            String type = (String) question.getContent( ).get("type");
            Integer score = (Integer) question.getContent( ).get("score");
            Map<String, Object> data = (Map<String, Object>) question.getContent( ).get("data");
            Map<String, Object> oneAnswer = new HashMap<>( );
            oneAnswer.put("answer", answerMap.get(i));
            if (type.equals(QuestionTypeEnums.singleChoice.getType( ))) {
                Integer trueAnswer = (Integer) data.get("answer");
                Integer youAnswer = (Integer) answerMap.get(i);
                if (trueAnswer.equals(youAnswer)) {
                    totalScore += score;
                    oneAnswer.put("score", score);
                    oneAnswer.put("isRight", "YES");
                } else {
                    oneAnswer.put("score", 0);
                    oneAnswer.put("isRight", "NO");
                }
                oneAnswer.put("isJudge", true);
            } else if (type.equals(QuestionTypeEnums.multiChoice.getType( ))) {
                List<Integer> trueAnswer = (List<Integer>) data.get("answer");
                List<Integer> youAnswer = (List<Integer>) answerMap.get(i);
                if (trueAnswer.equals(youAnswer)) {
                    totalScore += score;
                    oneAnswer.put("score", score);
                    oneAnswer.put("isRight", "YES");
                } else {
                    oneAnswer.put("score", 0);
                    oneAnswer.put("isRight", "NO");
                }
                oneAnswer.put("isJudge", true);
            } else {
                oneAnswer.put("score", 0);
                oneAnswer.put("isRight", "NO");
                oneAnswer.put("isJudge", false);
            }
            // 生成answer列表
            transAnswerMap.put(Integer.toString(i), oneAnswer);
        }
        stuExam.setScore(totalScore);
        stuExam.setAnswerList(transAnswerMap);
        return ResultUtils.success(stuexamrecordService.updateById(stuExam));
    }

    @GetMapping("/detail/{id}")
    public BaseResponse<ExamDetailVo> showDetail(@PathVariable Integer id){
        // 根据试卷id关联到课程上,根据课程id关联出应该参与的学生
        Exam exam = examService.getById(id);
        ExamDetailVo examDetailVo = new ExamDetailVo( );
        examDetailVo.setId(id);
        // {stuId:"",stuName:"",score:0,isJoin:true}
        Map<Integer,Map<String,Object>> data = new HashMap<>();
        // 获取到课程下的学生列表
        List<Stuincourse> stus = stuincourseService.list(new LambdaQueryWrapper<Stuincourse>( ).eq(Stuincourse::getCourseId, exam.getCourseId( )));
        stus.forEach(stu->{
            HashMap<String, Object> map = new HashMap<>( );
            User user = userService.getById(stu.getStuId( ));
            map.put("stuId",user.getId());
            map.put("stuName",user.getName());
            map.put("isJoin",false);
            data.put(stu.getStuId(),map);
        });
        // 获取学生考试关系表
        List<Stuexamrecord> stuExamList = stuexamrecordService.list(new LambdaQueryWrapper<Stuexamrecord>( ).eq(Stuexamrecord::getExamId, id));
        stuExamList.forEach(stuExam->{
            Map<String, Object> map = data.get(stuExam.getStuId( ));
            if(map==null){
                map = new HashMap<>(  );
                User user = userService.getById(stuExam.getStuId());
                map.put("stuId",user.getId());
                map.put("stuName",user.getName());
                data.put(stuExam.getStuId(),map);
            }
            map.put("stuExamId",stuExam.getId());
            map.put("score",stuExam.getScore());
            map.put("isJoin",true);
        });
        List<Map<String, Object>> list = data.values( ).stream( ).toList( );
        examDetailVo.setData(list);
        return ResultUtils.success(examDetailVo);
    }


    @GetMapping("/showAnswer/{id}")
    public BaseResponse<StuExamVo> showAnswer(@PathVariable Integer id){
        return ResultUtils.success(getStuExamVo(id,true));
    }
    // 手动阅卷接口
    @PostMapping("/score")
    public BaseResponse<Boolean> scoreQuestion(@RequestBody ScoreQuestionRequest scoreQuestionRequest){
        Stuexamrecord stuExam = stuexamrecordService.getById(scoreQuestionRequest.getStuExamId( ));
        Exam exam = examService.getById(stuExam.getExamId());
        Paper paper = paperService.getById(exam.getPaperId());
        List<Integer> questionList = paper.getQuestionList( );
        Integer qId = questionList.get(scoreQuestionRequest.getQId()-1);
        Question question = questionService.getById(qId);
        Integer score = scoreQuestionRequest.getScore();
        Integer totalScore = (Integer) question.getContent().get("score");
        if(score<0 || score>totalScore){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"分数设置错误");
        }
        String isRight = "";
        if(score==0){
            isRight = "NO";
        }else if(score.equals(totalScore)){
            isRight = "YES";
        }else{
            isRight = "HALF";
        }
        Map<String, Map<String, Object>> answerList = stuExam.getAnswerList( );
        Map<String, Object> answer = answerList.get(Integer.toString(scoreQuestionRequest.getQId( ) - 1));
        answer.put("isJudge",true);
        answer.put("isRight",isRight);
        answer.put("score",scoreQuestionRequest.getScore());
        answerList.put(Integer.toString(scoreQuestionRequest.getQId( ) - 1),answer);
        stuExam.setScore(stuExam.getScore()+score);
        return ResultUtils.success(stuexamrecordService.updateById(stuExam));
    }
}