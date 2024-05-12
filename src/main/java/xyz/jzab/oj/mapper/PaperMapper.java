package xyz.jzab.oj.mapper;

import org.apache.ibatis.annotations.Mapper;
import xyz.jzab.oj.model.entity.Paper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author 86131
* @description 针对表【paper(试卷表)】的数据库操作Mapper
* @createDate 2024-05-11 09:31:13
* @Entity xyz.jzab.oj.model.entity.Paper
*/
@Mapper
public interface PaperMapper extends BaseMapper<Paper> {

}




