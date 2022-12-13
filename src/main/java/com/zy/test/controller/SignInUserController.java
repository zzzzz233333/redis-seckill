package com.zy.test.controller;

import com.zy.test.entity.ResultInfo;
import com.zy.test.entity.ShortUserInfo;
import com.zy.test.service.UserService;
import com.zy.test.util.ResultInfoUtil;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author {庄勇}
 * @since 2022/12/13 13:38
 */
public class SignInUserController  extends BaseController{

  @Autowired
  private UserService userService;
  /**
   * 根据 ids 查询用户信息
   *
   * @param ids
   * @return
   */
  @GetMapping("findByIds")
  public ResultInfo<List<ShortUserInfo>> findByIds(String ids) {
    List<ShortUserInfo> dinerInfos = userService.findByIds(ids);
    return ResultInfoUtil.buildSuccess(request.getServletPath(), dinerInfos);
  }
}
