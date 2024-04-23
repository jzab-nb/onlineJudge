package xyz.jzab.oj.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import xyz.jzab.oj.common.PageRequest;
import xyz.jzab.oj.model.dto.user.UserAddRequest;
import xyz.jzab.oj.model.dto.user.UserUpdateRequest;
import xyz.jzab.oj.model.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import xyz.jzab.oj.model.vo.LoginUserVo;
import xyz.jzab.oj.model.vo.UserVo;

import java.util.List;

/**
* @author 86131
* @description 针对表【user(用户表)】的数据库操作Service
* @createDate 2024-03-07 17:36:14
*/
public interface UserService extends IService<User> {
    LoginUserVo createLoginVo(User user);
    LoginUserVo login(String username, String password);
    LoginUserVo updateToken(String token);
    User getLoginUser(HttpServletRequest request);
    Boolean addUser(UserAddRequest userAddRequest, User loginUser, HttpSession session);
    Boolean delUser(Integer id, User loginUser);
    Boolean updateUser(UserUpdateRequest userUpdateRequest, User loginUser, HttpSession session);
    Page<UserVo> listPage(PageRequest pageRequest);
    List<UserVo> getVos(List<User> users);
    UserVo getVo(User user);
}
