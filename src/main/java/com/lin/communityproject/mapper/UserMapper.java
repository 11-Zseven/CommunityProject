package com.lin.communityproject.mapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import com.lin.communityproject.entity.UserEntity;

import java.util.List;
@Mapper
public interface UserMapper {
    //简单的语句使用注解，稍微复杂的就使用xml，结合使用。
    @Select("select id,name,avatar_ur,,token,create_time,modified_time,description from user where user_id=#{id}")
    UserEntity getUserById(Integer id);

    @Select("select id,name,avatar_url,token,create_time,modified_time,description from user where id=#{githubId}")
    UserEntity getUserByGithubId(String githubId);

    Integer insertUser(UserEntity user);

    Integer updateUser(UserEntity user);

    @Select("select user_id,id,name,avatar_url,token,create_time,modified_time,description from user where token=#{token}")
    List<UserEntity> getUserByToken(String token);
}
