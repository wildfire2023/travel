package com.waner.primary.web.service.impl;

import com.waner.primary.web.mapper.TravelRecommendMapper;
import com.waner.primary.web.service.RecommendService;
import org.springframework.stereotype.Service;

/**
 * 推荐内容服务实现类
 * @author Monster
 * @date 2019/3/9 14:38
 * @since 1.8
 */
@Service
public class RecommendServiceImpl implements RecommendService{

    private final TravelRecommendMapper travelRecommendMapper;

    public RecommendServiceImpl(TravelRecommendMapper travelRecommendMapper) {
        this.travelRecommendMapper = travelRecommendMapper;
    }


}
