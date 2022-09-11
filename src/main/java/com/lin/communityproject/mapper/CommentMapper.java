package com.lin.communityproject.mapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import com.lin.communityproject.entity.CommentEntity;

import java.util.List;
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
    CommentEntity getCommentById(@Param("id") Integer id);

    Integer updateCommentById(CommentEntity entity);

    @Select("select id,parent_Id,`type`,create_time,modified_time,comment,commenter,like_count from comment where parent_id=#{parentId} and type = #{type} order by create_time desc")
    List<CommentEntity> getCommentsQues(@Param("parentId") Integer parentId,@Param("type") Integer type);

    List<CommentEntity> getCommentsComm(Integer parentId);
}
