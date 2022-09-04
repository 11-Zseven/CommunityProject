package com.lin.communityproject.service.impl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lin.communityproject.dto.QuestionDTO;
import com.lin.communityproject.entity.QuestionEntity;
import com.lin.communityproject.mapper.QuestionMapper;
import com.lin.communityproject.service.QuestionService;
/**
 *@program: CommunityProject
 *@description:
 *@author: lin han
 *@date: 2022/09/04
 */
@Service
public class QuestionServiceImpl implements QuestionService {
    @Autowired
    private QuestionMapper questionMapper;
    @Override
    public void createQuestion(QuestionDTO questionDTO) {
        QuestionEntity entity=new QuestionEntity();
        BeanUtils.copyProperties(questionDTO,entity);
        questionMapper.createQuestion(entity);
    }
}
