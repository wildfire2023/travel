package com.waner.primary.config;

import com.waner.primary.common.exception.GlobalException;
import com.waner.primary.common.result.CodeMsg;
import com.waner.primary.common.result.Response;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局异常处理
 *
 * @author Monster
 * @date 2019/1/26 16:32
 * @since 1.8
 */
@ControllerAdvice
@RestController
public class GlobalExceptionHandler {

    /**
     * 处理自定义全局异常
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(value = GlobalException.class)
    public Response<String> exceptionHandler(HttpServletRequest request, Exception e) {
        GlobalException ex = (GlobalException) e;
        return Response.fail(ex.getCodeMsg());
    }

}
