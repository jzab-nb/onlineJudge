package xyz.jzab.oj.mapper;

import org.apache.ibatis.annotations.Mapper;
import xyz.jzab.oj.model.entity.Stuinclazz;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author 86131
* @description 针对表【stuinclazz(学生班级关联表)】的数据库操作Mapper
* @createDate 2024-04-30 11:28:36
* @Entity xyz.jzab.oj.model.entity.Stuinclazz
*/
@Mapper
public interface StuinclazzMapper extends BaseMapper<Stuinclazz> {

}




