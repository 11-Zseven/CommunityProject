package com.lin.communityproject.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.lin.communityproject.dto.AccessTokenDTO;
import com.lin.communityproject.dto.PageDTO;
import com.lin.communityproject.dto.QuestionDTO;
import com.lin.communityproject.dto.UserDTO;
import com.lin.communityproject.service.QuestionService;
import com.lin.communityproject.service.UserService;
import com.lin.communityproject.utils.GithubUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
/**
 *@program: CommunityProject
 *@description:
 *@author: lin han
 *@date: 2022/09/03
 */
@Controller
public class IndexController {

    @Autowired
    private GithubUtils githubUtils;

    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.client.redirectUri}")
    private String clientRedirectUri;

    @Autowired
    private UserService userService;

    @Autowired
    private QuestionService questionService;


    /**
     * 获取到github返回的授权码code后发起post请求获取access_token，获取到用户信息
     * @param code
     * @param state
     * @return
     */
    @RequestMapping("/loginForGit")
    public String callback(@RequestParam(name = "code")String code,
            @RequestParam(name="state")String state,
            HttpServletResponse response){
        AccessTokenDTO accessTokenDTO=new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setState(state);
        accessTokenDTO.setRedirect_uri(clientRedirectUri);
        String accessToken = githubUtils.getAccessToken(accessTokenDTO);
        //根据token获取到github上的用户信息
        UserDTO gitHubUser = githubUtils.getGitHubUser(accessToken);
        if(gitHubUser!=null){//获取到的github上的用户信息
            String token = userService.save(gitHubUser);

            //将token存入cookie,作为登录验证。
            response.addCookie(new Cookie("community_user_token",token));
        }else {
            //登录失败提示

        }
        return "redirect:/index";
//        return "index";//使用模板引擎的情况下，return "thymeleafName" 只是会使用该页面渲染浏览器页面，不会改变浏览器地址栏内容。要想改变，可以使用重定向。
    }

    /**
     * 访问主页面，分页显示帖子(注意设置默认值)
     */
    @RequestMapping(value = {"/","/index"})
    public String toIndex(HttpServletRequest request,Model model,
                @RequestParam(value = "page",defaultValue = "1") Integer page,
                @RequestParam(value = "size",defaultValue = "1") Integer size){
        //在访问主页的时候将所有的帖子查出来
//        List<QuestionDTO> questions = questionService.getAll();
        //因为需要分页显示，因此需要根据传入的分页数据显示
        if(page<1) page=1;
        Integer total = questionService.getCount();
        if(page>total) page=total;
        List<QuestionDTO> questions=questionService.getQuesByPage(page,size);
        PageDTO pageDTO = questionService.setPagination(questions, page, size, total);
        model.addAttribute("pagination",pageDTO);
        return "index";
    }


    @RequestMapping("/logout")
    public String logout(HttpServletRequest request,HttpServletResponse response){
        //退出登录：删除服务器端的session和浏览器的cookie
        request.getSession().removeAttribute("user");
        Cookie cookie=new Cookie("community_user_token",null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";
    }

}
