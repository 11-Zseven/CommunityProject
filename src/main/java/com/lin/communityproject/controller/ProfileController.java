package com.lin.communityproject.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.lin.communityproject.dto.PageDTO;
import com.lin.communityproject.dto.UserDTO;
import com.lin.communityproject.service.QuestionService;
import com.lin.communityproject.service.UserService;

import javax.servlet.http.HttpServletRequest;
/**
 *@program: CommunityProject
 *@description: 我的主页/个人资料
 *@author: lin han
 *@date: 2022/09/05
 */
@Controller
public class ProfileController{

    @Autowired
    private UserService userService;

    @Autowired
    private QuestionService questionService;

    @RequestMapping("/profile/{action}/{page}")
    public String profile(@PathVariable("action") String action,
            Model model,
            HttpServletRequest request,
            @PathVariable("page") Integer page,
            @RequestParam(value = "size",defaultValue = "1") Integer size
    ){
        if("question".equals(action)){
            model.addAttribute("profileTitle","我发布的问题");

        }else if("replies".equals(action)){
            model.addAttribute("profileTitle","最新回复");
        }
        UserDTO user = (UserDTO) request.getSession().getAttribute("user");
        PageDTO myQuestion = questionService.getMyQuestion(user.getUserId(),page,size);
        model.addAttribute("proQuestion",myQuestion);
        return "/profile";
    }





}
