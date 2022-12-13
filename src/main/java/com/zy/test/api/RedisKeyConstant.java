package com.zy.test.api;


import com.zy.test.entity.Following;

/**
 * @author {庄勇}
 * @since 2022/12/13 13:21
 */
public class RedisKeyConstant {
  public static  Following following= new Following("following");

  public static Following followers = new Following("followings");

}