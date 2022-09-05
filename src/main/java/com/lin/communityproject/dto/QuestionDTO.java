package com.lin.communityproject.dto;
import lombok.Data;
/**
 *@program: CommunityProject
 *@description:
 *@author: lin han
 *@date: 2022/09/04
 */
@Data
public class QuestionDTO {
    private Integer id;
    private String title;
    private String content;
    private String tag;
    private String createTime;
    private String modifiedTime;
    private Integer creator;//记录创建者的id
    private Integer commentCount;
    private Integer viewCount;
    private Integer likeCount;
    private UserDTO user;
}
