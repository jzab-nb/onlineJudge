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
public class CourseVo {
    private Integer id;

    /**
     * 课程名字
     */
    private String name;

    /**
     * 课程图片
     */
    private String img;

    /**
     * 课程介绍
     */
    private String introduce;

    /**
     * 是否公开
     */
    private Integer open;

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