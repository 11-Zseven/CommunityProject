package com.lin.communityproject.service;
import com.lin.communityproject.dto.PageDTO;
import com.lin.communityproject.dto.QuestionDTO;

import java.util.List;
public interface QuestionService {
    void createQuestion(QuestionDTO questionDTO);

    List<QuestionDTO> getAll();

    List<QuestionDTO> getQuesByPage(Integer page, Integer size);

    Integer getCount();

    PageDTO setPagination(List<QuestionDTO> questions,Integer page,Integer size,Integer total);
}
