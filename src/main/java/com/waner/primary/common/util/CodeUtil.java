package com.waner.primary.common.util;

import org.springframework.util.DigestUtils;

import java.util.UUID;

/**
 * 编码工具类
 *
 * @author Monster
 * @since 1.0.0-SNAPSHOT
 */
public final class CodeUtil {

    /**
     * 使用Spring提供的
     *
     * @param string
     * @return
     */
    public static String md5(String string) {
        return DigestUtils.md5DigestAsHex(string.getBytes());
    }

    /**
     * 随机6位验证码
     *
     * @return
     */
    public static String randomCode() {
        return UUID.randomUUID().toString().replaceAll("-", "").substring(0, 6);
    }

    public static void main(String[] args) {
        String md5String = randomCode();
        System.out.println(md5String);
    }
}
