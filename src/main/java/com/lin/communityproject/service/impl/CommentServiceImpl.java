package com.lin.communityproject.service.impl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lin.communityproject.dto.CommentDTO;
import com.lin.communityproject.entity.CommentEntity;
import com.lin.communityproject.entity.QuestionEntity;
import com.lin.communityproject.enums.CommentType;
import com.lin.communityproject.exception.CustomizeErrorCode;
import com.lin.communityproject.exception.CustomizeException;
import com.lin.communityproject.mapper.CommentMapper;
import com.lin.communityproject.mapper.QuestionMapper;
import com.lin.communityproject.service.CommentService;

import java.text.SimpleDateFormat;
import java.util.Date;
/**
 *@program: CommunityProject
 *@description:
 *@author: lin han
 *@date: 2022/09/06
 */
@Service
public class CommentServiceImpl implements CommentService {


    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Override
    public void saveComment(CommentDTO commentDTO) {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String nowTime = sdf.format(new Date());
        if(commentDTO.getId()!=null){//更新
            //likeCount comment modifiedTime
            CommentEntity entity=commentMapper.getCommentById(commentDTO.getId());
            entity.setModifiedTime(nowTime);
            entity.setComment(commentDTO.getComment());
            entity.setLikeCount(commentDTO.getLikeCount());
            commentMapper.updateCommentById(entity);
        }else {//创建
            commentDTO.setCreateTime(nowTime);
            commentDTO.setModifiedTime(nowTime);
            commentDTO.setType(2);//表示是2级问题
            CommentEntity entity=new CommentEntity();
            BeanUtils.copyProperties(commentDTO,entity);
            commentMapper.createComment(entity);
            if(commentDTO.getType().equals(CommentType.Question_TYPE)){
                //如果回复的是问题的话，问题的评论数+1(其实回复某问题的某个评论，那么这个问题的评论数是不是也应该＋1？？==>层层找出问题然后+1?不如直接写个questionId属性？ )
                questionMapper.incrCommentCount(commentDTO.getParentId());
            }
        }
    }

    @Override
    public void judgeBeforeSave(CommentDTO commentDTO) {
        if(commentDTO.getParentId()==null || commentDTO.getParentId()==0){
            throw new CustomizeException(CustomizeErrorCode.Target_Not_Fount);
        }
        if(!CommentType.exist(commentDTO.getType())){
            throw new CustomizeException(CustomizeErrorCode.Comment_Type_Wrong);
        }
        //查询数据库看要回复的评论/问题是否存在，不存在抛异常
        if(commentDTO.getType().equals(CommentType.Question_TYPE)){
            //回复的是问题
            QuestionEntity quesById = questionMapper.getQuesById(commentDTO.getParentId());
            if(quesById==null) throw new CustomizeException(CustomizeErrorCode.Question_Not_Found);
        }else if(commentDTO.getType().equals(CommentType.COMMENT_TYPE)){
            //回复的是评论
            CommentEntity commentById = commentMapper.getCommentById(commentDTO.getParentId());
            if(commentById==null) throw new CustomizeException(CustomizeErrorCode.Comment_Not_Fount);
        }
    }
}
