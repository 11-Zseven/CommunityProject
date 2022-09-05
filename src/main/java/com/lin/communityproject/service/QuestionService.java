package com.lin.communityproject.service;
import com.lin.communityproject.dto.QuestionDTO;

import java.util.List;
public interface QuestionService {
    void createQuestion(QuestionDTO questionDTO);

    List<QuestionDTO> getAll();
}
