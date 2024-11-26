package xyz.jzab.oj.common;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author JZAB
 * @from http://vip.jzab.xyz
 */
// 分页的返回结果
public class  PageResult<T> {
    // 总条数
    private Long total;
    // 总页数
    private Long pages;
    // 数据
    private List<T> records;
    // 将数据转为统一的分页结果
    public static <P,T> PageResult<T> toPageResult(Page<P> page,Class<T> tClass){
        PageResult<T> result = new PageResult<T>();
        result.total = page.getTotal();
        result.pages = page.getPages();

        result.records = BeanUtil.copyToList(page.getRecords(),tClass);

        return result;
    }

    public static <P,T> PageResult<T> toPageResult(Page<P> page, Class<T> tClass, Function<P,T> func){
        PageResult<T> result = new PageResult<T>();
        result.total = page.getTotal();
        result.pages = page.getPages();

        result.records = page.getRecords().stream( ).map(func).collect(Collectors.toList( ));

        return result;
    }
}
