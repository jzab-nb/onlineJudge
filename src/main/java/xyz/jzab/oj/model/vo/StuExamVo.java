package xyz.jzab.oj.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author JZAB
 * @from http://vip.jzab.xyz
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StuExamVo {
    private Integer id;

    /**
     * 学生ID
     */
    private Integer stuId;

    /**
     * 考试ID
     */
    private Integer examId;

    /**
     * 考试答案
     */
    // { 答案,得分,是否正确,是否已经判完
    //  0:{answer:"", score:0, isRight:"YES|NO|HALF",isJudge:false}
    // }
    private Map<String, Map<String,Object>> answerList;

    // 题目列表
    private List<QuestionVo> questionList;

    /**
     * 考试分数
     */
    private Integer score;

    /**
     * 创建者
     */
    private Integer createUser;

    /**
     * 更新者
     */
    private Integer updateUser;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}
