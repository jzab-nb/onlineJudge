package xyz.jzab.oj.common;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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

    public <T> Page<T> getPageObj(OrderItem ... orderItems){
        Page<T> page = Page.of(current, size);
        // 若设置了排序字段,则排序
        if(!StringUtils.isEmpty(orderBy)){
            page.addOrder(OrderItem.asc(orderBy));
        }else{
        // 否则按照传进来的排序
            page.addOrder(orderItems);
        }
        return page;
    }
}
