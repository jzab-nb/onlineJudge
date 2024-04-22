package xyz.jzab.oj.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;
import xyz.jzab.oj.annotation.AuthCheck;
import xyz.jzab.oj.common.BaseResponse;
import xyz.jzab.oj.common.ResultUtils;
import xyz.jzab.oj.model.dto.user.UserAddRequest;
import xyz.jzab.oj.model.dto.user.UserLoginRequest;
import xyz.jzab.oj.model.dto.user.UserUpdateRequest;
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
        return ResultUtils.success(null);
    }
    // 删除用户
    @PostMapping("/del/{id}")
    @AuthCheck(mustRole = {UserRoleEnum.ADMIN})
    public BaseResponse<Boolean> delUser(@PathVariable Integer id, HttpServletRequest request){
        return ResultUtils.success(null);
    }

    // 更新用户
    @PostMapping("/update")
    @AuthCheck(mustRole = {UserRoleEnum.ADMIN})
    public BaseResponse<Boolean> updateUser(@RequestBody UserUpdateRequest userUpdateRequest, HttpServletRequest httpServletRequest){
        return ResultUtils.success(null);
    }

    @PostMapping("/update/me")
    @AuthCheck(mustRole = {UserRoleEnum.ADMIN})
    public BaseResponse<Boolean> updateMe(@RequestBody UserUpdateRequest userUpdateRequest, HttpServletRequest httpServletRequest){
        return ResultUtils.success(null);
    }


    // 分页查询用户
    @GetMapping("/list/page")
    @AuthCheck(mustRole = {UserRoleEnum.ADMIN})
    public BaseResponse<Page<UserVo>> pageUser(HttpServletRequest request){
        return ResultUtils.success(null);
    }


    // id查询用户
    @GetMapping("/get/{id}")
    public BaseResponse<UserVo> getUser(@PathVariable Integer id, HttpServletRequest request){
        return ResultUtils.success(null);
    }
}
