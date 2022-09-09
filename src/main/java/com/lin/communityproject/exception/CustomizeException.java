package com.lin.communityproject.exception;
/**
 *@program: CommunityProject
 *@description:
 *@author: lin han
 *@date: 2022/09/06
 */
public class CustomizeException extends RuntimeException{
    private Integer code;
    private String message;
    public CustomizeException(Integer code,String message) {
        this.code=code;
        this.message = message;
    }

    public CustomizeException() {
    }
    public CustomizeException(ICustomizeErrorCode ex) {
        this.code=ex.getCode();
        this.message=ex.getMessage();
    }
    public Integer getCode() {
        return code;
    }
    public void setCode(Integer code) {
        this.code = code;
    }
    @Override
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}
