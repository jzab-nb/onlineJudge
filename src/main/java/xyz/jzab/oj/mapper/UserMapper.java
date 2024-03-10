package xyz.jzab.oj.mapper;

import org.apache.ibatis.annotations.Mapper;
import xyz.jzab.oj.model.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author 86131
* @description 针对表【user(用户表)】的数据库操作Mapper
* @createDate 2024-03-07 17:36:14
* @Entity xyz.jzab.oj.model.entity.User
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {

}




