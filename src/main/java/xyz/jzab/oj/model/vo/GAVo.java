package xyz.jzab.oj.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.jzab.oj.ga.GAPaper;

import java.util.List;

/**
 * @author JZAB
 * @from http://vip.jzab.xyz
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GAVo {
    private Integer bankId;
    private Integer questionNum;
    private Integer needDifficulty;
    private List<GAPaper> result;
}
