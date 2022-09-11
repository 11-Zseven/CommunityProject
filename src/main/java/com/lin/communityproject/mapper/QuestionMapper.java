package com.lin.communityproject.mapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import com.lin.communityproject.entity.QuestionEntity;

import java.util.List;
@Mapper
public interface QuestionMapper {

    void createQuestion(QuestionEntity entity);

    @Select("select id,title,content,tag,create_time,modified_time,creator,comment_count,view_count,like_count from question")
    List<QuestionEntity> getAll();

    @Select("select id,title,content,tag,create_time,modified_time,creator,comment_count,view_count,like_count from question limit #{offset},#{size} order by id desc")
    List<QuestionEntity> getQuesByPage(@Param("offset") Integer offset, @Param("size") Integer size);

    @Select("select count(*) from question")
    Integer getCount();

    @Select("select count(*) from question where creator=#{userId}")
    Integer getCountByUserId(@Param("userId") Integer userId);

    @Select("select id,title,content,tag,create_time,modified_time,creator,comment_count,view_count,like_count from question where creator=#{userId} limit #{offset},#{size}")
    List<QuestionEntity> getQuesByUserId(@Param("userId") Integer userId,@Param("offset") Integer offset,@Param("size") Integer size);

    @Select("select id,title,content,tag,create_time,modified_time,creator,comment_count,view_count,like_count from question where id=#{id}")
    QuestionEntity getQuesById(@Param("id") Integer id);

    Integer updateQues(QuestionEntity entity);

    Integer incrViewCount(Integer id);

    Integer incrCommentCount(Integer id);
}

