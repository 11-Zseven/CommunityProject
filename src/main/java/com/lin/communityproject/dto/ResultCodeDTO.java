package com.lin.communityproject.dto;
import com.lin.communityproject.exception.CustomizeErrorCode;
import com.lin.communityproject.exception.CustomizeException;
/**
 *@program: CommunityProject
 *@description: 定义统一的返回前端的响应状态码DTO？（便于前后端分离）
 *@author: lin han
 *@date: 2022/09/07
 */
public class ResultCodeDTO {
    private Integer code;
    private String message;
    /**
     * 这几个errorOf是不是有点重复了？？？
     */
    public static ResultCodeDTO errorOf(Integer code,String message){
        ResultCodeDTO dto=new ResultCodeDTO();
        dto.setCode(code);
        dto.setMessage(message);
        return dto;
    }
    public static ResultCodeDTO errorOf(CustomizeErrorCode error) {
        return errorOf(error.getCode(),error.getMessage());
    }
    public static ResultCodeDTO errorOf(CustomizeException ex){
        return errorOf(ex.getCode(),ex.getMessage());
    }
    public ResultCodeDTO() {

    }
    public Integer getCode() {
        return code;
    }
    public void setCode(Integer code) {
        this.code = code;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}
