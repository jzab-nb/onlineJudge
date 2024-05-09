package xyz.jzab.oj.model.dto.questionBank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author JZAB
 * @from http://vip.jzab.xyz
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionBankUpdateRequest {
    private Integer id;

    /**
     * 题库名字
     */
    private String name;

    /**
     * 题库介绍
     */
    private String introduce;

    /**
     * 关联的课程ID
     */
    private Integer courseId;
}
