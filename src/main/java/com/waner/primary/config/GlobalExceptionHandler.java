package com.waner.primary.config;

import com.waner.primary.common.exception.GlobalException;
import com.waner.primary.common.result.Response;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局异常处理
 *
 * @author Monster
 * @since 1.0.0-SNAPSHOT
 */
@ControllerAdvice
@RestController
public class GlobalExceptionHandler {

  /**
   * 处理自定义全局异常
   *
   * @param request
   * @param e
   * @return
   */
  @ExceptionHandler(value = GlobalException.class)
  public Response exceptionHandler(HttpServletRequest request, Exception e) {
    GlobalException ex = (GlobalException) e;
    return Response.fail(ex.getCode(), ex.getMessage());
  }
}
