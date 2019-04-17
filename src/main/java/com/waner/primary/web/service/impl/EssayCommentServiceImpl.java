package com.waner.primary.web.service.impl;

import com.waner.primary.web.entity.TravelComment;
import com.waner.primary.web.mapper.TravelCommentMapper;
import com.waner.primary.web.mapper.TravelUserMapper;
import com.waner.primary.web.service.EssayCommentService;
import com.waner.primary.web.vo.CommentWithUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class EssayCommentServiceImpl implements EssayCommentService {
    private final TravelCommentMapper commentMapper;
    private final TravelUserMapper travelUserMapper;

    public EssayCommentServiceImpl(TravelCommentMapper commentMapper, TravelUserMapper travelUserMapper) {
        this.commentMapper = commentMapper;
        this.travelUserMapper = travelUserMapper;
    }

    /**
     * 插入评论--->事务操作
     * @param essayId
     * @param userId
     * @param content
     * @return
     */
    @Transactional
    @Override
    public int add(Integer essayId, Integer userId, String content) {
        TravelComment travelComment = new TravelComment();
        travelComment.setContent(content);
        travelComment.setSysUserId(userId);
        travelComment.setDelFlag((byte) 0);
        travelComment.setCreateTime(new Date());
        // TODO: 2019/4/16 handle exception
        // 插入评论表
        int commentRet = commentMapper.insertTravelCommentMapper(travelComment);
        // 插入文章-评论表
        int relativeRet =  commentMapper.insertEssayCommentRelative(essayId, travelComment.getId());
        return commentRet + relativeRet;
    }

    /**
     * 查询文章对应的评论列表信息
     * @param essayId
     * @return
     */
    @Override
    public List<CommentWithUser> listComments(Integer essayId) {
        
        return null;
    }
}
