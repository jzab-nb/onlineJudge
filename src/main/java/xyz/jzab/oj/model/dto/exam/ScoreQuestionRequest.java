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
public class ScoreQuestionRequest {
    private Integer stuExamId;
    private Integer qId;
    private Integer score;
}
