package com.lin.communityproject.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import com.lin.communityproject.dto.UserDTO;
import com.lin.communityproject.dto.QuestionDTO;
import com.lin.communityproject.service.QuestionService;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 *@program: CommunityProject
 *@description:
 *@author: lin han
 *@date: 2022/09/04
 */
@Controller
public class PublishController {

    @Autowired
    private QuestionService questionService;
    @GetMapping("/publish")
    public String toPublish(){
        return "publish";
    }

    /**
     * 这里有个问题，不能使用@Requestbody注解，因为：项目中出现@RequestBody注解无效的情况后，首先检查了前端是否设置了正确的'Content-Type': 'application/json'
     * 而浏览器默认的form表单请求(不设置的情况下)，content-type是：application/xx...忘了
     * @param questionDTO
     * @param request
     * @return
     */
    @PostMapping(value = "/publish")
    public String publish( QuestionDTO questionDTO,
            HttpServletRequest request,
            Model model){

        model.addAttribute("title",questionDTO.getTitle());
        model.addAttribute("content",questionDTO.getContent());
        model.addAttribute("tag",questionDTO.getTag());
        if(!StringUtils.hasLength(StringUtils.trimWhitespace(questionDTO.getTitle()))){//去掉前后空格
            model.addAttribute("errorTitle","问题标题不能为空!");
        }
        if(!StringUtils.hasLength(StringUtils.trimWhitespace(questionDTO.getContent()))){
            model.addAttribute("errorContent","问题内容/补充不能为空!");
        }
        if(!StringUtils.hasLength(StringUtils.trimWhitespace(questionDTO.getTag()))){
            model.addAttribute("errorTag","问题标签不能为空!");
        }
        if(StringUtils.hasLength(StringUtils.trimWhitespace(questionDTO.getTitle())) &&
                StringUtils.hasLength(StringUtils.trimWhitespace(questionDTO.getContent())) &&
                StringUtils.hasLength(StringUtils.trimWhitespace(questionDTO.getTag()))){
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String nowTime = sdf.format(new Date());
            questionDTO.setCreateTime(nowTime);
            questionDTO.setModifiedTime(nowTime);
            UserDTO user = (UserDTO) request.getSession().getAttribute("user");
            questionDTO.setCreator(user.getUserId());
            questionService.createQuestion(questionDTO);
            return "redirect:/";
        }else {
            return "publish";
        }

    }
}
