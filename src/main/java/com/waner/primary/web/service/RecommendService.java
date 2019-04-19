package com.waner.primary.web.service;

import com.waner.primary.web.entity.TravelRecommend;

import java.util.List;

/**
 * 推荐内容服务
 *
 * @author Monster
 * @since 1.0.0-SNAPSHOT
 */
public interface RecommendService {
  int addRecommend(TravelRecommend recommend);

  List<TravelRecommend> getList(
      String checkStatus, TravelRecommend travelRecommend, int limit, int page);

  int getCount(String checkStatus, TravelRecommend travelRecommend);

  int remove(TravelRecommend[] recommend);

  TravelRecommend getOneRecommend(Integer id);

  int modifyRecommend(TravelRecommend recommend);
}
