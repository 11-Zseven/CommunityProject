package com.lin.communityproject.service.impl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lin.communityproject.dto.PageDTO;
import com.lin.communityproject.dto.QuestionDTO;
import com.lin.communityproject.dto.UserDTO;
import com.lin.communityproject.entity.QuestionEntity;
import com.lin.communityproject.entity.UserEntity;
import com.lin.communityproject.exception.CustomizeErrorCode;
import com.lin.communityproject.exception.CustomizeException;
import com.lin.communityproject.mapper.QuestionMapper;
import com.lin.communityproject.mapper.UserMapper;
import com.lin.communityproject.service.QuestionService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
/**
 *@program: CommunityProject
 *@description:
 *@author: lin han
 *@date: 2022/09/04
 */
@Service
public class QuestionServiceImpl implements QuestionService {
    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public void saveQuestion(QuestionDTO questionDTO) {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String nowTime = sdf.format(new Date());

        if(questionDTO.getId()!=null){//update
            QuestionEntity quesById = questionMapper.getQuesById(questionDTO.getId());
            quesById.setTag(questionDTO.getTag());
            quesById.setContent(questionDTO.getContent());
            quesById.setTitle(questionDTO.getTitle());
            quesById.setModifiedTime(nowTime);
            questionMapper.updateQues(quesById);
        }else {//create
            QuestionEntity entity=new QuestionEntity();
            BeanUtils.copyProperties(questionDTO,entity);
            entity.setCommentCount(0);
            entity.setViewCount(0);
            entity.setLikeCount(0);
            entity.setModifiedTime(nowTime);
            entity.setCreateTime(nowTime);
            questionMapper.createQuestion(entity);
        }


    }

    @Override
    public List<QuestionDTO> getAll() {
        List<QuestionEntity> entities=questionMapper.getAll();
        List<QuestionDTO> collect = entities.stream().map(one -> {
            QuestionDTO dto = new QuestionDTO();
            BeanUtils.copyProperties(one, dto);
            UserDTO userDTO = new UserDTO();
            UserEntity userById = userMapper.getUserById(one.getCreator());
            BeanUtils.copyProperties(userById, userDTO);
            userDTO.setLogin(userById.getName());
            dto.setUser(userDTO);
            return dto;
        }).collect(Collectors.toList());
        return collect;
    }

    @Override
    public List<QuestionDTO> getQuesByPage(Integer page, Integer size) {
        Integer offset=(page-1)*size;
        List<QuestionEntity> quesByPage = questionMapper.getQuesByPage(offset, size);
        List<QuestionDTO> collect = quesByPage.stream().map(one -> {
            QuestionDTO dto = new QuestionDTO();
            BeanUtils.copyProperties(one, dto);
            UserDTO userDTO = new UserDTO();
            UserEntity userById = userMapper.getUserById(one.getCreator());
            BeanUtils.copyProperties(userById, userDTO);
            userDTO.setLogin(userById.getName());
            dto.setUser(userDTO);
            return dto;
        }).collect(Collectors.toList());

        return collect;
    }

    //查询question表总记录数
    @Override
    public Integer getCount() {
        return questionMapper.getCount();
    }

    @Override
    public Integer getCountByUserId(Integer userId){
        return questionMapper.getCountByUserId(userId);
    }

    //设置分页
    @Override
    public PageDTO setPagination(List<QuestionDTO> questions, Integer page, Integer size, Integer total) {
        PageDTO dto=new PageDTO();
        dto.setQuestions(questions);
        dto.setPagination(page,total,size);
        return dto;
    }

    @Override
    public PageDTO getMyQuestion(Integer userId,Integer page, Integer size) {
        Integer total=getCountByUserId(userId);
        List<QuestionEntity> entities=questionMapper.getQuesByUserId(userId,(page-1)*size,size);
        List<QuestionDTO> myQues = entities.stream().map(one -> {
            QuestionDTO dto = new QuestionDTO();
            BeanUtils.copyProperties(one, dto);
            UserDTO userDTO = new UserDTO();
            UserEntity userById = userMapper.getUserById(one.getCreator());
            BeanUtils.copyProperties(userById, userDTO);
            userDTO.setLogin(userById.getName());
            dto.setUser(userDTO);
            return dto;
        }).collect(Collectors.toList());
        PageDTO pageDTO = setPagination(myQues, page, size, total);
        return pageDTO;
    }

    @Override
    public QuestionDTO getQuesById(Integer id) {
        QuestionEntity entity = questionMapper.getQuesById(id);
        if(entity==null) throw new CustomizeException(CustomizeErrorCode.Question_Not_Found);//这里就可以直接使用枚举类型中的异常
        //每一次访问都会让阅读数加+（注意高并发情况：使用乐观锁，这里没有用到）
        Integer incrView=questionMapper.incrViewCount(id);
        QuestionDTO dto=new QuestionDTO();
        BeanUtils.copyProperties(entity,dto);
        UserEntity userById = userMapper.getUserById(entity.getCreator());
        UserDTO userDTO=new UserDTO();
        BeanUtils.copyProperties(userById,userDTO);
        userDTO.setLogin(userById.getName());
        dto.setUser(userDTO);
        return dto;
    }

    @Override
    public void incrCommentCount(Integer id) {
        questionMapper.incrCommentCount(id);
    }
}
