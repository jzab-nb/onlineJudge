package xyz.jzab.oj.mapper;

import org.apache.ibatis.annotations.Mapper;
import xyz.jzab.oj.model.entity.Clazz;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author 86131
* @description 针对表【clazz(班级表)】的数据库操作Mapper
* @createDate 2024-04-29 10:39:18
* @Entity xyz.jzab.oj.model.entity.Clazz
*/
@Mapper
public interface ClazzMapper extends BaseMapper<Clazz> {

}




