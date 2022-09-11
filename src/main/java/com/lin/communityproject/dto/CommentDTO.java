package com.lin.communityproject.dto;
/**
 *@program: CommunityProject
 *@description:
 *@author: lin han
 *@date: 2022/09/07
 */
public class CommentDTO {
    private Integer id;
    private Integer parentId;//==questionId/commentId 对某个问题的回复或者是对某个评论的回复
    private Integer type;
    private String createTime;
    private String modifiedTime;
    private Integer commenter;
    private String comment;
    private Integer likeCount;
    private UserDTO commenterDetail;
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getParentId() {
        return parentId;
    }
    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }
    public Integer getType() {
        return type;
    }
    public void setType(Integer type) {
        this.type = type;
    }
    public String getCreateTime() {
        return createTime;
    }
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
    public String getModifiedTime() {
        return modifiedTime;
    }
    public void setModifiedTime(String modifiedTime) {
        this.modifiedTime = modifiedTime;
    }
    public Integer getCommenter() {
        return commenter;
    }
    public void setCommenter(Integer commenter) {
        this.commenter = commenter;
    }
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
    public Integer getLikeCount() {
        return likeCount;
    }
    public void setLikeCount(Integer likeCount) {
        if(likeCount==null) likeCount=0;
        else this.likeCount = likeCount;
    }

    public UserDTO getCommenterDetail() {
        return commenterDetail;
    }
    public void setCommenterDetail(UserDTO commenterDetail) {
        this.commenterDetail = commenterDetail;
    }
}
