package com.lin.communityproject.service;
import com.lin.communityproject.dto.PageDTO;
import com.lin.communityproject.dto.QuestionDTO;

import java.util.List;
public interface QuestionService {
    void saveQuestion(QuestionDTO questionDTO);

    List<QuestionDTO> getAll();

    List<QuestionDTO> getQuesByPage(Integer page, Integer size);

    Integer getCount();

    Integer getCountByUserId(Integer userId);

    PageDTO setPagination(List<QuestionDTO> questions,Integer page,Integer size,Integer total);

    PageDTO getMyQuestion(Integer userId,Integer page, Integer size);

    QuestionDTO getQuesById(Integer id);

    void incrCommentCount(Integer id);
}
