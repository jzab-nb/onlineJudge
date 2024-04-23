package xyz.jzab.oj.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;
import xyz.jzab.oj.annotation.AuthCheck;
import xyz.jzab.oj.common.BaseResponse;
import xyz.jzab.oj.common.ErrorCode;
import xyz.jzab.oj.common.PageRequest;
import xyz.jzab.oj.common.ResultUtils;
import xyz.jzab.oj.exception.BusinessException;
import xyz.jzab.oj.model.dto.user.UserAddRequest;
import xyz.jzab.oj.model.dto.user.UserLoginRequest;
import xyz.jzab.oj.model.dto.user.UserUpdateRequest;
import xyz.jzab.oj.model.entity.User;
import xyz.jzab.oj.model.enums.UserRoleEnum;
import xyz.jzab.oj.model.vo.LoginUserVo;
import xyz.jzab.oj.model.vo.UserVo;
import xyz.jzab.oj.service.UserService;

/**
 * @author JZAB
 * @from http://vip.jzab.xyz
 */
@RestController
@RequestMapping("/user")
@CrossOrigin()
public class UserController {

    @Resource
    UserService userService;

    // 登录接口
    @PostMapping("/login")
    public BaseResponse<LoginUserVo> login(@RequestBody UserLoginRequest request){
        return ResultUtils.success(userService.login(request.getUsername(),request.getPassword()));
    }

    // 更新token接口
    @GetMapping("/updateToken")
    public BaseResponse<LoginUserVo> updateToken(HttpServletRequest request){
        return ResultUtils.success(userService.updateToken(request.getHeader("token")));
    }

    // 添加用户
    @PostMapping("/add")
    @AuthCheck(mustRole = {UserRoleEnum.ADMIN})
    public BaseResponse<Boolean> addUser(@RequestBody UserAddRequest userAddRequest, HttpServletRequest request){
        return ResultUtils.success(userService.addUser(userAddRequest, userService.getLoginUser(request), request.getSession()));
    }
    // 删除用户
    @PostMapping("/del/{id}")
    @AuthCheck(mustRole = {UserRoleEnum.ADMIN})
    public BaseResponse<Boolean> delUser(@PathVariable Integer id, HttpServletRequest request){
        return ResultUtils.success(userService.delUser(id, userService.getLoginUser(request )));
    }

    // 删除自己
    @PostMapping("/del/me")
    @AuthCheck(mustRole = {UserRoleEnum.ADMIN})
    public BaseResponse<Boolean> delMe(HttpServletRequest request){
        return ResultUtils.success(userService.delUser(userService.getLoginUser(request).getId(), userService.getLoginUser(request )));
    }

    // 更新用户
    @PostMapping("/update")
    @AuthCheck(mustRole = {UserRoleEnum.ADMIN})
    public BaseResponse<Boolean> updateUser(@RequestBody UserUpdateRequest userUpdateRequest, HttpServletRequest httpServletRequest){
        User loginUser = userService.getLoginUser(httpServletRequest);
        return ResultUtils.success(userService.updateUser(userUpdateRequest, loginUser, httpServletRequest.getSession()));
    }

    // 更新自己
    @PostMapping("/update/me")
    @AuthCheck()
    public BaseResponse<Boolean> updateMe(@RequestBody UserUpdateRequest userUpdateRequest, HttpServletRequest httpServletRequest){
        User loginUser = userService.getLoginUser(httpServletRequest);
        userUpdateRequest.setId(loginUser.getId());
        return ResultUtils.success(userService.updateUser(userUpdateRequest, loginUser, httpServletRequest.getSession()));
    }


    // 分页查询用户
    @GetMapping("/list/page")
    @AuthCheck(mustRole = {UserRoleEnum.ADMIN})
    public BaseResponse<Page<UserVo>> pageUser(PageRequest pageRequest){
        return ResultUtils.success(userService.listPage(pageRequest));
    }


    // id查询用户
    @GetMapping("/get/{id}")
    public BaseResponse<UserVo> getUser(@PathVariable Integer id){
        User user = userService.getById(id);
        if(user==null) throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "用户不存在");
        return ResultUtils.success(userService.getVo(user));
    }
}
