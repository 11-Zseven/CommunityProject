package com.lin.communityproject.controller;
/**
 *@program: CommunityProject
 *@description: 评论回复相关
 *@author: lin han
 *@date: 2022/09/07
 */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.lin.communityproject.dto.CommentDTO;
import com.lin.communityproject.dto.ResultCodeDTO;
import com.lin.communityproject.dto.UserDTO;
import com.lin.communityproject.exception.CustomizeErrorCode;
import com.lin.communityproject.service.CommentService;

import javax.servlet.http.HttpServletRequest;
@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;
    //返回值为什么要用Object?
    @PostMapping("/comment")
    public Object comment(@RequestBody CommentDTO commentDTO, HttpServletRequest request){
        UserDTO user = (UserDTO) request.getSession().getAttribute("user");
        if(user==null){
            return ResultCodeDTO.errorOf(CustomizeErrorCode.UnlessLogin);
        }
        commentDTO.setCommenter(user.getUserId());
        commentService.saveComment(commentDTO);
        return null;
    }
}
