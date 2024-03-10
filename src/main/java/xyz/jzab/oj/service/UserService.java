package xyz.jzab.oj.service;

import xyz.jzab.oj.model.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import xyz.jzab.oj.model.vo.LoginUserVo;

/**
* @author 86131
* @description 针对表【user(用户表)】的数据库操作Service
* @createDate 2024-03-07 17:36:14
*/
public interface UserService extends IService<User> {
    LoginUserVo login(String username, String password);
}
