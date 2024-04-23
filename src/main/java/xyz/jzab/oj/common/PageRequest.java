package xyz.jzab.oj.common;

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
public class PageRequest {
    // 当前页
    private Integer current;
    // 页面大小
    private Integer size;
    // 排序字段
    private String orderBy;
    // 是否降序
    private Boolean desc;
}
