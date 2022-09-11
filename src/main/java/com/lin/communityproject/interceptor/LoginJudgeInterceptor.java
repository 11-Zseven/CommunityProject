package com.lin.communityproject.interceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import com.lin.communityproject.dto.ResultCodeDTO;
import com.lin.communityproject.dto.UserDTO;
import com.lin.communityproject.exception.CustomizeErrorCode;
import com.lin.communityproject.service.UserService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
/**
 *@program: CommunityProject
 *@description:登录验证
 *@author: lin han
 *@date: 2022/09/05
 */
@Service
public class LoginJudgeInterceptor implements HandlerInterceptor {

    @Autowired
    private UserService userService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        //需要做登录验证——之后抽取到拦截器
        //访问主页面，先判断cookie中是否有token值，并且判断和数据库中的是否一致，验证通过的话表示之前已经登陆过，无需重复登录
        if(request.getCookies()!=null)
        {
            List<Cookie> cookies = Arrays.asList(request.getCookies());
            List<Cookie> tokenList = cookies.stream().filter(one ->one.getName().equals("community_user_token")).collect(
                    Collectors.toList());
            if(tokenList.size()!=0){
                String token = tokenList.get(0).getValue();
                List<UserDTO> userByToken = userService.getUserByToken(token);
                if(userByToken.size()!=0){
                    request.getSession().setAttribute("user",userByToken.get(0));
                    return true;//登录验证成功 放行
                }
            }
        }
        if("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))){
            response.setHeader("REDIRECT","REDIRECT");
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.setHeader("CONTENTPATH","https://github.com/login/oauth/authorize?client_id=Iv1.1417ff976c0c76e4&redirect_uri=http://localhost:8080/loginForGit&state=1");
//            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().print(JSON.toJSON(ResultCodeDTO.resultOf(CustomizeErrorCode.UnlessLogin)));
//            response.getWriter().write(JSON.toJSONString(ResultCodeDTO.resultOf(CustomizeErrorCode.UnlessLogin)));
        }
        else response.sendRedirect("/");//跳转到首页
        return false;
    }
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
