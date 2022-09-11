package com.lin.communityproject.advice;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.lin.communityproject.dto.ResultCodeDTO;
import com.lin.communityproject.exception.CustomizeErrorCode;
import com.lin.communityproject.exception.CustomizeException;

import javax.servlet.http.HttpServletRequest;
/**
 *@program: CommunityProject
 *@description: @ControllerAdvice+@ExceptionHandler配合处理全局异常
 * https://blog.csdn.net/m1090760001/article/details/105454399  需要实现ErrorController接口，并重写getErrorPath()方法:新的好像这个接口中没有这个方法了？
 * https://blog.csdn.net/qq_41107231/article/details/115874974
 *@author: lin han
 *@date: 2022/09/06
 */
//有个问题未处理：如果访问无效请求返回error.html的话怎么设置message (而不是默认的no message available)
@ControllerAdvice
public class CustomizeExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    Object handle(Throwable ex, Model model,HttpServletRequest request){
        //两种形式返回，一种是返回json格式的数据，一种是直接返回错误页面
        String contentType = request.getContentType();
        if("application/json".equals(contentType)){
            if (ex instanceof  CustomizeException){
                return ResultCodeDTO.resultOf((CustomizeException) ex);
            }else {
                return ResultCodeDTO.resultOf(CustomizeErrorCode.System_Error);
            }
        }
        if(ex instanceof CustomizeException){
            model.addAttribute("message",ex.getMessage());
        }else {
            model.addAttribute("message",CustomizeErrorCode.System_Error.getMessage());
        }

        return new ModelAndView("error");
    }

    /**
     * 用于获取状态码
     */
    private HttpStatus getStatus(HttpServletRequest request){
        Integer statusCode=(Integer) request.getAttribute("javax.servlet.error.status_code");
        if(statusCode == null){
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }
}
