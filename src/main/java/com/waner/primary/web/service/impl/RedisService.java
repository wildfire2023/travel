package com.waner.primary.web.service.impl;

import com.waner.primary.common.cache.BasePrefix;
import com.waner.primary.common.cache.Collectionkey;
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
   *
   * @param id
   * @param key
   * @return
   */
  public Number getViewNum(Integer id, BasePrefix key) {
    return redisUtil.get(key, "article-id:" + id + ":num");
  }

  /**
   * 根据view key进行删除
   *
   * @param id
   * @param key
   */
  public void deleteViewKey(Integer id, BasePrefix key) {
    redisUtil.delete(key, "article-id:" + id + ":num");
  }

  /**
   * 获取收藏键是否存在bool值
   *
   * @param type
   * @param articleId
   * @param userId
   * @return
   */
  public boolean getCollectionState(String type, Integer articleId, Integer userId) {
    Optional<Object> optional;
    if ("recommend".equals(type)) {
      optional =
          Optional.ofNullable(
              redisUtil.get(
                  Collectionkey.RECOMMEND_KEY, "user-id:" + userId + ":article-id:" + articleId));
    } else if ("essay".equals(type)) {
      optional =
          Optional.ofNullable(
              redisUtil.get(
                  Collectionkey.ESSAY_KEY, "user-id:" + userId + ":article-id:" + articleId));
    } else {
      optional =
          Optional.ofNullable(
              redisUtil.get(
                  Collectionkey.QUESTION_KEY, "user-id:" + userId + ":article-id:" + articleId));
    }
    if (optional.isPresent()) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * 设置收藏状态
   *
   * @param type
   * @param articleId
   * @param userId
   */
  public void setCollectionState(String type, Integer articleId, Integer userId) {
    if ("recommend".equals(type)) {
      redisUtil.set(
          Collectionkey.RECOMMEND_KEY, "user-id:" + userId + ":article-id:" + articleId, "exist");
    } else if ("essay".equals(type)) {
      redisUtil.set(
          Collectionkey.ESSAY_KEY, "user-id:" + userId + ":article-id:" + articleId, "exist");
    } else {
      redisUtil.set(
          Collectionkey.QUESTION_KEY, "user-id:" + userId + ":article-id:" + articleId, "exist");
    }
  }

  public void delCollectionState(String type, Integer articleId, Integer userId) {
    if ("recommend".equals(type)) {
      redisUtil.delete(
          Collectionkey.RECOMMEND_KEY, "user-id:" + userId + ":article-id:" + articleId);
    } else if ("essay".equals(type)) {
      redisUtil.delete(Collectionkey.ESSAY_KEY, "user-id:" + userId + ":article-id:" + articleId);
    } else {
      redisUtil.delete(Collectionkey.QUESTION_KEY, "user-id:" + userId + ":article-id:" + articleId);
    }
  }
}
