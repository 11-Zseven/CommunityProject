package com.lin.communityproject.controller;
/**
 *@program: CommunityProject
 *@description: 评论回复相关
 *@author: lin han
 *@date: 2022/09/07
 */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.fastjson.JSON;
import com.lin.communityproject.dto.CommentDTO;
import com.lin.communityproject.dto.ResultCodeDTO;
import com.lin.communityproject.dto.UserDTO;
import com.lin.communityproject.enums.CommentType;
import com.lin.communityproject.exception.CustomizeErrorCode;
import com.lin.communityproject.exception.CustomizeException;
import com.lin.communityproject.service.CommentService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
/**
 * 注意：要返回json格式的数据需要加上@ResponsBody
 */
@RestController
public class CommentController {

    @Autowired
    private CommentService commentService;
    //返回值为什么要用Object?
    @PostMapping("/comment")
    public Object comment(@RequestBody CommentDTO commentDTO, HttpServletRequest request, Model model){
        UserDTO user = (UserDTO) request.getSession().getAttribute("user");
        if(user==null){
            return ResultCodeDTO.resultOf(CustomizeErrorCode.UnlessLogin);
        }
        try {
            commentService.judgeBeforeSave(commentDTO);
        }catch (CustomizeException e){
            return ResultCodeDTO.resultOf(e);
        }
        commentDTO.setCommenter(user.getUserId());
        commentService.saveComment(commentDTO);
        Object o = JSON.toJSON(commentDTO);
        return ResultCodeDTO.resultOf(200,o.toString());
    }

    /**
     * 显示二级评论
     * @param id     一级评论id
     * @param model
     * @return
     */
    @RequestMapping(value = "/comment/{id}",method = RequestMethod.GET)
    public Object subComment(@PathVariable("id")Integer id,Model model){
        List<CommentDTO> subComm = commentService.getCommentsQues(id, CommentType.COMMENT_TYPE);
        model.addAttribute("subComm",subComm);
        return null;
    }
}
