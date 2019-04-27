package com.waner.primary.web.service.impl;

import com.waner.primary.common.cache.BasePrefix;
import com.waner.primary.common.util.RedisUtil;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RedisService {

  private final RedisUtil redisUtil;

  public RedisService(RedisUtil redisUtil) {
    this.redisUtil = redisUtil;
  }

  /**
   * 设置redis对应文章detail浏览次数
   *
   * @param id
   * @param key
   */
  public void setViewNum(Integer id, BasePrefix key) {
    Optional optional = Optional.ofNullable(redisUtil.get(key, "article-id:" + id + ":num"));
    if (optional.isPresent()) {
      redisUtil.increment(key, "article-id:" + id + ":num", 1);
    } else {
      redisUtil.set(key, "article-id:" + id + ":num", 1);
    }
  }

  /**
   * 根据view key获取viewNum值
   * @param id
   * @param key
   * @return
   */
  public Number getViewNum(Integer id, BasePrefix key) {
    return redisUtil.get(key, "article-id:" + id + ":num");
  }

  /**
   * 根据view key进行删除
   * @param id
   * @param key
   */
  public void deleteViewKey(Integer id, BasePrefix key) {
    redisUtil.delete(key,"article-id:" + id + ":num");
  }
}

