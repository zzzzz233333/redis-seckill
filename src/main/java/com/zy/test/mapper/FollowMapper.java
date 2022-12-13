package com.zy.test.mapper;

import com.zy.test.entity.Follow;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

/**
 * @author {庄勇}
 * @since 2022/12/13 10:55
 */
@Component
public interface FollowMapper {
/**
*查询关注信息
* @paramuserId
followUserIaQparam
@return
 */
@Select("select id,user_id,follow_user_id,is_valid from t_follow " +
"where user_id = #{userId} and follow_user_id = #{followUserId}")
Follow selectFollow(@Param("userId") Integer userId, @Param("followUserId") Integer followUserId);
/**
* 添加关注信息
* @param userId
  @param followUserId
  @return
 */
@Insert("insert into t_follow (user_id,follow_user_id,is_valid,create_date,update_date)" + " values(#{userId}，#{followUserId}，1，now()，now())")
    int save(@Param("userId") Integer userId, @Param("followUserId") Integer followUserId);
/**
* 修改关注信息
* @param
*@param isFollowed
@return
 */
@Update("update t follow set is valid = #{isFollowed}, update date = now() where id = #{id}")
int update(@Param("id") Integer id, @Param("isFollowed") int isFollowed);

}
