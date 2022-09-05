package com.lin.communityproject.service;
import com.lin.communityproject.dto.UserDTO;

import java.util.List;

public interface UserService {
    /**
     * 保存/修改用户数据到数据库中
     * @param userDTO
     */
    String save(UserDTO userDTO);

    UserDTO getUserByUserId(Integer userId);

    UserDTO getUserByGithubId(String githubId);

    List<UserDTO> getUserByToken(String token);


}
