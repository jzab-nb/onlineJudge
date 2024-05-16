package xyz.jzab.oj.mapper;

import org.apache.ibatis.annotations.Mapper;
import xyz.jzab.oj.model.entity.Exam;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author 86131
* @description 针对表【exam(考试表)】的数据库操作Mapper
* @createDate 2024-05-13 09:44:47
* @Entity xyz.jzab.oj.model.entity.Exam
*/
@Mapper
public interface ExamMapper extends BaseMapper<Exam> {

}