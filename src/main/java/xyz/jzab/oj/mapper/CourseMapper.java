package xyz.jzab.oj.mapper;

import org.apache.ibatis.annotations.Mapper;
import xyz.jzab.oj.model.entity.Course;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author 86131
* @description 针对表【course(课程表)】的数据库操作Mapper
* @createDate 2024-05-03 09:43:26
* @Entity xyz.jzab.oj.model.entity.Course
*/
@Mapper
public interface CourseMapper extends BaseMapper<Course> {

}




