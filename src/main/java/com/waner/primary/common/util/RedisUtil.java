package com.waner.primary.common.util;

import com.alibaba.fastjson.JSON;
import com.waner.primary.common.cache.KeyPrefix;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.Set;
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
    // String str = bean2String(value);
    if (value == null || (value instanceof String && ((String) value).length() <= 0) ) {
      return false;
    }
    String realKey = prefix.getPrefix() + key;
    int expire = prefix.expireSeconds();
    if (expire > 0) {
      ops.set(realKey, value, expire, TimeUnit.SECONDS);
    } else {
      ops.set(realKey, value);
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

  public <T> T get(String key) {
    return (T) template.opsForValue().get(key);
  }

  /**
   * redis自增操作
   * @param prefix
   * @param key
   * @return
   */
  public long increment(KeyPrefix prefix, String key, int delta) {
    ValueOperations<String, Object> ops = template.opsForValue();
    String realKey = prefix.getPrefix() + key;
    return ops.increment(realKey, delta);
  }


  /**
   * 删除操作
   * @param prefix
   * @param key
   */
  public void delete(KeyPrefix prefix, String key) {
    String realKey = prefix.getPrefix() + key;
    template.delete(realKey);
  }

  /**
   * 查询匹配的所有key
   * @param pattern
   * @return
   */
  public Set<String> keys(String pattern) {
    return template.keys(pattern);
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
    if (clazz == String.class) {
      return (String) value;
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
    if (clazz == String.class) {
      return (T) str;
    } else {
      return JSON.toJavaObject(JSON.parseObject(str), clazz);
    }
  }


}
