package xyz.jzab.oj.mapper;

import org.apache.ibatis.annotations.Mapper;
import xyz.jzab.oj.model.entity.Questionbank;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author 86131
* @description 针对表【questionbank(题库表)】的数据库操作Mapper
* @createDate 2024-05-06 14:55:56
* @Entity xyz.jzab.oj.model.entity.Questionbank
*/
@Mapper
public interface QuestionbankMapper extends BaseMapper<Questionbank> {

}




