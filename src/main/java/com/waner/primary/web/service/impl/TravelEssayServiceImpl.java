package com.waner.primary.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.waner.primary.common.cache.ViewKey;
import com.waner.primary.common.result.Response;
import com.waner.primary.web.dto.TopMap;
import com.waner.primary.web.entity.TravelEssay;
import com.waner.primary.web.entity.TravelRecommend;
import com.waner.primary.web.entity.TravelUser;
import com.waner.primary.web.mapper.TravelEssayMapper;
import com.waner.primary.web.mapper.TravelUserMapper;
import com.waner.primary.web.service.EssayCommentService;
import com.waner.primary.web.service.TravelEssayService;
import com.waner.primary.web.vo.ArticleWithTag;
import com.waner.primary.web.vo.EssayWithUser;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Monster
 * @since 1.0.0-SNAPSHOT
 */
@Service
public class TravelEssayServiceImpl implements TravelEssayService {

  private final TravelEssayMapper essayMapper;
  private final TravelUserMapper userMapper;
  private final EssayCommentService essayCommentService;
  private final RedisService redisService;

  public TravelEssayServiceImpl(
      TravelEssayMapper essayMapper,
      TravelUserMapper userMapper,
      EssayCommentService essayCommentService,
      RedisService redisService) {
    this.essayMapper = essayMapper;
    this.userMapper = userMapper;
    this.essayCommentService = essayCommentService;
    this.redisService = redisService;
  }

  /**
   * 执行保存操作
   *
   * @param essay
   * @return
   */
  @Override
  public int save(TravelEssay essay) {
    return essayMapper.insert(essay);
  }

  /**
   * 根据搜索条件查询列表
   *
   * @param essay
   * @param limit
   * @param page
   * @param checkPushFlag
   * @return
   */
  @Override
  public List<EssayWithUser> getList(
      EssayWithUser essay, int limit, int page, boolean checkPushFlag) {
    // 分页
    Page<EssayWithUser> pageHelper = new Page<>();
    pageHelper.setSize(limit);
    pageHelper.setCurrent(page);

    IPage<EssayWithUser> pageVo = null;

    // 前台查询
    if (checkPushFlag) {
      pageVo = essayMapper.selectPageVo(pageHelper, essay, 1);
      // 后台所有查询
    } else {
      pageVo = essayMapper.selectPageVo(pageHelper, essay, null);
    }
    return pageVo.getRecords();
  }

  /**
   * 查询总条数
   *
   * @param essay
   * @param checkPushFlag
   * @return
   */
  @Override
  public int getCount(EssayWithUser essay, boolean checkPushFlag) {
    if (checkPushFlag) {
      return essayMapper.count(essay, 1);
    } else {
      return essayMapper.count(essay, null);
    }
  }

  /**
   * 审核游记
   *
   * @param essay
   * @return
   */
  @Override
  public int modify(TravelEssay essay) {
    // 设置发表标志位1
    essay.setPushFlag((byte) 1);
    return essayMapper.updateByPrimaryKeySelective(essay);
  }

  /**
   * 批量删除游记
   *
   * @param essays
   * @return
   */
  @Override
  @Transactional
  public int remove(TravelEssay[] essays) {
    List<Integer> ids =
        Lists.newArrayList(essays)
            .parallelStream()
            .map(TravelEssay::getId)
            .collect(Collectors.toList());
    // 删除游记相关评论
    ids.forEach(
        id -> {
          essayCommentService.deleteCommentWithEssayId(id);
          // 删除缓存
          redisService.deleteViewKey(id, ViewKey.ESSAY_KEY);
        });
    return essayMapper.deleteBatchIds(ids);
  }

  /**
   * 根据游记主键获取游记详情信息
   *
   * @param id
   * @return
   */
  @Override
  @Transactional
  public EssayWithUser getEssayDetail(Integer id) {
    TravelEssay travelEssay = essayMapper.selectByPrimaryKey(id);
    int userId = travelEssay.getSysUserId();
    TravelUser travelUser = userMapper.selectByPrimaryKey(userId);
    EssayWithUser essayWithUser = new EssayWithUser();
    // 复制properties
    BeanUtils.copyProperties(travelEssay, essayWithUser);
    BeanUtils.copyProperties(travelUser, essayWithUser);
    return essayWithUser;
  }

  @Override
  public List<TravelEssay> queryEssayByUser(Integer userId) {
    QueryWrapper<TravelEssay> queryWrapper = new QueryWrapper<>();
    queryWrapper.eq("sys_user_id", userId);
    queryWrapper.eq("del_flag", (byte) 0);
    return essayMapper.selectList(queryWrapper);
  }

  @Override
  public List<TravelEssay> getListWithSearchPattern(String pattern) {
    QueryWrapper<TravelEssay> wrapper = new QueryWrapper<>();
    wrapper.like("title", pattern);
    return essayMapper.selectList(wrapper);
  }

  /**
   * 根据游记编号获取文章列表
   *
   * @param essayIds
   * @return
   */
  @Override
  public List<ArticleWithTag> getListByIds(List<Integer> essayIds) {
    if (essayIds == null || essayIds.size() == 0) {
      return null;
    }
    QueryWrapper<TravelEssay> wrapper = new QueryWrapper<>();
    wrapper.in("id", essayIds);
    List<TravelEssay> recommends = essayMapper.selectList(wrapper);
    ArrayList<ArticleWithTag> results = Lists.newArrayList();
    recommends.forEach(
        essay -> {
          ArticleWithTag articleWithTag =
              ArticleWithTag.builder()
                  .id(essay.getId())
                  .tag("游记")
                  .createTime(essay.getCreateTime())
                  .delFLag(essay.getDelFlag())
                  .title(essay.getTitle())
                  .build();
          results.add(articleWithTag);
        });
    return results;
  }

  @Override
  public Response<List<TravelEssay>> top(List<TopMap> essays) {
    if (essays == null || essays.size() == 0) {
      return Response.fail(null);
    }
    List<Integer> ids = Lists.newArrayList();
    essays.forEach(top -> ids.add(top.getArticleId()));
    List<TravelEssay> recommendList = new ArrayList<>();
    // 按照排名先后查询
    ids.forEach(
        id -> {
          // 查询已发布的游记
          TravelEssay essay = essayMapper.selectPublishedEssay(id);
          if (essay != null) {
            recommendList.add(essay);
          }
        });
    List<TravelEssay> results = null;
    if (recommendList.size() > 10) {
      results = recommendList.subList(0, 10);
    }
    return results != null ? Response.success(results) : Response.success(recommendList);
  }
}
