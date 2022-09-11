package com.lin.communityproject.service;
import com.lin.communityproject.dto.CommentDTO;
import com.lin.communityproject.enums.CommentType;

import java.util.List;
public interface CommentService {

    void judgeBeforeSave(CommentDTO commentDTO);

    void saveComment(CommentDTO commentDTO);

    List<CommentDTO> getComments(Integer parentId, CommentType type);

    void incrCommLike(Integer cid);

    CommentDTO getCommentById(Integer id);
}
