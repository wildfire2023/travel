package com.waner.primary.common.util;

import org.springframework.util.DigestUtils;

/**
 * <p>编码工具类</p>
 *
 * @author Monster
 * @date 2019/1/4 14:42
 * @since 1.8
 */
public final class CodeUtil {

    /**
     * 使用Spring提供的
     * @param string
     * @return
     */
    public static String md5(String string) {
        return DigestUtils.md5DigestAsHex(string.getBytes());
    }

    public static void main(String[] args) {
        String md5String = md5("123");
        System.out.println(md5String.length());
    }
}
