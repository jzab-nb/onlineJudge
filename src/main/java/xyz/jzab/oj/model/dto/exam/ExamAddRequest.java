package xyz.jzab.oj.model.dto.exam;

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
public class ExamAddRequest {
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
}
