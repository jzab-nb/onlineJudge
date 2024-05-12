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
public class GARequest {
    private Integer bankId;
    private Integer questionNum;
    private Integer needDifficulty;
    private Integer resultNum;
}
