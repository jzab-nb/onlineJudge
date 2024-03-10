package xyz.jzab.oj.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.jzab.oj.common.BaseResponse;
import xyz.jzab.oj.common.ResultUtils;
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
        return ResultUtils.success(userService.login("abc","123"));
    }
}
