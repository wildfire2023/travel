package com.waner.primary.web.controller;

import com.waner.primary.common.cache.ViewKey;
import com.waner.primary.common.exception.GlobalException;
import com.waner.primary.common.result.CodeMsg;
import com.waner.primary.common.result.Response;
import com.waner.primary.web.entity.TravelRecommend;
import com.waner.primary.web.service.RecommendService;
import com.waner.primary.web.service.impl.RecommendServiceImpl;
import com.waner.primary.web.service.impl.RedisService;
import com.waner.primary.web.vo.RecommendWithView;
import com.waner.primary.web.vo.TableResult;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 推荐内容控制器
 *
 * @author Monster
 * @since 1.0.0-SNAPSHOT
 */
@Controller
@RequestMapping("recommend")
public class RecommendController {

  private final RecommendService recommendService;
  private final RedisService redisService;

  public RecommendController(RecommendService recommendService, RedisService redisService) {
    this.recommendService = recommendService;
    this.redisService = redisService;
  }

  /**
   * 推荐内容列表页面
   *
   * @return
   */
  @GetMapping("list")
  public String toRecommendList() {
    return "background/app/content/recommend-list";
  }

  /**
   * 返回前台推荐详情 添加redis浏览人数
   *
   * @param id
   * @return
   */
  @GetMapping("detail-page")
  public String toRecommendDetail(Integer id, HttpServletRequest request) {
    request.setAttribute("id", id);
    redisService.setViewNum(id, ViewKey.RECOMMEND_KEY);
    return "front/recommend-details";
  }

  /**
   * 表格请求接口
   *
   * @param role
   * @return
   */
  @GetMapping("table-data")
  @ResponseBody
  public TableResult<List<TravelRecommend>> getTableData(
      @RequestParam(value = "role", required = false) String role,
      TravelRecommend travelRecommend,
      int limit,
      int page) {
    String checkStatus = "";
    if (RecommendServiceImpl.USER.equals(role)) {
      // 已发布推荐内容
      checkStatus = "pushed";
    } else if (RecommendServiceImpl.ADMINISTRATOR.equals(role)) {
      // 所有推荐内容
      checkStatus = "all";
    }
    // 分页查询
    List<TravelRecommend> recommends =
        recommendService.getList(checkStatus, travelRecommend, limit, page);
    int count = recommendService.getCount(checkStatus, travelRecommend);
    return new TableResult<>(200, "", count, recommends);
  }

  /**
   * 返回单条详情页面所需推荐内容
   *
   * @param id
   * @return
   */
  @GetMapping("detail")
  @ResponseBody
  public Response<RecommendWithView> getOneTravelRecommend(Integer id) {
    TravelRecommend recommend = recommendService.getOneRecommend(id);
    Number viewNum = redisService.getViewNum(id, ViewKey.RECOMMEND_KEY);
    RecommendWithView withView = new RecommendWithView();
    BeanUtils.copyProperties(recommend, withView);
    withView.setViewNum(viewNum.longValue());
    return Response.success(withView);
  }

  /**
   * 文章添加、修改页面
   *
   * @return
   */
  @GetMapping("add-page")
  public String recommendAddPage() {
    return "background/app/content/recommend-listform";
  }

  /**
   * 发表推荐内容
   *
   * @return
   */
  @PostMapping("add")
  @ResponseBody
  public Response<String> addRecommend(@RequestBody TravelRecommend recommend) {
    if (ObjectUtils.isEmpty(recommend)) {
      throw new GlobalException("空参数", 500100);
    }
    int ret = recommendService.addRecommend(recommend);
    if (ret > 0) {
      return Response.success("添加成功");
    } else {
      return Response.fail(CodeMsg.FAIL);
    }
  }

  /**
   * 修改推荐内容
   *
   * @return
   */
  @PostMapping("modify")
  @ResponseBody
  public Response<String> modifyRecommend(TravelRecommend recommend) {
    if (ObjectUtils.isEmpty(recommend)) {
      throw new GlobalException("空参数", 500100);
    }
    int ret = recommendService.modifyRecommend(recommend);
    if (ret > 0) {
      return Response.success("修改成功");
    } else {
      return Response.fail(CodeMsg.FAIL);
    }
  }

  /**
   * 批量删除
   *
   * @param recommend
   * @return
   */
  @PostMapping("batch-delete")
  @ResponseBody
  public Response<String> removeRecommend(@RequestBody TravelRecommend[] recommend) {
    if (ObjectUtils.isEmpty(recommend)) {
      throw new GlobalException("空参数", 500100);
    }
    int ret = recommendService.remove(recommend);
    if (ret > 0) {
      return Response.success("删除成功");
    } else {
      return Response.fail(CodeMsg.FAIL);
    }
  }
}
