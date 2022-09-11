package com.lin.communityproject.service.impl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import com.lin.communityproject.dto.CommentDTO;
import com.lin.communityproject.dto.UserDTO;
import com.lin.communityproject.entity.CommentEntity;
import com.lin.communityproject.entity.QuestionEntity;
import com.lin.communityproject.entity.UserEntity;
import com.lin.communityproject.enums.CommentType;
import com.lin.communityproject.exception.CustomizeErrorCode;
import com.lin.communityproject.exception.CustomizeException;
import com.lin.communityproject.mapper.CommentMapper;
import com.lin.communityproject.mapper.QuestionMapper;
import com.lin.communityproject.mapper.UserMapper;
import com.lin.communityproject.service.CommentService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
/**
 *@program: CommunityProject
 *@description:
 *@author: lin han
 *@date: 2022/09/06
 */
@Service
public class CommentServiceImpl implements CommentService {


    @Autowired
    private UserMapper userMapper;

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
            if(StringUtils.hasLength(commentDTO.getComment()) && !commentDTO.getComment().equals(entity.getComment()))
                entity.setComment(commentDTO.getComment());
            if( commentDTO.getLikeCount()!=entity.getLikeCount())
                entity.setLikeCount(commentDTO.getLikeCount()+entity.getLikeCount());
            if(commentDTO.getCommentCount()!=entity.getCommentCount())
                entity.setCommentCount(commentDTO.getCommentCount()+entity.getCommentCount());
            commentMapper.updateCommentById(entity);
        }else {//创建 type的值由前端传 减少和数据库的交互
            commentDTO.setCreateTime(nowTime);
            commentDTO.setModifiedTime(nowTime);
            commentDTO.setCommentCount(0);
            commentDTO.setLikeCount(0);
            CommentEntity entity=new CommentEntity();
            BeanUtils.copyProperties(commentDTO,entity);
            commentMapper.createComment(entity);
            if(commentDTO.getType().equals(CommentType.Question_TYPE.getType())){
                //目前一级评论数是问题的评论数，二级评论数是一级的评论数，不会统计到问题中。(统计的话，要么改结构加个questionId,要么当type=2的时候再获取这个评论的questionId,再incr(前提是只有二级评论不允许评论套娃))
                //如果回复的是问题的话，问题的评论数+1(其实回复某问题的某个评论，那么这个问题的评论数是不是也应该＋1？？==>层层找出问题然后+1?不如直接写个questionId属性？ )
                questionMapper.incrCommentCount(commentDTO.getParentId());
            }else if(commentDTO.getType().equals(CommentType.COMMENT_TYPE.getType())){
                commentMapper.incrCommentCount(commentDTO.getParentId());
            }
        }
    }

    @Override
    public void judgeBeforeSave(CommentDTO commentDTO) {
        //没有输入内容却点击回复
        if(commentDTO == null || !StringUtils.hasLength(commentDTO.getComment())){
//            throw ResultCodeDTO.resultOf(CustomizeErrorCode.No_Comment_Content);
            throw new CustomizeException(CustomizeErrorCode.No_Comment_Content);
        }
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

    /**
     * 获取问题/一级评论的评论
     * @param parentId 问题/一级评论Id
     * @param type 1：问题 2：一级评论
     * @return
     */
    @Override
    public List<CommentDTO> getComments(Integer parentId,CommentType type){
        List<CommentEntity> commentQuestion = commentMapper.getComments(parentId,type.getType());
        if(commentQuestion == null || commentQuestion.size()==0){
            return new ArrayList<>();
        }
        //commenterDetail distinct()方法主要是根据hashcode和equals方法进去去重的，所以需要重写hashCode、equals才可以使用distinct去重
        List<Integer> commenters = commentQuestion.stream().map(one -> one.getCommenter()).distinct().collect(Collectors.toList());
        List<UserEntity> entities= userMapper.getUsersInIds(commenters);
        List<UserDTO> userList = entities.stream().map(one -> {
            UserDTO dto = new UserDTO();
            BeanUtils.copyProperties(one, dto);
            dto.setLogin(one.getName());
            return dto;
        }).collect(Collectors.toList());

        Map<Integer, UserDTO> userMap = userList.stream().collect(Collectors.toMap(user -> user.getUserId(), user -> user));
        List<CommentDTO> collect = commentQuestion.stream().map(one -> {
            CommentDTO dto = new CommentDTO();
            BeanUtils.copyProperties(one, dto);
            dto.setCommenterDetail(userMap.get(dto.getCommenter()));
            return dto;
        }).collect(Collectors.toList());
        return collect;
    }

    @Override
    public void incrCommLike(Integer cid) {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String nowTime = sdf.format(new Date());
        commentMapper.incrCommLike(cid,nowTime);
    }

    @Override
    public CommentDTO getCommentById(Integer id) {
        CommentEntity commentById = commentMapper.getCommentById(id);
        CommentDTO dto=new CommentDTO();
        BeanUtils.copyProperties(commentById,dto);
        UserEntity userById = userMapper.getUserById(dto.getCommenter());
        UserDTO dto1=new UserDTO();
        BeanUtils.copyProperties(userById,dto1);
        dto1.setLogin(userById.getName());
        dto.setCommenterDetail(dto1);
        return dto;
    }
}
