package com.lin.communityproject.dto;
import lombok.Data;
/**
 *@program: CommunityProject
 *@description:
 *@author: lin han
 *@date: 2022/09/03
 */
@Data
public class UserDTO {
    //对应库中githubId
    private String id;
    //主键  user_id
    private Integer userId;
    //对应库中name
    private String login;//name
    private String avatarUrl;
    private String bio;
    private String token;
    private String createTime;
    private String modifiedTime;
    private String description;

}
