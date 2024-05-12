package xyz.jzab.oj.model.dto.question;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @author JZAB
 * @from http://vip.jzab.xyz
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionAddRequest {
    private String type;

    /**
     * 题目内容
     */
    private Map<String,Object> content;

    /**
     * 关联的题库ID
     */
    private Integer questionBankId;
}
