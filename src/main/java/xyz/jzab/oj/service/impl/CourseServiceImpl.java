package xyz.jzab.oj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import xyz.jzab.oj.model.entity.Course;
import xyz.jzab.oj.service.CourseService;
import xyz.jzab.oj.mapper.CourseMapper;
import org.springframework.stereotype.Service;

/**
* @author 86131
* @description 针对表【course(课程表)】的数据库操作Service实现
* @createDate 2024-05-03 09:43:26
*/
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course>
    implements CourseService{

}




