package com.zy.test.util;

import com.zy.test.entity.SignInUserInfo;
import java.util.LinkedHashMap;

/**
 * @author {庄勇}
 * @since 2022/12/13 13:12
 */
public class BeanUtil {
  public static SignInUserInfo fillBeanWithMap(LinkedHashMap data,SignInUserInfo signInUserInfo,boolean flag) {
    return new SignInUserInfo();
  }
}
