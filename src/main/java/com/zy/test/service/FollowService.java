package com.zy.test.service;



import com.zy.test.api.ApiConstant;
import com.zy.test.api.ParameterException;
import com.zy.test.api.RedisKeyConstant;
import com.zy.test.entity.Follow;
import com.zy.test.entity.ResultInfo;
import com.zy.test.entity.ShortUserInfo;
import com.zy.test.entity.SignInUserInfo;
import com.zy.test.mapper.FollowMapper;
import com.zy.test.util.AssertUtil;
import com.zy.test.util.BeanUtil;
import com.zy.test.util.ResultInfoUtil;
import com.zy.test.util.StrUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.LinkedHashMap;

/**
 * 关注/取关业务逻辑层
 * @author zjq
 */
@Service
public class FollowService {

  @Resource
  private RestTemplate restTemplate;
  @Autowired
  private FollowMapper followMapper;
  @Resource
  private RedisTemplate redisTemplate;


  /**
   * 关注/取关
   *
   * @param followUserId 关注的食客ID
   * @param isFollowed    是否关注 1=关注 0=取关
   * @param accessToken   登录用户token
   * @param path          访问地址
   * @return
   */
  public ResultInfo follow(Integer followUserId, int isFollowed,
                           String accessToken, String path) {
    // 是否选择了关注对象
    AssertUtil.isTrue(followUserId == null || followUserId < 1,
                      "请选择要关注的人");
    // 获取登录用户信息 (封装方法)
    SignInUserInfo dinerInfo = loadSignInDinerInfo(accessToken);
    // 获取当前登录用户与需要关注用户的关注信息
    Follow follow = followMapper.selectFollow(dinerInfo.getId(), followUserId);

    // 如果没有关注信息，且要进行关注操作 -- 添加关注
    if (follow == null && isFollowed == 1) {
      // 添加关注信息
      int count = followMapper.save(dinerInfo.getId(), followUserId);
      // 添加关注列表到 Redis
      if (count == 1) {
        addToRedisSet(dinerInfo.getId(), followUserId);
      }
      return ResultInfoUtil.build(ApiConstant.SUCCESS_CODE,
                                  "关注成功", path, "关注成功");
    }

    // 如果有关注信息，且目前处于关注状态，且要进行取关操作 -- 取关关注
    if (follow != null && follow.getIsValid() == 1 && isFollowed == 0) {
      // 取关
      int count = followMapper.update(follow.getId(), isFollowed);
      // 移除 Redis 关注列表
      if (count == 1) {
        removeFromRedisSet(dinerInfo.getId(), followUserId);
      }
      return ResultInfoUtil.build(ApiConstant.SUCCESS_CODE,
                                  "成功取关", path, "成功取关");
    }

    // 如果有关注信息，且目前处于取关状态，且要进行关注操作 -- 重新关注
    if (follow != null && follow.getIsValid() == 0 && isFollowed == 1) {
      // 重新关注
      int count = followMapper.update(follow.getId(), isFollowed);
      // 添加关注列表到 Redis
      if (count == 1) {
        addToRedisSet(dinerInfo.getId(), followUserId);
      }
      return ResultInfoUtil.build(ApiConstant.SUCCESS_CODE,
                                  "关注成功", path, "关注成功");
    }

    return ResultInfoUtil.buildSuccess(path, "操作成功");
  }

  /**
   * 添加关注列表到 Redis
   *
   * @param dinerId
   * @param followUserId
   */
  private void addToRedisSet(Integer dinerId, Integer followUserId) {
    redisTemplate.opsForSet().add(RedisKeyConstant.following.getKey() + dinerId, followUserId);
    redisTemplate.opsForSet().add(RedisKeyConstant.followers.getKey() + followUserId, dinerId);
  }

  /**
   * 移除 Redis 关注列表
   *
   * @param dinerId
   * @param followUserId
   */
  private void removeFromRedisSet(Integer dinerId, Integer followUserId) {
    redisTemplate.opsForSet().remove(RedisKeyConstant.following.getKey() + dinerId, followUserId);
    redisTemplate.opsForSet().remove(RedisKeyConstant.followers.getKey() + followUserId, dinerId);
  }


  /**
   * 共同关注列表
   *
   * @param userId
   * @param accessToken
   * @param path
   * @return
   */
  @Transactional(rollbackFor = Exception.class)
  public ResultInfo findCommonsFriends(Integer userId, String accessToken, String path) {
    // 是否选择了查看对象
    AssertUtil.isTrue(userId == null || userId < 1,
                      "请选择要查看的人");
    // 获取登录用户信息
    SignInUserInfo userInfo = loadSignInDinerInfo(accessToken);
    // 获取登录用户的关注信息
    String loginuserKey = RedisKeyConstant.following.getKey() + userInfo.getId();
    // 获取登录用户查看对象的关注信息
    String userKey = RedisKeyConstant.following.getKey() + userId;
    // 计算交集
    Set<Integer> userIds = redisTemplate.opsForSet().intersect(loginuserKey, userKey);
    // 没有
    if (userIds == null || userIds.isEmpty()) {
      return ResultInfoUtil.buildSuccess(path, new ArrayList<ShortUserInfo>());
    }
    // 调用食客服务根据 ids 查询食客信息
    ResultInfo resultInfo = restTemplate.getForObject("findByIds?access_token={accessToken}&ids={ids}",
                                                      ResultInfo.class, accessToken, StrUtil
                                                          .join(",", userIds));
    if (resultInfo.getCode() != ApiConstant.SUCCESS_CODE) {
      resultInfo.setPath(path);
      return resultInfo;
    }
    // 处理结果集
    List<LinkedHashMap> dinnerInfoMaps = (ArrayList) resultInfo.getData();
    List<ShortUserInfo> userInfos = dinnerInfoMaps.stream()
        .map(user -> BeanUtil.fillBeanWithMap(user, new ShortUserInfo(), true))
        .collect(Collectors.toList());

    return ResultInfoUtil.buildSuccess(path, userInfos);
  }

  /**
   * 获取登录用户信息
   *
   * @param accessToken
   * @return
   */
  private SignInUserInfo loadSignInDinerInfo(String accessToken) {
    // 必须登录
    AssertUtil.mustLogin(accessToken);
    String url = "user/me?access_token={accessToken}";
    ResultInfo resultInfo = restTemplate.getForObject(url, ResultInfo.class, accessToken);
    if (resultInfo.getCode() != ApiConstant.SUCCESS_CODE) {
      throw new ParameterException(resultInfo.getMessage());
    }
    SignInUserInfo dinerInfo = BeanUtil.fillBeanWithMap((LinkedHashMap) resultInfo.getData(),
                                                        new SignInUserInfo(), false);
    return dinerInfo;
  }

}
