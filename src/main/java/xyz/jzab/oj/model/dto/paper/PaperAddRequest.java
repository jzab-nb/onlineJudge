package xyz.jzab.oj.model.dto.paper;

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
public class PaperAddRequest {
    private String name;

    /**
     * 试卷介绍
     */
    private String introduce;

    /**
     * 试卷题目列表
     */
    private Integer[] questionList;
}