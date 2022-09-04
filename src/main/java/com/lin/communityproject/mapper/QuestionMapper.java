package com.lin.communityproject.mapper;
import org.apache.ibatis.annotations.Mapper;
import com.lin.communityproject.entity.QuestionEntity;
@Mapper
public interface QuestionMapper {

    void createQuestion(QuestionEntity entity);
}
