package com.zy.test.service;

import com.zy.test.entity.ShortUserInfo;
import com.zy.test.mapper.UserMapper;
import com.zy.test.util.AssertUtil;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author {庄勇}
 * @since 2022/12/13 13:41
 */
@Service
public class UserService {
  @Autowired
  private UserMapper usersMapper;
  /**
   * 根据 ids 查询食客信息
   *
   * @param ids 主键 id，多个以逗号分隔，逗号之间不用空格
   * @return
   */
  public List<ShortUserInfo> findByIds(String ids) {
    AssertUtil.isNotEmpty(ids);
    String[] idArr = ids.split(",");
    List<ShortUserInfo> dinerInfos = usersMapper.findByIds(idArr);
    return dinerInfos;
  }
}
