package com.waner.primary.web.service;

import com.waner.primary.web.vo.CommentWithUser;

import java.util.List;

public interface EssayCommentService {
  int add(Integer essayId, Integer userId, String content);

  List<CommentWithUser> listComments(Integer essayId, int limit, Integer page);

  int getCommentsCountWithEssay(Integer essayId);
}
