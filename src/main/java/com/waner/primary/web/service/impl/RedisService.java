package com.waner.primary.web.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.waner.primary.common.cache.BasePrefix;
import com.waner.primary.common.cache.CollectionKey;
import com.waner.primary.common.util.RedisUtil;
import com.waner.primary.web.dto.TopMap;
import org.springframework.stereotype.Service;

import java.util.*;

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
                  CollectionKey.RECOMMEND_KEY, "user-id:" + userId + ":article-id:" + articleId));
    } else if ("essay".equals(type)) {
      optional =
          Optional.ofNullable(
              redisUtil.get(
                  CollectionKey.ESSAY_KEY, "user-id:" + userId + ":article-id:" + articleId));
    } else {
      optional =
          Optional.ofNullable(
              redisUtil.get(
                  CollectionKey.QUESTION_KEY, "user-id:" + userId + ":article-id:" + articleId));
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
          CollectionKey.RECOMMEND_KEY, "user-id:" + userId + ":article-id:" + articleId, articleId);
    } else if ("essay".equals(type)) {
      redisUtil.set(
          CollectionKey.ESSAY_KEY, "user-id:" + userId + ":article-id:" + articleId, articleId);
    } else {
      redisUtil.set(
          CollectionKey.QUESTION_KEY, "user-id:" + userId + ":article-id:" + articleId, articleId);
    }
  }

  public void delCollectionState(String type, Integer articleId, Integer userId) {
    if ("recommend".equals(type)) {
      redisUtil.delete(
          CollectionKey.RECOMMEND_KEY, "user-id:" + userId + ":article-id:" + articleId);
    } else if ("essay".equals(type)) {
      redisUtil.delete(CollectionKey.ESSAY_KEY, "user-id:" + userId + ":article-id:" + articleId);
    } else {
      redisUtil.delete(
          CollectionKey.QUESTION_KEY, "user-id:" + userId + ":article-id:" + articleId);
    }
  }

  /**
   * 根据用户编号获取分好类的文章编号
   *
   * @param userId
   * @return
   */
  public Map<String, List<Integer>> getCollectionArticlesId(Integer userId) {
    Map<String, List<Integer>> map = Maps.newHashMap();
    Set<String> keys = redisUtil.keys("CollectionKey:type:*:user-id:" + userId + ":article-id:*");
    List<Integer> essayIdList = Lists.newArrayList();
    List<Integer> questionIdList = Lists.newArrayList();
    List<Integer> recommendIdList = Lists.newArrayList();
    keys.forEach(
        key -> {
          if (key.contains("essay")) {
            int resultBefore = key.lastIndexOf(":");
            String idStr = key.substring(resultBefore + 1);
            essayIdList.add(Integer.valueOf(idStr));
          } else if (key.contains("question")) {
            int resultBefore = key.lastIndexOf(":");
            String idStr = key.substring(resultBefore + 1);
            questionIdList.add(Integer.valueOf(idStr));
          } else {
            int resultBefore = key.lastIndexOf(":");
            String idStr = key.substring(resultBefore + 1);
            recommendIdList.add(Integer.valueOf(idStr));
          }
        });
    map.put("essay", essayIdList);
    map.put("question", questionIdList);
    map.put("recommend", recommendIdList);
    return map;
  }

  /**
   * 根据redis推荐查询
   *
   * @return
   */
  public List<TopMap> top(String type) {
    Set<String> keys = redisUtil.keys("ViewKey:type:" + type + "*");
    ArrayList<TopMap> results = Lists.newArrayList();
    keys.forEach(
        key -> {
          int last = key.lastIndexOf(":");
          String substring = key.substring(0, last);
          int secondLast = substring.lastIndexOf(":");
          String viewNumStr = substring.substring(secondLast + 1);
          Number number = redisUtil.get(key);
          TopMap topMap =
              TopMap.builder()
                  .articleId(Integer.valueOf(viewNumStr))
                  .viewNUm(number.intValue())
                  .build();
          results.add(topMap);
        });
    results.sort((r1, r2) -> r2.getViewNUm() - r1.getViewNUm());
    return results;
  }
}
