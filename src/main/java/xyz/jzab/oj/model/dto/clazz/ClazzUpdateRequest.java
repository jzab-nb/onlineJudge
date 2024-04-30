package xyz.jzab.oj.model.dto.clazz;

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
public class ClazzUpdateRequest {
    private Integer id;
    /**
     * 班级名称
     */
    private String name;

    /**
     * 教师ID
     */
    private Integer teacherId;
}
