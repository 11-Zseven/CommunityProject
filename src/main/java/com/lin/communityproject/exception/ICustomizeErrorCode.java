package com.lin.communityproject.exception;
//自定义异常状态码接口(方便后续拓展其他业务异常)：有状态码也有信息
public interface ICustomizeErrorCode {
    String getMessage();
    Integer getCode();
}
