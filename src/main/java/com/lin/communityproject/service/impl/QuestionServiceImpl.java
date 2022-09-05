package com.lin.communityproject.service.impl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lin.communityproject.dto.QuestionDTO;
import com.lin.communityproject.dto.UserDTO;
import com.lin.communityproject.entity.QuestionEntity;
import com.lin.communityproject.entity.UserEntity;
import com.lin.communityproject.mapper.QuestionMapper;
import com.lin.communityproject.mapper.UserMapper;
import com.lin.communityproject.service.QuestionService;

import java.util.List;
import java.util.stream.Collectors;
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

    @Autowired
    private UserMapper userMapper;

    @Override
    public void createQuestion(QuestionDTO questionDTO) {
        QuestionEntity entity=new QuestionEntity();
        BeanUtils.copyProperties(questionDTO,entity);
        questionMapper.createQuestion(entity);
    }

    @Override
    public List<QuestionDTO> getAll() {
        List<QuestionEntity> entities=questionMapper.getAll();
        List<QuestionDTO> collect = entities.stream().map(one -> {
            QuestionDTO dto = new QuestionDTO();
            BeanUtils.copyProperties(one, dto);
            UserDTO userDTO = new UserDTO();
            UserEntity userById = userMapper.getUserById(one.getCreator());
            BeanUtils.copyProperties(userById, userDTO);
            userDTO.setLogin(userById.getName());
            dto.setUser(userDTO);
            return dto;
        }).collect(Collectors.toList());
        return collect;
    }
}
