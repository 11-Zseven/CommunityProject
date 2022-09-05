package com.lin.communityproject.mapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import com.lin.communityproject.entity.UserEntity;

import java.util.List;
@Mapper
public interface UserMapper {
    //简单的语句使用注解，稍微复杂的就使用xml，结合使用。
    @Select("select id,name,avatar_url,token,create_time,modified_time,description from user where user_id=#{id}")
    UserEntity getUserById(@Param("id")Integer id);

    @Select("select id,name,avatar_url,token,create_time,modified_time,description from user where id=#{githubId}")
    UserEntity getUserByGithubId(@Param("githubId") String githubId);

    /*
    java 接口不使用 @Param 注解，同时 mapper 文件也可以不使用 parameterType 这个参数，
    Mybatis会 根据实体类(entity)的类型自动识别并匹配javaBean(这一部分在 spring配置文件关于数据源那一部分)
     */
    Integer insertUser(UserEntity user);

    Integer updateUser(UserEntity user);

    /*
    使用idea可以满足：即时不写@Param 也能成功，原因是
        IDEA编译时采取了强制保持方法参数变量名，但需要满足如下
        1. 必须是jdk8或以上
        2. 编译器参数-parameters
     */
    @Select("select user_id,id,name,avatar_url,token,create_time,modified_time,description from user where token=#{token}")
    List<UserEntity> getUserByToken(@Param("token")String token);
}
