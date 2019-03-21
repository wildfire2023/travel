package com.waner.primary.web.service.impl;

import com.waner.primary.web.mapper.TravelRecommendMapper;
import com.waner.primary.web.service.RecommendService;
import org.springframework.stereotype.Service;

/**
 * 推荐内容服务实现类
 *
 * @author Monster
 * @since 1.0.0-SNAPSHOT
 */
@Service
public class RecommendServiceImpl implements RecommendService{

    private final TravelRecommendMapper travelRecommendMapper;

    public RecommendServiceImpl(TravelRecommendMapper travelRecommendMapper) {
        this.travelRecommendMapper = travelRecommendMapper;
    }


}
