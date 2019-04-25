package com.waner.primary.web.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.waner.primary.web.entity.TravelComment;
import com.waner.primary.web.entity.TravelEssay;
import com.waner.primary.web.entity.TravelEssayComment;
import com.waner.primary.web.mapper.TravelCommentMapper;
import com.waner.primary.web.mapper.TravelEssayMapper;
import com.waner.primary.web.mapper.TravelUserMapper;
import com.waner.primary.web.service.EssayCommentService;
import com.waner.primary.web.vo.CommentWithUser;
import com.waner.primary.web.vo.Reply;
import com.waner.primary.web.vo.ResponseWithTag;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EssayCommentServiceImpl implements EssayCommentService {
  private final TravelCommentMapper commentMapper;
  private final TravelUserMapper travelUserMapper;
  private final TravelEssayMapper travelEssayMapper;

  public EssayCommentServiceImpl(
      TravelCommentMapper commentMapper,
      TravelUserMapper travelUserMapper,
      TravelEssayMapper travelEssayMapper) {
    this.commentMapper = commentMapper;
    this.travelUserMapper = travelUserMapper;
    this.travelEssayMapper = travelEssayMapper;
  }

  /**
   * 插入评论--->事务操作
   *
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
    int relativeRet = commentMapper.insertEssayCommentRelative(essayId, travelComment.getId());
    return commentRet + relativeRet;
  }

  /**
   * 查询文章对应的评论列表信息
   *
   * @param essayId
   * @param limit
   * @param page
   * @return
   */
  @Override
  public List<CommentWithUser> listComments(Integer essayId, int limit, Integer page) {
    Page<CommentWithUser> pageHelper = new Page<>();
    pageHelper.setSize(limit);
    pageHelper.setCurrent(page);

    IPage<CommentWithUser> pageVo = commentMapper.queryCommentsWithUser(pageHelper, essayId);
    return pageVo.getRecords();
  }

  /**
   * 查询文章对应的评论列表总数
   *
   * @param essayId
   * @return
   */
  @Override
  public int getCommentsCountWithEssay(Integer essayId) {
    return commentMapper.queryCommentsWithUserCount(essayId);
  }

  /**
   * 删除与给定文章编号相关的评论
   *
   * @param essayId
   * @return
   */
  @Override
  @Transactional
  public void deleteCommentWithEssayId(Integer essayId) {
    List<TravelEssayComment> essayComments = commentMapper.queryCommentIdsByEssayId(essayId);
    List<Integer> commentIds =
        essayComments
            .parallelStream()
            .map(TravelEssayComment::getTravelCommentId)
            .collect(Collectors.toList());
    // 批量删除评论
    commentIds.forEach(commentMapper::deleteById);
    // 删除游记评论关联表
    commentMapper.deleteEssayComment(essayId);
  }

  /**
   * 查询游记以及游记对应的评论
   *
   * @param userId
   * @return
   */
  @Override
  public List<ResponseWithTag> queryReplysByUser(Integer userId) {
    List<CommentWithUser> commentsWithUser = commentMapper.queryCommentsByUserId(userId);
    List<ResponseWithTag> results = Lists.newArrayList();
    commentsWithUser.forEach(
        commentWithUser -> {
          ResponseWithTag result =
              ResponseWithTag.builder()
                  .tag("游记")
                  .responseContent(commentWithUser.getContent())
                  .responseCreateTime(commentWithUser.getCreateTime())
                  .userNickname(commentWithUser.getNickname())
                  .userHeadImgUrl(commentWithUser.getImgUrl())
                  .build();
          Integer commentId = commentWithUser.getId();
          int essayId = commentMapper.queryEssayIdByCommentId(commentId);
          int commentsCount = commentMapper.queryCommentsWithUserCount(essayId);
          TravelEssay travelEssay = travelEssayMapper.selectByPrimaryKey(essayId);
          result
              .setCommentsCount(commentsCount)
              .setArticleId(travelEssay.getId())
              .setArticleCreateTime(travelEssay.getCreateTime())
              .setArticleTitle(travelEssay.getTitle());
          results.add(result);
        });
    return results;
  }

  @Override
  public List<CommentWithUser> queryReplysAccoring2Condition(Reply reply) {
    List<CommentWithUser> result = null;
    if (reply == null) {
      result = commentMapper.queryWithContent(null);
    } else {
      result = commentMapper.queryWithContent(reply.getTitle());
    }
    return result;
  }

  @Override
  @Transactional
  public void remove(Integer id) {
    commentMapper.deleteByPrimaryKey(id);
    commentMapper.deleteEssayCommentAccording2CommentId(id);
  }
}
