package com.lin.communityproject.exception;
/**
 *@program: CommunityProject
 *@description:
 *@author: lin han
 *@date: 2022/09/06
 */
public enum CustomizeErrorCode implements ICustomizeErrorCode{
    //枚举类型，会有个分号，就是用于写枚举数据...
    Question_Not_Found("您访问的问题不存在了呢~ （ps:请不要恶意访问！！)");

    //枚举属性就是枚举数据括号中的数据
    private String message;
    CustomizeErrorCode(String message) {
        this.message = message;
    }
    @Override
    public String getMessage() {
        return message;
    }
}
