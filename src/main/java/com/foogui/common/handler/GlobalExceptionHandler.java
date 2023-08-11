package com.foogui.common.handler;

import com.foogui.common.domain.Result;
import com.foogui.common.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


    /**
     * 业务异常处理
     */
    @ExceptionHandler(BizException.class)
    public Result<Object> handleBizException(BizException e, HttpServletRequest request) {
        log.error(e.getMessage(), e);
        return Result.fail(e.getMessage());
    }

    /**
     * 参数校验异常处理
     */
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public Result<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.error(ex.getMessage(), ex);
        BindingResult bindingResult = ex.getBindingResult();
        StringBuilder sb = new StringBuilder("参数校验失败:");
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            sb.append(fieldError.getField()).append("：").append(fieldError.getDefaultMessage()).append(", ");
        }
        String msg = sb.toString();
        return Result.fail(msg);
    }

    /**
     * 参数校验异常处理
     */
    @ExceptionHandler({ConstraintViolationException.class})
    public Result<Object> handleConstraintViolationException(ConstraintViolationException ex) {
        log.error(ex.getMessage(), ex);
        return Result.fail("参数校验失败");
    }

    /**
     * 兜底处理其他异常
     */
    @ExceptionHandler(value =Exception.class)
    public Result<Object> handleException(HttpServletRequest req, Exception ex){
        log.error(ex.getMessage(), ex);
        return Result.fail(ex.getMessage());
    }

}
