package com.waner.primary.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.waner.primary.web.entity.TravelComment;

public interface TravelCommentMapper extends BaseMapper<TravelComment> {
    int deleteByPrimaryKey(Integer id);

    int insert(TravelComment record);

    int insertSelective(TravelComment record);

    TravelComment selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TravelComment record);

    int updateByPrimaryKey(TravelComment record);
}