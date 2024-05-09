package xyz.jzab.oj.model.vo;

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
public class QuestionBankVo {
    /**
     * 主键
     */
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

    /**
     * 关联的教师ID
     */
    private Integer teacherId;

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
