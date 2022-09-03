package com.lin.communityproject.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 *@program: CommunityProject
 *@description:
 *@author: lin han
 *@date: 2022/09/03
 */
@Controller
public class HelloController {
    @RequestMapping("/hello")
    public String hello(){
        return "hello";
    }
}
