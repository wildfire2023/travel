package com.waner.primary.common.cache;

/**
 * 不同类型Redis key 提供统一前缀
 * @author Monster
 * @date 2019/2/5 17:33
 * @since 1.8
 */
public interface KeyPrefix {
    /**
     * 设置key缓存时间
     * @return
     */
    int expireSeconds();

    /**
     * 获取前缀
     * @return
     */
    String getPrefix();
}
