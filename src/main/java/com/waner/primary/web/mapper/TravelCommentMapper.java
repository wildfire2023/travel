package com.waner.primary.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.waner.primary.web.entity.TravelComment;
import com.waner.primary.web.vo.CommentWithUser;
import com.waner.primary.web.vo.EssayWithUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TravelCommentMapper extends BaseMapper<TravelComment> {
    int deleteByPrimaryKey(Integer id);

    int insertTravelCommentMapper(TravelComment record);

    int insertSelective(TravelComment record);

    TravelComment selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TravelComment record);

    int updateByPrimaryKey(TravelComment record);

    int insertEssayCommentRelative(@Param("essayId") Integer essayId,@Param("commentId") Integer commentId);

    IPage<CommentWithUser> queryCommentsWithUser(Page pageHelper, @Param("essayId") Integer essayId);

    int queryCommentsWithUserCount(@Param("essayId") Integer essayId);
}