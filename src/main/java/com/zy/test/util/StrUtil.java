package com.zy.test.util;

import io.netty.util.internal.StringUtil;
import java.util.Collection;
import java.util.List;

/**
 * @author {庄勇}
 * @since 2022/12/13 16:12
 */
public class StrUtil {
  public static CharSequence  join(String character, Collection collection){
    return StringUtil.join(",", collection);
  }
}
