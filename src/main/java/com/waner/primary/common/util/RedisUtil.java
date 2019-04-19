package com.waner.primary.common.util;

import com.alibaba.fastjson.JSON;
import com.waner.primary.common.cache.KeyPrefix;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * Redis缓存操作工具类
 *
 * @author Monster
 * @since 1.0.0-SNAPSHOT
 */
@Component
public class RedisUtil {

  private final RedisTemplate<String, Object> template;

  public RedisUtil(RedisTemplate<String, Object> redisTemplate) {
    this.template = redisTemplate;
  }

  /**
   * set 操作
   *
   * @param prefix
   * @param key
   * @param value
   * @param <T>
   * @return
   */
  public <T> boolean set(KeyPrefix prefix, String key, T value) {
    ValueOperations<String, Object> ops = template.opsForValue();
    String str = bean2String(value);
    if (str == null || str.length() <= 0) {
      return false;
    }
    String realKey = prefix.getPrefix() + key;
    int expire = prefix.expireSeconds();
    if (expire > 0) {
      ops.set(realKey, str, expire, TimeUnit.SECONDS);
    } else {
      ops.set(realKey, str);
    }
    return true;
  }

  /**
   * get 操作
   *
   * @param prefix
   * @param key
   * @param <T>
   * @return
   */
  public <T> T get(KeyPrefix prefix, String key) {
    String realKey = prefix.getPrefix() + key;
    Object o = template.opsForValue().get(realKey);
    return (T) o;
  }

  /**
   * 实体类转为字符串
   *
   * @param value
   * @param <T>
   * @return
   */
  public static <T> String bean2String(T value) {
    if (value == null) {
      return null;
    }
    Class<?> clazz = value.getClass();
    if (clazz == int.class || clazz == Integer.class) {
      return "" + value;
    } else if (clazz == String.class) {
      return (String) value;
    } else if (clazz == long.class || clazz == Long.class) {
      return "" + value;
    } else {
      return JSON.toJSONString(value);
    }
  }

  /**
   * 字符串转为实体类
   *
   * @param str
   * @param clazz
   * @param <T>
   * @return
   */
  public static <T> T string2Bean(String str, Class<T> clazz) {
    if (str == null || str.length() <= 0 || clazz == null) {
      return null;
    }
    if (clazz == int.class || clazz == Integer.class) {
      return (T) Integer.valueOf(str);
    } else if (clazz == String.class) {
      return (T) str;
    } else if (clazz == long.class || clazz == Long.class) {
      return (T) Long.valueOf(str);
    } else {
      return JSON.toJavaObject(JSON.parseObject(str), clazz);
    }
  }
}
