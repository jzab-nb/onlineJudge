package xyz.jzab.oj.model.dto.course;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author JZAB
 * @from http://vip.jzab.xyz
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddByClazzRequest {
    // 课程id
    private Integer courseId;
    // 班级id列表
    private List<Integer> clazzIds;
}
