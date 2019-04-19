package com.waner.primary.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 前台控制页面
 *
 * @author Monster
 * @since 1.0.0-SNAPSHOT
 */
@Controller
@RequestMapping("front/page")
public class FrontPageController {

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
  public String index() {
    return "front/index";
  }
}
