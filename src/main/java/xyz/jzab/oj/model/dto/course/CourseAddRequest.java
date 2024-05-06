package xyz.jzab.oj.model.dto.course;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author JZAB
 * @from http://vip.jzab.xyz
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseAddRequest {

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
}
