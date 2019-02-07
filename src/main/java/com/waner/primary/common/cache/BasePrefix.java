package com.waner.primary.common.cache;

/**
 * Redis key公共前缀
 *
 * @author Monster
 * @date 2019/2/5 17:35
 * @since 1.8
 */
public abstract class BasePrefix implements KeyPrefix {
    protected int seconds;
    protected String prefix;


    /**
     * 永不过期的key设置
     *
     * @param prefix
     */
    protected BasePrefix(String prefix) {
        this(0, prefix);
    }

    protected BasePrefix(int seconds, String prefix) {
        this.seconds = seconds;
        this.prefix = prefix;
    }

    @Override
    public int expireSeconds() {
        return seconds;
    }

    @Override
    public String getPrefix() {
        String className = getClass().getSimpleName();
        return className + ":" + prefix + ":";
    }
}
