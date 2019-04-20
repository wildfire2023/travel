package com.waner.primary.common.result;

/**
 * @author Monster
 * @since 1.0.0-SNAPSHOT
 */
public enum CodeMsg {
    /**
     * 成功响应结果
     */
    SUCCESS(200, "成功"),
    /**
     * 失败响应结果
     */
    FAIL(500, "失败"),
    /**
     *
     */
    SERVER_ERROR(500500, "系统错误"),
    /**
     * 空用户
     */
    USER_NULL(500001, "空用户"),
    /**
     * 账号或密码错误
     */
    USER_PASS_ERROR(500002, "账号或密码错误"),
    /**
     * 用户注册失败
     */
    USER_REGISTER_ERROR(500003, "用户注册失败"),
    /**
     * 邮件发送失败
     */
    MAIL_SEND_ERROR(500004, "邮件发送失败"),
    /**
     * 空邮箱
     */
    MAIL_NULL(500005, "邮箱为空"),
    /**
     * 用户邮箱已存在
     */
    USER_EXIST_EMAIL(500006, "用户邮箱已存在"),
    /**
     * 用户昵称已存在
     */
    USER_EXIST_NICKNAME(500007, "用户昵称已存在"),
    /**
     * 缓存超过限时
     */
    CODE_TIME_OUT(500008, "缓存失效"),
    /**
     * 验证码错误
     */
    VERCODE_ERROR(500009, "验证码错误"),
    /**
     * 空文件
     */
    EMPTY_FILE(500010, "空文件"),
    /**
     * 游记发表失败
     */
    ESSAY_PUSH_FAIL(500011, "游记发表失败");
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
        return "CodeMsg{" + "code=" + code + ", msg='" + msg + '\'' + "} " + super.toString();
    }
}
