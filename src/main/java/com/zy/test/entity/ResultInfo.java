package com.zy.test.entity;


/**
 * @author {庄勇}
 * @since 2022/12/13 11:17
 */
public class ResultInfo<T> {
  private String  code;

  private String message;

  private String path;

  private T data;

  public ResultInfo(String code, String message, String path, T data) {
    this.code = code;
    this.message = message;
    this.path = path;
    this.data = data;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }


  public T getData() {
    return data;
  }

  public void setData(T data) {
    this.data = data;
  }
}
