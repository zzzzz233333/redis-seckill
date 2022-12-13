package com.zy.test.entity;

import java.util.Date;
import lombok.Data;

/**
 * @author {庄勇}
 * @since 2022/12/13 10:57
 */
@Data
public class Follow {
  private int id;
  private int followUserId;
  private int userId;
  private int isValid;
  private Date createDate;
  private Date updateDate;

}
