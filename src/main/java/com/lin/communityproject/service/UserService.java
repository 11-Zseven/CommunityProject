package com.lin.communityproject.service;
import com.lin.communityproject.dto.GitHubUserDTO;

import java.util.List;

public interface UserService {
    /**
     * 保存/修改用户数据到数据库中
     * @param gitHubUserDTO
     */
    String save(GitHubUserDTO gitHubUserDTO);

    GitHubUserDTO getUserByUserId(Integer userId);

    GitHubUserDTO getUserByGithubId(String githubId);

    List<GitHubUserDTO> getUserByToken(String token);


}
