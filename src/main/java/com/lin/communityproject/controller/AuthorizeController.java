package com.lin.communityproject.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.lin.communityproject.dto.AccessTokenDTO;
import com.lin.communityproject.dto.GitHubUserDTO;
import com.lin.communityproject.service.UserService;
import com.lin.communityproject.utils.GithubUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
/**
 *@program: CommunityProject
 *@description:
 *@author: lin han
 *@date: 2022/09/03
 */
@Controller
public class AuthorizeController {

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


    /**
     * 获取到github返回的授权码code后发起post请求获取access_token，获取到用户信息
     * @param code
     * @param state
     * @return
     */
    @RequestMapping("/loginForGit")
    public String callback(@RequestParam(name = "code")String code,
            @RequestParam(name="state")String state,
            HttpServletRequest request,
            HttpServletResponse response){
        AccessTokenDTO accessTokenDTO=new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setState(state);
        accessTokenDTO.setRedirect_uri(clientRedirectUri);
        String accessToken = githubUtils.getAccessToken(accessTokenDTO);
//        System.out.println(accessToken);//ghu_xXyfWBaYjwPaS0meQ1RcUuUE7jzxLE4XrcIs
        //根据token获取到github上的用户信息
        GitHubUserDTO gitHubUser = githubUtils.getGitHubUser(accessToken);
        if(gitHubUser!=null){//获取到的github上的用户信息
            String token = userService.save(gitHubUser);
            //将token存入cookie,作为登录验证。
            response.addCookie(new Cookie("community_user_token",token));
//            request.getSession().setAttribute("user",gitHubUser);
        }else {
            //登录失败提示
        }
        return "redirect:/index";
//        return "index";//使用模板引擎的情况下，return "thymeleafName" 只是会使用该页面渲染浏览器页面，不会改变浏览器地址栏内容。要想改变，可以使用重定向。
    }

    @RequestMapping(value = {"/","/index"})
    public String toIndex(HttpServletRequest request){
        //访问主页面，先判断cookie中是否有token值，并且判断和数据库中的是否一致，验证通过的话表示之前已经登陆过，无需重复登录
        List<Cookie> cookies = Arrays.asList(request.getCookies());
        List<Cookie> tokenList = cookies.stream().filter(one ->one.getName().equals("community_user_token")).collect(Collectors.toList());
        if(tokenList.size()!=0){
            String token = tokenList.get(0).getValue();
            List<GitHubUserDTO> userByToken = userService.getUserByToken(token);
            if(userByToken.size()!=0){
                request.getSession().setAttribute("user",userByToken.get(0));
            }
        }
        return "index";
    }


}
