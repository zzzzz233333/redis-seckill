package com.zy.test.api;

/**
 * @author {庄勇}
 * @since 2022/12/13 13:09
 */
public class ParameterException extends  RuntimeException{

  public ParameterException(String message){
    super(message,null);
  }
}
