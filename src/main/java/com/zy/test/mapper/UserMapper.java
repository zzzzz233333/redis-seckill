package com.zy.test.mapper;

import com.zy.test.entity.ShortUserInfo;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

/**
 * @author {庄勇}
 * @since 2022/12/13 14:02
 */
@Component
public interface UserMapper {
  /**
   * 根据 ID 集合查询多个食客信息
   * @param ids
   * @return
   */
  @Select("<script> " +
      " select id, nickname, avatar_url from t_diners " +
      " where is_valid = 1 and id in " +
      " <foreach item=\"id\" collection=\"ids\" open=\"(\" separator=\",\" close=\")\"> " +
      "   #{id} " +
      " </foreach> " +
      " </script>")
  List<ShortUserInfo> findByIds(@Param("ids") String[] ids);
}
