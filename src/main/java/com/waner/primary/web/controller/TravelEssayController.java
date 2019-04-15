package com.waner.primary.web.controller;

import com.waner.primary.common.exception.GlobalException;
import com.waner.primary.common.result.CodeMsg;
import com.waner.primary.common.result.Response;
import com.waner.primary.web.entity.SysUser;
import com.waner.primary.web.entity.TravelEssay;
import com.waner.primary.web.entity.TravelRecommend;
import com.waner.primary.web.service.TravelEssayService;
import com.waner.primary.web.vo.EssayWithUser;
import com.waner.primary.web.vo.TableResult;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 用户游记相关内容控制器
 * @author Monster
 * @since 1.0.0-SNAPSHOT
 */
@Controller
@RequestMapping("travel-essay")
public class TravelEssayController {

    private final TravelEssayService essayService;

    public TravelEssayController(TravelEssayService essayService) {
        this.essayService = essayService;
    }


    /**
     * 返回到前端游记列表
     * @return
     */
    @GetMapping("list-page")
    public String frontEssayListPage() {
        return "front/essay-list";
    }

    /**
     * 返回前端编写游记页面
     * @return
     */
    @GetMapping("page")
    public String travelEssayPage() {
        return "front/travel-essay-form";
    }

    /**
     * 游记发表
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
        SysUser user = (SysUser) httpSession.getAttribute("sessionUser");
        essay.setSysUserId(user.getId());
        int ret = essayService.save(essay);
        return ret > 0 ? Response.success("发表成功，审核通过即可展示") : Response.fail(CodeMsg.ESSAY_PUSH_FAIL);
    }

    /**
     * 返回后台游记审核页面
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
    public TableResult<List<EssayWithUser>> getTableData(@RequestParam(value = "role", required = false) String role,
                                                         EssayWithUser essay,
                                                 int limit,
                                                 int page) {
        boolean checkPushFlag = false;
        if ("user".equals(role)) {
            checkPushFlag = true;
        }
        List<EssayWithUser> essays =  essayService.getList(essay, limit, page, checkPushFlag);
        int count = essayService.getCount(essay, checkPushFlag);
        return new TableResult<>(200, "" ,count, essays);
    }

    /**
     * 返回到游记审核详情页面
     * @return
     */
    @GetMapping("edit-page")
    public String editPage() {
        return "/background/app/content/essay-listform";
    }


    /**
     * 游记发表审核--->状态扭转
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
     * @return
     */
    @GetMapping("detail-page")
    public String toDetailPage(@RequestParam("id") Integer id, HttpServletRequest request) {
        request.setAttribute("id", id);
        return "front/essay-details";
    }


}
