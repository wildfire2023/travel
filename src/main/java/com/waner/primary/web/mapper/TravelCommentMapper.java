package com.waner.primary.web.mapper;

import com.waner.primary.web.entity.TravelComment;

public interface TravelCommentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TravelComment record);

    int insertSelective(TravelComment record);

    TravelComment selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TravelComment record);

    int updateByPrimaryKey(TravelComment record);
}