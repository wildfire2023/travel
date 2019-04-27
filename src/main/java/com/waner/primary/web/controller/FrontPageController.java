package com.waner.primary.web.controller;

import com.waner.primary.web.dto.TopMap;
import com.waner.primary.web.entity.TravelRecommend;
import com.waner.primary.web.service.RecommendService;
import com.waner.primary.web.service.impl.RedisService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 前台控制页面
 *
 * @author Monster
 * @since 1.0.0-SNAPSHOT
 */
@Controller
@RequestMapping("front/page")
public class FrontPageController {

  private final RedisService redisService;
  private final RecommendService recommendService;

  public FrontPageController(RedisService redisService, RecommendService recommendService) {
    this.redisService = redisService;
    this.recommendService = recommendService;
  }

  /**
   * 跳转登录页面
   *
   * @return
   */
  @GetMapping("login")
  public String login() {
    return "login";
  }

  /**
   * 跳转注册页面
   *
   * @return
   */
  @GetMapping("register")
  public String register() {
    return "reg";
  }

  /**
   * 忘记密码页面
   *
   * @return
   */
  @GetMapping("forget")
  public String forget() {
    return "forget";
  }

  /**
   * 前台主页面
   *
   * @return
   */
  @GetMapping("index")
  public String index(Model model) {
    List<TopMap> recommend = redisService.top("recommend");
    List<TravelRecommend> recommends = recommendService.top(recommend, false);
    model.addAttribute("topThree", recommends);
    return "front/index";
  }

  @GetMapping("search-page")
  public String searchPage(HttpServletRequest request, String title) {
    request.setAttribute("pattern", title);
    return "front/search";
  }
}
