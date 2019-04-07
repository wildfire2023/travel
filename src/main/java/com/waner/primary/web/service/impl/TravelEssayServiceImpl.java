package com.waner.primary.web.service.impl;

import com.waner.primary.web.entity.TravelEssay;
import com.waner.primary.web.mapper.TravelEssayMapper;
import com.waner.primary.web.service.TravelEssayService;
import org.springframework.stereotype.Service;

@Service
public class TravelEssayServiceImpl implements TravelEssayService {

    private final TravelEssayMapper essayMapper;

    public TravelEssayServiceImpl(TravelEssayMapper essayMapper) {
        this.essayMapper = essayMapper;
    }

    /**
     * 执行保存操作
     * @param essay
     * @return
     */
    @Override
    public int save(TravelEssay essay) {
        return essayMapper.insert(essay);
    }
}
