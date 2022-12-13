package com.zy.test.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * @author {庄勇}
 * @since 2022/12/13 14:11
 */
public class BaseController {
  // 这些对象何以直接被子类使用
  protected HttpServletRequest request;
  protected HttpServletResponse response;
  protected HttpSession session;

  @ModelAttribute
  public void setReqAndRes(HttpServletRequest req, HttpServletResponse res) {
    this.request = req;
    this.response = res;
    this.session = req.getSession();
  }
}
