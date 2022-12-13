package com.zy.test.util;

import com.zy.test.entity.ResultInfo;
import lombok.Builder;

/**
 * @author {庄勇}
 * @since 2022/12/13 11:18
 */

public class ResultInfoUtil {
  public static ResultInfo build(String code,String message,String path,String msg){
    return  new ResultInfo(code,message,path,msg);
  }

  public  static  ResultInfo buildSuccess(String path,Object msg){
    return  new ResultInfo("200",null,path,msg);
  }
}
