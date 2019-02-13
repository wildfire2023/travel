package com.waner.primary.common.exception;

import lombok.Getter;

/**
 * <p>全局异常返回</p>
 *
 * @author Monster
 * @date 2019/1/4 14:46
 * @since 1.8
 */
@Getter
public class GlobalException extends RuntimeException {
    private Integer code;


    public GlobalException(String message, Integer code) {
        super(message);
        this.code = code;
    }


}
