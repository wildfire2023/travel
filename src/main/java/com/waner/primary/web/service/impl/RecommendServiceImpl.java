package com.waner.primary.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.waner.primary.common.cache.ViewKey;
import com.waner.primary.web.entity.TravelRecommend;
import com.waner.primary.web.mapper.TravelRecommendMapper;
import com.waner.primary.web.service.RecommendService;
import com.waner.primary.web.vo.ArticleWithTag;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 推荐内容服务实现类
 *
 * @author Monster
 * @since 1.0.0-SNAPSHOT
 */
@Service
public class RecommendServiceImpl implements RecommendService {

  public static final String USER = "user";
  public static final String ADMINISTRATOR = "administrator";

  private final TravelRecommendMapper travelRecommendMapper;
  private final RedisService redisService;

  public RecommendServiceImpl(
      TravelRecommendMapper travelRecommendMapper, RedisService redisService) {
    this.travelRecommendMapper = travelRecommendMapper;
    this.redisService = redisService;
  }

  /**
   * 新增推荐内容
   *
   * @param recommend
   * @return 新增推荐成功标志
   */
  @Override
  public int addRecommend(TravelRecommend recommend) {
    recommend.setCreateTime(new Date());
    int insertRet = travelRecommendMapper.insert(recommend);
    return insertRet;
  }

  /**
   * 删除指定推荐内容
   *
   * @param recommends
   * @return
   */
  @Override
  @Transactional
  public int remove(TravelRecommend[] recommends) {
    List<Integer> ids =
        Lists.newArrayList(recommends)
            .parallelStream()
            .map(TravelRecommend::getId)
            .collect(Collectors.toList());
    // 删除缓存
    ids.forEach(id -> redisService.deleteViewKey(id, ViewKey.RECOMMEND_KEY));
    return travelRecommendMapper.deleteBatchIds(ids);
  }

  /**
   * 获取单条推荐内容
   *
   * @param id
   * @return
   */
  @Override
  public TravelRecommend getOneRecommend(Integer id) {
    QueryWrapper<TravelRecommend> wrapper = new QueryWrapper<>();
    wrapper.eq("id", id);
    return travelRecommendMapper.selectOne(wrapper);
  }

  @Override
  public int modifyRecommend(TravelRecommend recommend) {
    return travelRecommendMapper.updateById(recommend);
  }

  /**
   * 根据给定条件查找列表
   *
   * @param pattern
   * @return
   */
  @Override
  public List<TravelRecommend> getListWithSearchPattern(String pattern) {
    QueryWrapper<TravelRecommend> wrapper = new QueryWrapper<>();
    wrapper.like("title", pattern);
    return travelRecommendMapper.selectList(wrapper);
  }

  /**
   * 根据推荐编号获取推荐列表
   *
   * @param recommendIds
   * @return
   */
  @Override
  public List<ArticleWithTag> getListByIds(List<Integer> recommendIds) {
    if (recommendIds == null || recommendIds.size() == 0) {
      return null;
    }
    QueryWrapper<TravelRecommend> wrapper = new QueryWrapper<>();
    wrapper.in("id", recommendIds);
    List<TravelRecommend> recommends = travelRecommendMapper.selectList(wrapper);
    ArrayList<ArticleWithTag> results = Lists.newArrayList();
    recommends.forEach(
        recommend -> {
          ArticleWithTag articleWithTag =
              ArticleWithTag.builder()
                  .id(recommend.getId())
                  .tag("推荐")
                  .createTime(recommend.getCreateTime())
                  .delFLag(recommend.getDelFlag())
                  .title(recommend.getTitle())
                  .build();
          results.add(articleWithTag);
        });
    return results;
  }

  /**
   * 分页查询
   *
   * @param checkStatus
   * @param travelRecommend
   * @param limit
   * @param page
   * @return
   */
  @Override
  public List<TravelRecommend> getList(
      String checkStatus, TravelRecommend travelRecommend, int limit, int page) {
    // 分页
    Page<TravelRecommend> pageHelper = new Page<>();
    pageHelper.setSize(limit);
    pageHelper.setCurrent(page);

    IPage<TravelRecommend> pageVo = null;
    // 未删除推荐内容

    // 已发表推荐
    if ("pushed".equals(checkStatus)) {
      pageVo = travelRecommendMapper.selectPageVo(pageHelper, 1, travelRecommend.getTitle());
    } else {
      pageVo = travelRecommendMapper.selectPageVo(pageHelper, null, travelRecommend.getTitle());
    }
    return pageVo.getRecords();
  }

  /**
   * 查询未删除的推荐数量
   *
   * @param checkStatus
   * @param travelRecommend
   * @return
   */
  @Override
  public int getCount(String checkStatus, TravelRecommend travelRecommend) {
    QueryWrapper<TravelRecommend> wrapper = new QueryWrapper<>();
    // 设置未删除查询条件
    wrapper.eq("del_flag", 0);
    // 搜索
    if (travelRecommend.getTitle() != null) {
      wrapper.like("title", travelRecommend.getTitle());
    }
    if ("pushed".equals(checkStatus)) {
      wrapper.eq("push_flag", 1);
    }
    return travelRecommendMapper.selectCount(wrapper);
  }
}
