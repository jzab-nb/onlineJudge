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
public class PaperVo {
    private Integer id;

    /**
     * 试卷名字
     */
    private String name;

    /**
     * 试卷介绍
     */
    private String introduce;

    /**
     * 试卷题目列表
     */
    private Integer[] questionList;

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
}
