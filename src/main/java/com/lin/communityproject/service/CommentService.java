package com.lin.communityproject.service;
import com.lin.communityproject.dto.CommentDTO;
public interface CommentService {

    void judgeBeforeSave(CommentDTO commentDTO);

    void saveComment(CommentDTO commentDTO);
}
