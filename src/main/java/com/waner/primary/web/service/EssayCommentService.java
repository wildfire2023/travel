package com.waner.primary.web.service;

import com.waner.primary.web.vo.CommentWithUser;
import com.waner.primary.web.vo.Reply;
import com.waner.primary.web.vo.ResponseWithTag;

import java.util.List;

public interface EssayCommentService {
  int add(Integer essayId, Integer userId, String content);

  List<CommentWithUser> listComments(Integer essayId, int limit, Integer page);

  int getCommentsCountWithEssay(Integer essayId);

  void deleteCommentWithEssayId(Integer essayId);

  List<ResponseWithTag> queryReplysByUser(Integer userId);

  List<CommentWithUser> queryReplysAccoring2Condition(Reply reply);

  void remove(Integer id);
}
