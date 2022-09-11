package com.lin.communityproject.controller;
/**
 *@program: CommunityProject
 *@description: 评论回复相关
 *@author: lin han
 *@date: 2022/09/07
 */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
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
     * 获取到二级评论内容
     * @param
     * @return
     */
    @GetMapping("/comment")
    public ResultCodeDTO getSubComm(@RequestParam("parentId")Integer parentId,@RequestParam("questionId")Integer questionId){
        if(parentId == null){
            return ResultCodeDTO.resultOf(CustomizeErrorCode.Comment_Not_Fount);
        }
        //id:一级评论的id
        List<CommentDTO> comments = commentService.getComments(parentId, CommentType.COMMENT_TYPE);
        return ResultCodeDTO.resultOf(comments);
    }


    @GetMapping("incrCommLike")
    public ResultCodeDTO incrCommLike(@RequestParam Integer id)
    {
        if(id==null){
            return  ResultCodeDTO.resultOf(CustomizeErrorCode.Comment_Not_Fount);
        }
        commentService.incrCommLike(id);
        CommentDTO dto1=commentService.getCommentById(id);
        return ResultCodeDTO.resultOf(dto1);
    }
}
