package com.waner.primary.common.cache;

/**
 * 用户存储前缀
 * @author Monster
 * @date 2019/2/7 15:23
 * @since 1.8
 */
public class UserKey extends BasePrefix {
    protected UserKey(String prefix, int seconds) {
        super(prefix, seconds);
    }

    /**
     * 注册邮箱缓存前缀
     */
    public static UserKey MAIL_KEY = new UserKey("mail", 60);
}
