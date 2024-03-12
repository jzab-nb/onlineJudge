package xyz.jzab.oj.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import xyz.jzab.oj.common.BaseResponse;
import xyz.jzab.oj.common.ErrorCode;
import xyz.jzab.oj.common.ResultUtils;

/**
 * @author JZAB
 * @from http://vip.jzab.xyz
 */
@RestControllerAdvice
@Slf4j
// 全局异常处理器
public class GlobalExceptionHandler {
    @ExceptionHandler(BusinessException.class)
    public BaseResponse<?> businessExceptionHandler(BusinessException e) {
        log.error("BusinessException", e);
        return ResultUtils.error(e.getCode(),e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public BaseResponse<?> runtimeExceptionHandler(RuntimeException e) {
        log.error("RuntimeException", e);
        return ResultUtils.error(ErrorCode.SYSTEM_ERROR, "系统内部异常");
    }
}