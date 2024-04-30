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
public class ClazzVo {
    private Integer id;

    /**
     * 班级名称
     */
    private String name;

    /**
     * 教师ID
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
