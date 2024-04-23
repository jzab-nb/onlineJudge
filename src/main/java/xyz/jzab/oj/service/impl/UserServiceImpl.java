package xyz.jzab.oj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.BeanUtils;
import xyz.jzab.oj.common.ErrorCode;
import xyz.jzab.oj.common.PageRequest;
import xyz.jzab.oj.common.ResultUtils;
import xyz.jzab.oj.constant.SysConstant;
import xyz.jzab.oj.exception.BusinessException;
import xyz.jzab.oj.model.dto.user.UserAddRequest;
import xyz.jzab.oj.model.dto.user.UserUpdateRequest;
import xyz.jzab.oj.model.entity.User;
import xyz.jzab.oj.model.enums.FileTypeEnums;
import xyz.jzab.oj.model.vo.LoginUserVo;
import xyz.jzab.oj.model.vo.UserVo;
import xyz.jzab.oj.service.UserService;
import xyz.jzab.oj.mapper.UserMapper;
import org.springframework.stereotype.Service;
import xyz.jzab.oj.utils.FileUtils;
import xyz.jzab.oj.utils.JwtUtils;

import java.util.List;

/**
* @author 86131
* @description 针对表【user(用户表)】的数据库操作Service实现
* @createDate 2024-03-07 17:36:14
*/
@Service
// 实现类继承ServiceImpl,实现UserService接口
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService {

    @Resource
    FileUtils fileUtils;

    public LoginUserVo createLoginVo(User user) {
        LoginUserVo loginUserVo = new LoginUserVo( );
        BeanUtils.copyProperties(user, loginUserVo);
        loginUserVo.setToken(JwtUtils.createToken(user.getId( )));
        return loginUserVo;
    }

    @Override
    public LoginUserVo login(String username, String password) {
        // 用户登录
        QueryWrapper<User> query = new QueryWrapper<>( );
        query.eq("username", username).eq("password", password);
        User user = this.getOne(query);
        if (user == null) throw new BusinessException(ErrorCode.PARAMS_ERROR.getCode( ), "用户名或密码错误");
        return createLoginVo(user);
    }

    @Override
    public LoginUserVo updateToken(String token) {
        // 更新token
        Integer id = JwtUtils.getUserIdFromToken(token);
        User user = this.getById(id);
        if (user == null) throw new BusinessException(ErrorCode.PARAMS_ERROR.getCode( ), "Token错误");
        return createLoginVo(user);
    }

    @Override
    public User getLoginUser(HttpServletRequest request) {
        String token = request.getHeader("token");
        Integer id = JwtUtils.getUserIdFromToken(token);
        User user = this.getById(id);
        if(user==null) throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR, "未登录");
        return user;
    }

    @Override
    public Boolean addUser(UserAddRequest userAddRequest, User loginUser, HttpSession session) {
        User user = new User( );
        BeanUtils.copyProperties(userAddRequest, user);
        user.setCreateUser(loginUser.getId());
        user.setUpdateUser(loginUser.getId());
        this.save(user);
        if(!StringUtils.isBlank(userAddRequest.getAvatar())){
            user.setAvatar(fileUtils.StoreTemp(session, FileTypeEnums.USER_AVATAR, loginUser.getId()));
            // 保存文件的操作
        }else{
            user.setAvatar(SysConstant.DEFAULT_IMG);
        }
        return this.updateById(user);
    }

    @Override
    public Boolean delUser(Integer id, User loginUser) {
        User user = this.getById(id);
        if(user==null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR.getCode( ), "用户不存在");
        }
        user.setUpdateUser(loginUser.getId());
        this.updateById(user);
        return this.removeById(user);
    }

    @Override
    public Boolean updateUser(UserUpdateRequest userUpdateRequest, User loginUser, HttpSession session) {
        User user = this.getById(userUpdateRequest.getId());
        if(user==null) throw new BusinessException(ErrorCode.PARAMS_ERROR.getCode( ), "用户不存在");
        if(!StringUtils.isBlank(userUpdateRequest.getAvatar())){
            // 保存文件的操作
            userUpdateRequest.setAvatar(fileUtils.StoreTemp(session, FileTypeEnums.USER_AVATAR, user.getId()));
        }else{
            userUpdateRequest.setAvatar(user.getAvatar());
        }
        BeanUtils.copyProperties(userUpdateRequest, user);
        user.setCreateUser(loginUser.getId());
        user.setUpdateUser(loginUser.getId());
        return this.updateById(user);
    }

    @Override
    public Page<UserVo> listPage(PageRequest pageRequest) {
        Page<User> userPage = new Page<>(pageRequest.getCurrent( ), pageRequest.getSize( ));
        QueryWrapper<User> wrapper = new QueryWrapper<>( );
        if(pageRequest.getDesc()!=null && StringUtils.isNotBlank(pageRequest.getOrderBy())){
            if(pageRequest.getDesc()){
                wrapper.orderByDesc(pageRequest.getOrderBy( ));
            }else{
                wrapper.orderByAsc(pageRequest.getOrderBy());
            }
        }
        this.page(userPage,wrapper);
        Page<UserVo> userVoPage = new Page<>(pageRequest.getCurrent( ), pageRequest.getSize( ), userPage.getTotal( ));
        userVoPage.setRecords(this.getVos(userPage.getRecords()));
        return userVoPage;
    }

    @Override
    public List<UserVo> getVos(List<User> users) {
        // 通过流式计算转换成vo列表
        return users.stream().map(this::getVo).toList( );
    }

    @Override
    public UserVo getVo(User user) {
        UserVo userVo = new UserVo( );
        BeanUtils.copyProperties(user, userVo);
        return userVo;
    }
}