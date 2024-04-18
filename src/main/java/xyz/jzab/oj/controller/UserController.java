package xyz.jzab.oj.controller;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;
import xyz.jzab.oj.annotation.AuthCheck;
import xyz.jzab.oj.common.BaseResponse;
import xyz.jzab.oj.common.ResultUtils;
import xyz.jzab.oj.model.dto.user.UserLoginRequest;
import xyz.jzab.oj.model.vo.LoginUserVo;
import xyz.jzab.oj.service.UserService;

/**
 * @author JZAB
 * @from http://vip.jzab.xyz
 */
@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:8000", maxAge = 3600)
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

    // 分页查询用户
}
