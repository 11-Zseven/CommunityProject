package com.lin.communityproject.mapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import com.lin.communityproject.entity.QuestionEntity;

import java.util.List;
@Mapper
public interface QuestionMapper {

    void createQuestion(QuestionEntity entity);

    @Select("select id,title,content,tag,create_time,modified_time,creator,comment_count,view_count,like_count from question")
    List<QuestionEntity> getAll();
}
