package com.waner.primary.web.controller;

import com.google.common.collect.Lists;
import com.waner.primary.common.exception.GlobalException;
import com.waner.primary.common.result.CodeMsg;
import com.waner.primary.common.result.Response;
import com.waner.primary.web.entity.TravelEssay;
import com.waner.primary.web.entity.TravelRecommend;
import com.waner.primary.web.service.EssayCommentService;
import com.waner.primary.web.service.QuestionResolverService;
import com.waner.primary.web.service.TravelEssayService;
import com.waner.primary.web.vo.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 用户游记相关内容控制器
 *
 * @author Monster
 * @since 1.0.0-SNAPSHOT
 */
@Controller
@RequestMapping("travel-essay")
public class TravelEssayController {

  private final TravelEssayService essayService;
  private final EssayCommentService commentService;
  private final QuestionResolverService questionResolverService;

  public TravelEssayController(
      TravelEssayService essayService,
      EssayCommentService commentService,
      QuestionResolverService questionResolverService) {
    this.essayService = essayService;
    this.commentService = commentService;
    this.questionResolverService = questionResolverService;
  }

  // -------------------------------------------------------------------------
  // 游记相关方法
  // -------------------------------------------------------------------------

  /**
   * 返回到前端游记列表
   *
   * @return
   */
  @GetMapping("list-page")
  public String frontEssayListPage() {
    return "front/essay-list";
  }

  /**
   * 返回前端编写游记页面
   *
   * @return
   */
  @GetMapping("page")
  public String travelEssayPage() {
    return "front/travel-essay-form";
  }

  /**
   * 游记发表
   *
   * @param essay 前端提交字段填充
   * @param httpSession
   * @return
   */
  @PostMapping("post")
  @ResponseBody
  public Response<String> postTravelEssay(TravelEssay essay, HttpSession httpSession) {
    // 空参数校验
    if (ObjectUtils.isEmpty(essay)) {
      throw new GlobalException("空参数", 500100);
    }
    SessionUser user = (SessionUser) httpSession.getAttribute("sessionUser");
    essay.setSysUserId(user.getId());
    int ret = essayService.save(essay);
    return ret > 0 ? Response.success("发表成功，审核通过即可展示") : Response.fail(CodeMsg.ESSAY_PUSH_FAIL);
  }

  /**
   * 返回后台游记审核页面
   *
   * @return
   */
  @GetMapping("list")
  public String essayList() {
    return "background/app/content/essay-list";
  }

  /**
   * 根据条件查询游记列表
   *
   * @param role
   * @param essay
   * @param limit
   * @param page
   * @return
   */
  @GetMapping("table-data")
  @ResponseBody
  public TableResult<List<EssayWithUser>> getTableData(
      @RequestParam(value = "role", required = false) String role,
      EssayWithUser essay,
      int limit,
      int page) {
    boolean checkPushFlag = false;
    if ("user".equals(role)) {
      checkPushFlag = true;
    }
    List<EssayWithUser> essays = essayService.getList(essay, limit, page, checkPushFlag);
    int count = essayService.getCount(essay, checkPushFlag);
    return new TableResult<>(200, "", count, essays);
  }

  /**
   * 返回到游记审核详情页面
   *
   * @return
   */
  @GetMapping("edit-page")
  public String editPage() {
    return "/background/app/content/essay-listform";
  }

  /**
   * 游记发表审核--->状态扭转
   *
   * @param essay
   * @return
   */
  @PostMapping("audit")
  @ResponseBody
  public Response<String> audit(TravelEssay essay) {
    if (ObjectUtils.isEmpty(essay)) {
      throw new GlobalException("空参数", 500100);
    }
    int ret = essayService.modify(essay);
    if (ret > 0) {
      return Response.success("审核成功");
    } else {
      return Response.fail(CodeMsg.FAIL);
    }
  }

  /**
   * 批量删除实现
   *
   * @param essays
   * @return
   */
  @PostMapping("batch-delete")
  @ResponseBody
  public Response<String> removeRecommend(@RequestBody TravelEssay[] essays) {
    if (ObjectUtils.isEmpty(essays)) {
      throw new GlobalException("空参数", 500100);
    }
    int ret = essayService.remove(essays);
    if (ret > 0) {
      return Response.success("删除成功");
    } else {
      return Response.fail(CodeMsg.FAIL);
    }
  }

  /**
   * 获取游记详情
   *
   * @param id
   * @return
   */
  @GetMapping("detail")
  @ResponseBody
  public Response<EssayWithUser> getEssayDetail(@RequestParam("id") Integer id) {
    return essayService.getEssayDetail(id);
  }

  /**
   * 返回游记详情页面
   *
   * @return
   */
  @GetMapping("detail-page")
  public String toDetailPage(@RequestParam("id") Integer id, HttpServletRequest request) {
    request.setAttribute("id", id);
    return "front/essay-details";
  }

  // -------------------------------------------------------------------------
  // 评论相关方法
  // -------------------------------------------------------------------------

  /**
   * 新增评论
   *
   * @param essayId
   * @param userId
   * @param content
   * @return
   */
  @PostMapping("add-comment")
  @ResponseBody
  public Response<String> addComment(Integer essayId, Integer userId, String content) {
    if (essayId == null || userId == null || StringUtils.isEmpty(content)) {
      throw new GlobalException("空参数", 500100);
    }
    int ret = commentService.add(essayId, userId, content);
    if (ret > 1) {
      return Response.success("评论成功");
    } else {
      return Response.fail(CodeMsg.FAIL);
    }
  }

  /**
   * 游记留言列表
   *
   * @param essayId
   * @return
   */
  @PostMapping("list-comments")
  @ResponseBody
  public TableResult<List<CommentWithUser>> listComments(Integer essayId, int limit, int page) {
    if (essayId == null) {
      throw new GlobalException("空参数", 500100);
    }
    List<CommentWithUser> comments = commentService.listComments(essayId, limit, page);
    int count = commentService.getCommentsCountWithEssay(essayId);
    // 返回成功响应的数据
    return new TableResult<>(200, "", count, comments);
  }

  // -------------------------------------------------------------------------
  // 回复管理
  // -------------------------------------------------------------------------

  @GetMapping("reply-list-page")
  public String redirectToReplyList() {
    return "background/app/content/reply-list";
  }

  /**
   * 查找回复列表信息
   *
   * @param reply
   * @param page
   * @param limit
   * @return
   */
  @GetMapping("reply-list")
  @ResponseBody
  public TableResult<List<Reply>> getReplyTable(Reply reply, int page, int limit) {
    List<CommentWithUser> commentsWithUser = commentService.queryReplysAccoring2Condition(reply);
    List<AnswerWithUser> answersWithUser =
        questionResolverService.queryReplysAccoring2Condition(reply);
    List<Reply> replies = Lists.newArrayList();
    commentsWithUser.forEach(
        commentWithUser -> {
          Reply essayReply =
              Reply.builder()
                  .id(commentWithUser.getId())
                  .createTime(commentWithUser.getCreateTime())
                  .tag("游记")
                  .title(commentWithUser.getContent())
                  .userHeadImgUrl(commentWithUser.getImgUrl())
                  .userNickname(commentWithUser.getNickname())
                  .build();
          replies.add(essayReply);
        });
    answersWithUser.forEach(
        answerWithUser -> {
          Reply essayReply =
              Reply.builder()
                  .id(answerWithUser.getId())
                  .createTime(answerWithUser.getCreateTime())
                  .tag("问答")
                  .title(answerWithUser.getContent())
                  .userHeadImgUrl(answerWithUser.getImgUrl())
                  .userNickname(answerWithUser.getNickname())
                  .build();
          replies.add(essayReply);
        });
    if (reply != null && reply.getTag() != null) {
      if ("游记".equals(reply.getTag())) {
        replies.removeIf(replyTemp -> "问答".equals(replyTemp.getTag()));
      } else if ("问答".equals(reply.getTag())) {
        replies.removeIf(replyTemp -> "游记".equals(replyTemp.getTag()));
      }
    }
    replies.sort((r1, r2) -> r2.getCreateTime().compareTo(r1.getCreateTime()));
    int start = (page - 1) * limit;
    int end = page * limit - 1;
    List<Reply> results = Lists.newArrayList();
    for (int i = start; i <= end; i++) {
      if (i > replies.size() - 1) {
        break;
      }
      results.add(replies.get(i));
    }
    return new TableResult<>(200, "", replies.size(), results);
  }

  /**
   * 批量删除
   *
   * @param replies
   * @return
   */
  @PostMapping("reply-batch-delete")
  @ResponseBody
  public Response<String> removeRecommend(@RequestBody Reply[] replies) {
    if (ObjectUtils.isEmpty(replies)) {
      throw new GlobalException("空参数", 500100);
    }
    List<Reply> replyList = Lists.newArrayList(replies);
    replyList.forEach(
        reply -> {
          if ("游记".equals(reply.getTag())) {
            commentService.remove(reply.getId());
          } else {
            questionResolverService.removeAnswer(reply.getId());
          }
        });
    return Response.success("删除成功");
  }
}
