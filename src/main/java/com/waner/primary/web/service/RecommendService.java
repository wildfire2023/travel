package com.waner.primary.web.service;

import com.waner.primary.web.entity.TravelRecommend;

/**
 * 推荐内容服务
 * @author Monster
 * @since 1.0.0-SNAPSHOT
 */
public interface RecommendService {
    int addRecommend(TravelRecommend recommend);
}
