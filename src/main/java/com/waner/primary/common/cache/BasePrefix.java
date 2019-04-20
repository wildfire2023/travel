package com.waner.primary.common.cache;

/**
 * Redis key公共前缀
 *
 * @author Monster
 * @since 1.0.0-SNAPSHOT
 */
public abstract class BasePrefix implements KeyPrefix {
    private int seconds;
    private String prefix;

    /**
     * 永不过期的key设置
     *
     * @param prefix
     */
    protected BasePrefix(String prefix) {
        this(prefix, 0);
    }

    protected BasePrefix(String prefix, int seconds) {
        this.prefix = prefix;
        this.seconds = seconds;
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
