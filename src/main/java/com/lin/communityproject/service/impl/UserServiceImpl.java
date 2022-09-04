package com.lin.communityproject.service.impl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import com.lin.communityproject.dto.GitHubUserDTO;
import com.lin.communityproject.entity.UserEntity;
import com.lin.communityproject.mapper.UserMapper;
import com.lin.communityproject.service.UserService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
/**
 *@program: CommunityProject
 *@description:
 *@author: lin han
 *@date: 2022/09/04
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    /**
     * 增加/修改用户数据到数据库中
     *
     *
     * 遇到的报错：java.lang.IllegalArgumentException: Source must not be null
     * 原因及解决：因为第一行查询数据结果为空,后面又使用工具类进行转换，就导致异常了，只需要加非空判断即可解决
     *
     * @param gitHubUserDTO
     */
    @Override
    public String save(GitHubUserDTO gitHubUserDTO) {
        //先判断数据库中是否有该条数据
        GitHubUserDTO user = getUserByGithubId(gitHubUserDTO.getId());
        UserEntity entity=new UserEntity();
        BeanUtils.copyProperties(gitHubUserDTO,entity);
        entity.setName(gitHubUserDTO.getLogin());
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String nowTime=sdf.format(new Date());
        entity.setModifiedTime(nowTime);
        String token= UUID.randomUUID().toString();
        if(!StringUtils.hasLength(gitHubUserDTO.getToken())){
            //如果传进来的dto的token有数据，表示不需要用新的token覆盖,如果是空表示需要新的token覆盖
            entity.setToken(token);
            gitHubUserDTO.setToken(token);
        }
        //增加数据
        if(user==null){
            entity.setCreateTime(nowTime);
            Integer integer = userMapper.insertUser(entity);
            return token;
        }else {//修改
            Integer integer = userMapper.updateUser(entity);
        }
        return  gitHubUserDTO.getToken();
    }
    @Override
    public GitHubUserDTO getUserByUserId(Integer userId) {
        UserEntity entity = userMapper.getUserById(userId);
        GitHubUserDTO dto=new GitHubUserDTO();
        BeanUtils.copyProperties(entity,dto);
        dto.setLogin(entity.getName());
        return dto;
    }
    @Override
    public GitHubUserDTO getUserByGithubId(String githubId) {
        UserEntity entity = userMapper.getUserByGithubId(githubId);
        if(entity==null) return null;
        else {
            GitHubUserDTO dto=new GitHubUserDTO();
            BeanUtils.copyProperties(entity,dto);
            dto.setLogin(entity.getName());
            return dto;
        }

    }

    @Override
    public List<GitHubUserDTO> getUserByToken(String token) {
        List<UserEntity> userByToken = userMapper.getUserByToken(token);
        List<GitHubUserDTO> collect = userByToken.stream().map(one -> {
            GitHubUserDTO dto = new GitHubUserDTO();
            BeanUtils.copyProperties(one, dto);
            dto.setLogin(one.getName());
            return dto;
        }).collect(Collectors.toList());
        return collect;
    }
}
