package xyz.jzab.oj.aop;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import xyz.jzab.oj.annotation.AuthCheck;
import xyz.jzab.oj.common.ErrorCode;
import xyz.jzab.oj.exception.BusinessException;
import xyz.jzab.oj.model.entity.User;
import xyz.jzab.oj.model.enums.UserRoleEnum;
import xyz.jzab.oj.service.UserService;
import xyz.jzab.oj.utils.JwtUtils;

import java.util.Arrays;

/**
 * @author JZAB
 * @from http://vip.jzab.xyz
 */
@Aspect
@Component
@Slf4j
public class AuthInterceptor {
    @Resource
    private UserService userService;

    @Around("@annotation(authCheck)")
    public Object doInterceptor(ProceedingJoinPoint joinPoint, AuthCheck authCheck) throws Throwable {
        // 获取必须的权限数组,有其中之一即可继续执行
        UserRoleEnum[] mustRole = authCheck.mustRole();
        System.out.println(Arrays.deepToString(mustRole));
        // 获取当前请求的上下文
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes( );
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest( );
        // 获取请求头中的token
        String token = request.getHeader("token");
        // token为空
        if(StringUtils.isBlank(token)){
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        // 解析token
        Integer userId = JwtUtils.getUserIdFromToken(token);
        // 获取当前登录的用户
        User userById = userService.getById(userId);
        if(userById == null){
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        // 遍历需要的权限列表,当前角色的权限满足其中之一即可
        for (UserRoleEnum userRoleEnum : mustRole) {
            // 需要的是登录或者当前用户的权限满足列表中的权限,允许继续
            if(userRoleEnum == UserRoleEnum.USER || userById.getRole().equals(userRoleEnum.getDesc())){
                return joinPoint.proceed();
            }
        }
        throw new BusinessException(ErrorCode.NO_AUTH_ERROR, "无权限");
    }
}