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

    @Select("select id,parent_Id,`type`,create_time,modified_time,comment,commenter,like_count,comment_count from comment where parent_id=#{parentId} and type = #{type} order by create_time desc")
    List<CommentEntity> getComments(@Param("parentId") Integer parentId,@Param("type") Integer type);

    Integer incrCommentCount(@Param("id") Integer parentId);

    Integer incrCommLike(@Param("id") Integer cid,@Param("modifiedTime") String modifiedTime);
}
