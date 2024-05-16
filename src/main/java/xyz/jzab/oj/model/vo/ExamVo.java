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
public class ExamVo {
    private Integer id;

    /**
     * 考试标题
     */
    private String title;

    /**
     * 考试简介
     */
    private String introduce;

    /**
     * 关联的试卷ID
     */
    private Integer paperId;

    private Integer courseId;

    /**
     * 考试状态
     */
    private String status;

    /**
     * 是否删除
     */
    private Integer isDelete;

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
