package com.lin.communityproject.dto;
import com.lin.communityproject.exception.CustomizeErrorCode;
import com.lin.communityproject.exception.CustomizeException;
/**
 *@program: CommunityProject
 *@description: 定义统一的返回前端的响应状态码DTO？（便于前后端分离）
 *@author: lin han
 *@date: 2022/09/07
 */
public class ResultCodeDTO<T> {
    private Integer code;
    private String message;
    private T data;

    /*泛型的使用*/
    public static <T> ResultCodeDTO resultOf(T data){
        ResultCodeDTO dto=new ResultCodeDTO();
        dto.setCode(200);
        dto.setMessage("请求成功");
        dto.setData(data);
        return dto;
    }

    public static ResultCodeDTO resultOf(Integer code,String message){
        ResultCodeDTO dto=new ResultCodeDTO();
        dto.setCode(code);
        dto.setMessage(message);
        return dto;
    }
    public static ResultCodeDTO resultOf(CustomizeErrorCode error) {
        return resultOf(error.getCode(),error.getMessage());
    }
    public static ResultCodeDTO resultOf(CustomizeException ex){
        return resultOf(ex.getCode(),ex.getMessage());
    }

    public static ResultCodeDTO successOf(){
        ResultCodeDTO dto=new ResultCodeDTO();
        dto.setCode(200);
        dto.setMessage("success!");
        return dto;
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
    public T getData() {
        return data;
    }
    public void setData(T data) {
        this.data = data;
    }
}
