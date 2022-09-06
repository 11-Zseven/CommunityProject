package com.lin.communityproject.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.lin.communityproject.dto.QuestionDTO;
import com.lin.communityproject.dto.UserDTO;
import com.lin.communityproject.service.QuestionService;

import javax.servlet.http.HttpServletRequest;
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
    public String toPublish(Model model){
        QuestionDTO questionDTO=new QuestionDTO();
        model.addAttribute("question",questionDTO);
        return "publish";
    }

    /**
     * 这里有个问题，不能使用@Requestbody注解，因为：项目中出现@RequestBody注解无效的情况后，首先检查了前端是否设置了正确的'Content-Type': 'application/json'
     * 而浏览器默认的form表单请求(不设置的情况下)，content-type是：application/xx...忘了
     *
     * 本方法：发布和更新
     */
    @PostMapping(value = "/publish")
    public String publish( QuestionDTO questionDTO,
            HttpServletRequest request,
            Model model){

        model.addAttribute("question",questionDTO);
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

            if(questionDTO.getId()==null){
                UserDTO user = (UserDTO) request.getSession().getAttribute("user");
                questionDTO.setCreator(user.getUserId());
            }
            questionService.saveQuestion(questionDTO);
            return "redirect:/";
        }else {
            return "publish";
        }

    }

    @RequestMapping("/publish/{id}")
    public String editQues(@PathVariable("id")Integer id,Model model){
        QuestionDTO quesById = questionService.getQuesById(id);
        model.addAttribute("question",quesById);
        return "publish";
    }
}
