package com.waner.primary.common.cache;

/**
 * 用户存储前缀
 * @author Monster
 * @since 1.0.0-SNAPSHOT
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
