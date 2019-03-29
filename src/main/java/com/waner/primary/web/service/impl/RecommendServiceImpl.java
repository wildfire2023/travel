package com.waner.primary.web.service.impl;

import com.waner.primary.web.entity.TravelRecommend;
import com.waner.primary.web.mapper.TravelRecommendMapper;
import com.waner.primary.web.service.RecommendService;
import org.springframework.stereotype.Service;

import java.util.Date;

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


    /**
     * 新增推荐内容
     * @param recommend
     * @return 新增推荐成功标志
     */
    @Override
    public int addRecommend(TravelRecommend recommend) {
        recommend.setCreateTime(new Date());
        int insertRet = travelRecommendMapper.insert(recommend);
        return insertRet;
    }
}
