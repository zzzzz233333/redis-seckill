package com.zy.test.controller;

/**
 * @author {庄勇}
 * @since 2022/12/13 15:59
 */

import com.zy.test.entity.ResultInfo;
import com.zy.test.service.FollowService;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 关注/取关控制层
 * @author zjq
 */
@RestController
public class FollowController {

  @Resource
  private FollowService followService;
  @Resource
  private HttpServletRequest request;

  /**
   * 关注/取关
   *
   * @param followUserId 关注的用户ID
   * @param isFollowed    是否关注 1=关注 0=取消
   * @param access_token  登录用户token
   * @return
   */
  @PostMapping("/{followUserId}")
  public ResultInfo follow(@PathVariable Integer followUserId,
                           @RequestParam int isFollowed,
                           String access_token) {
    ResultInfo resultInfo = followService.follow(followUserId,
                                                 isFollowed, access_token, request.getServletPath());
    return resultInfo;
  }

  /**
   * 共同关注列表
   *
   * @param userId
   * @param access_token
   * @return
   */
  @GetMapping("commons/{userId}")
  public ResultInfo findCommonsFriends(@PathVariable Integer userId,
                                       String access_token) {
    return followService.findCommonsFriends(userId, access_token, request.getServletPath());
  }

}