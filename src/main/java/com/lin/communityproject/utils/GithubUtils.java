package com.lin.communityproject.utils;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.springframework.stereotype.Component;
import com.lin.communityproject.dto.AccessTokenDTO;
import com.alibaba.fastjson.JSON;
import com.lin.communityproject.dto.UserDTO;

import java.io.IOException;
/**
 *@program: CommunityProject
 *@description:
 *@author: lin han
 *@date: 2022/09/03
 */
@Component
public class GithubUtils {
    /**
     * 通过获取到的code发送post请求 ：POST https://github.com/login/oauth/access_token
     * 获取到access_token
     * @return
     */
    public String getAccessToken(AccessTokenDTO accessTokenDTO){
        MediaType json=MediaType.get("application/json;charset=utf-8");

        //通过okHttp发送post请求
        OkHttpClient client=new OkHttpClient();
        RequestBody body=RequestBody.create(json,JSON.toJSONString(accessTokenDTO));

        Request request=new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try {
            Response response=client.newCall(request).execute();
            /*
            注意：request.body():获取响应体，.string()解码响应体成byte[]数组构造成string 在.string()方法中finally中会在转换完后关闭资源。
            所以只能获取一次实体.否则就造成报错：[Request processing failed; nested exception is java.lang.IllegalStateException: closed]
            原因就是：在多次引用response.body().string()的时候，程序会崩溃掉。
             */
            String string = response.body().string();
            String access_token = string.split("&")[0].split("=")[1];//ctrl+alt+n 可以快速合并
            return  access_token;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public UserDTO getGitHubUser(String accessToken){
        OkHttpClient client=new OkHttpClient();
        Request request=new Request.Builder()
                .url("https://api.github.com/user")
                .header("Authorization","bearer "+accessToken)
                .build();
        try {
            Response response=client.newCall(request).execute();
            //这里有个点注意：response.body中获取到的图像的key是：avatar_url,由于fastjson可以自动将下划线标识映射成驼峰的属性，所以可以直接转换（否则userdto的avatarUrl应该写成avatar_url）
            return JSON.parseObject(response.body().string(), UserDTO.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
