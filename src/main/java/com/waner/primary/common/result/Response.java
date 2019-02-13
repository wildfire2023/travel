package com.waner.primary.common.result;

import lombok.Data;

/**
 * <p>全局结果返回</p>
 *
 * @author Monster
 * @date 2019/1/4 14:46
 * @since 1.8
 */
@Data
public class Response<T> {
    private Integer code;
    private String message;
    private T data;

    private Response(T data) {
        this.data = data;
    }

    private Response(CodeMsg codeMsg) {
        if (codeMsg != null) {
            this.code = codeMsg.getCode();
            this.message = codeMsg.getMsg();
        }
    }

    private Response(T data, CodeMsg codeMsg) {
        this.data = data;
        if (codeMsg != null) {
            this.code = codeMsg.getCode();
            this.message = codeMsg.getMsg();
        }
    }

    private Response(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 返回成功响应，默认CodeMsg的SUCCESS
     *
     * @param data
     * @param <T>
     * @return
     */
    public static <T> Response<T> success(T data) {
        return new Response<>(data,CodeMsg.SUCCESS);
    }

    /**
     * 返回失败结果
     *
     * @param codeMsg
     * @param <T>
     * @return
     */
    public static <T> Response<T> fail(CodeMsg codeMsg) {
        return new Response<>(codeMsg);
    }


    /**
     * 作为异常结果返回
     * @param message
     * @param <T>
     * @return
     */
    public static <T> Response<T> fail(Integer code, String message) {
        return new Response<>(code, message);
    }
}
