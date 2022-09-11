package com.lin.communityproject.exception;
/**
 *@program: CommunityProject
 *@description:
 *@author: lin han
 *@date: 2022/09/06
 */
public enum CustomizeErrorCode implements ICustomizeErrorCode{
    //枚举类型，会有个分号，就是用于写枚举数据...
    Question_Not_Found(2001,"您要访问/评论的问题不存在了呢~ （ps:请不要恶意访问！！)"),
    Target_Not_Fount(2002,"未选中问题/评论回复进行回复呢~(ps:有点常识行不行！)"),
    UnlessLogin(1001,"未登录不能评论/回复噢~"),
    System_Error(2003,"服务器内部错误！(ps:请不要写垃圾代码呢~)"),
    Comment_Type_Wrong(2004,"什么情况下会导致不是评论问题或者回复别人的评论呢？这个code定义的好奇怪.."),
    Comment_Not_Fount(2005,"您回复的评论已经不存在了呢~ (ps:天气这么好就不要那么暴躁啦~)"),
    No_Comment_Content(1002,"没有填写评论内容无法评论噢~");

    //枚举属性就是枚举数据括号中的数据
    private Integer code;
    private String message;
    CustomizeErrorCode(Integer code,String message) {
        this.code=code;
        this.message = message;
    }
    @Override
    public String getMessage() {
        return message;
    }
    @Override
    public Integer getCode() {
        return code;
    }

}
