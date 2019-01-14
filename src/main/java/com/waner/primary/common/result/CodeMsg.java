package com.waner.primary.common.result;

/**
 * @author Monster
 * @date 2019/1/13 15:49
 * @since 1.8
 */
public enum CodeMsg {
    /**
     * 成功响应结果
     */
    SUCCESS(200, "成功"),
    /**
     * 失败响应结果
     */
    FAIL(500,"失败"),
    /**
     *
     */
    SERVER_ERROR(500500, "系统错误")
    ;

    private Integer code;
    private String msg;

    CodeMsg(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    @Override
    public String toString() {
        return "CodeMsg{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                "} " + super.toString();
    }
}
