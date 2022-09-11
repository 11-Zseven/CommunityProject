package com.lin.communityproject;

import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.test.context.SpringBootTest;

import com.lin.communityproject.dto.CommentDTO;
import com.lin.communityproject.entity.CommentEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@SpringBootTest
class CommunityProjectApplicationTests {

    @Test
    void contextLoads() {
        List<CommentDTO> dto=new ArrayList<>();
        CommentDTO a=new CommentDTO();
        a.setCommenter(1);
        a.setId(1);
        CommentDTO b=new CommentDTO();
        b.setCommenter(1);
        b.setId(1);
        CommentDTO c=new CommentDTO();
        c.setCommenter(1);
        c.setId(1);
        CommentDTO d=new CommentDTO();
        d.setCommenter(1);
        d.setId(1);
        dto.add(a);
        dto.add(b);
        dto.add(c);
        dto.add(d);
        Set<CommentEntity> collect = dto.stream().map(one -> {
            CommentEntity entity = new CommentEntity();
            BeanUtils.copyProperties(one, entity);
            return entity;
        }).collect(Collectors.toSet());
        System.out.println(collect.size());//4 无法去重。HashSet 根据hashcode
    }

}
