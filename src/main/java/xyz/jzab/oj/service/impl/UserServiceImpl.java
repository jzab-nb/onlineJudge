package xyz.jzab.oj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import xyz.jzab.oj.common.ResultUtils;
import xyz.jzab.oj.model.entity.User;
import xyz.jzab.oj.model.vo.LoginUserVo;
import xyz.jzab.oj.service.UserService;
import xyz.jzab.oj.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
* @author 86131
* @description 针对表【user(用户表)】的数据库操作Service实现
* @createDate 2024-03-07 17:36:14
*/
@Service
// 实现类继承ServiceImpl,实现UserService接口
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    @Override
    public LoginUserVo login(String username, String password) {
        return new LoginUserVo();
    }
}




