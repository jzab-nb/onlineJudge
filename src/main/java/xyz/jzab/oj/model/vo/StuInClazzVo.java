package xyz.jzab.oj.model.vo;

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
public class StuInClazzVo {
    private Integer id;

    /**
     * 学生ID
     */
    private Integer stuId;

    private String stuName;
    private String stuUserName;

    /**
     * 班级ID
     */
    private Integer clazzId;
}
