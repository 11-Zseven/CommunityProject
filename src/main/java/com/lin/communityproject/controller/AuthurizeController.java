package com.lin.communityproject.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.alibaba.fastjson.JSON;
import com.lin.communityproject.dto.AccessTokenDTO;
import com.lin.communityproject.dto.GitHubUserDTO;
import com.lin.communityproject.utils.GithubUtils;
/**
 *@program: CommunityProject
 *@description:
 *@author: lin han
 *@date: 2022/09/03
 */
@Controller
public class AuthurizeController {

    @Autowired
    private GithubUtils githubUtils;

    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.client.redirectUri}")
    private String clientRedirectUri;


    /**
     * 获取到github返回的授权码code后发起post请求获取access_token，获取到用户信息
     * @param code
     * @param state
     * @return
     */
    @RequestMapping("/loginForGit")
    public String callback(@RequestParam(name = "code")String code,
            @RequestParam(name="state")String state){
        AccessTokenDTO accessTokenDTO=new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setState(state);
        accessTokenDTO.setRedirect_uri(clientRedirectUri);
        String accessToken = githubUtils.getAccessToken(accessTokenDTO);
        System.out.println(accessToken);//ghu_xXyfWBaYjwPaS0meQ1RcUuUE7jzxLE4XrcIs
        GitHubUserDTO gitHubUser = githubUtils.getGitHubUser(accessToken);
        System.out.println(JSON.toJSONString(gitHubUser));
        return "index";
    }
}
