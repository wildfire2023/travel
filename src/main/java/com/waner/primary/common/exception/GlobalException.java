package com.waner.primary.common.exception;

import lombok.Getter;

/**
 * 全局异常返回
 *
 * @author Monster
 * @since 1.0.0-SNAPSHOT
 */
@Getter
public class GlobalException extends RuntimeException {
  private Integer code;

  public GlobalException(String message, Integer code) {
    super(message);
    this.code = code;
  }
}
