package com.lin.communityproject.mapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import com.lin.communityproject.entity.CommentEntity;
/**
 *@program: CommunityProject
 *@description:
 *@author: lin han
 *@date: 2022/09/06
 */
@Mapper
public interface CommentMapper {
    void createComment(CommentEntity entity);

    @Select("select id,parent_Id,`type`,create_time,modified_time,comment,commenter,like_count from comment where id=#{id}")
    CommentEntity getCommentById(Integer id);

    Integer updateCommentById(CommentEntity entity);
}
