package xyz.jzab.oj.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.jzab.oj.annotation.AuthCheck;
import xyz.jzab.oj.common.BaseResponse;
import xyz.jzab.oj.common.ErrorCode;
import xyz.jzab.oj.common.ResultUtils;
import xyz.jzab.oj.model.enums.UserRoleEnum;
import xyz.jzab.oj.model.vo.LoginUserVo;
import xyz.jzab.oj.service.UserService;

/**
 * @author JZAB
 * @from http://vip.jzab.xyz
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    UserService userService;

    @PostMapping("/login")
    public BaseResponse<LoginUserVo> login(){
        return ResultUtils.success(userService.login("jzab","aaa"));
    }

    @RequestMapping("/test1")
    @AuthCheck()
    public BaseResponse<Integer> test1(){
        return ResultUtils.success(1);
    }

    @RequestMapping("/test2")
    @AuthCheck(mustRole = {UserRoleEnum.ADMIN})
    public BaseResponse<Integer> test2(){
        return ResultUtils.success(2);
    }
}
