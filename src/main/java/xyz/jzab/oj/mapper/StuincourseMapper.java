package xyz.jzab.oj.mapper;

import org.apache.ibatis.annotations.Mapper;
import xyz.jzab.oj.model.entity.Stuincourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author 86131
* @description 针对表【stuincourse(学生课程关联表)】的数据库操作Mapper
* @createDate 2024-05-04 10:44:37
* @Entity xyz.jzab.oj.model.entity.Stuincourse
*/
@Mapper
public interface StuincourseMapper extends BaseMapper<Stuincourse> {

}




