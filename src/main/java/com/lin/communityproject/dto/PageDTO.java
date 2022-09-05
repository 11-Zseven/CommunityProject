package com.lin.communityproject.dto;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
/**
 *@program: CommunityProject
 *@description: 传输给前端分页数据  SpringBoot中使用PageHelper: https://www.cnblogs.com/mengw/p/11673637.html
 *@author: lin han
 *@date: 2022/09/05
 */
@Data
public class PageDTO {
    private List<QuestionDTO> questions;
    private Integer currentPage;//当前页
    private Boolean showFirstPage;//首页跳转按钮
    private Boolean showEndPage;
    private Boolean showPre;//上一页显示按钮
    private Boolean showNext;
    private Integer totalPage;
    private List<Integer> pages=new ArrayList<>();
    /**
     * 设置分页信息
     * @param page
     * @param total
     * @param size
     */
    public void setPagination(Integer page,Integer total,Integer size){
        //记录一共分几页
        totalPage= total%size==0?(total/size) :(total/size)+1;

        //页面显示几到几页  5页（当前在中间，左右隔两个）
        for(int i=page-2;i<=page+2;i++){
            if(i>0&&i<=totalPage) pages.add(i);
        }
//        另一种写法
            /*pages.add(page);
            for(int i=1;i<=3;i++){
                if((page-i)>0)
                    pages.add(0,page-i);
                if((page+i)<=totalPage)
                    pages.add(page+i);
            }*/
        //设置上/下一页 首/尾页按钮显示与否
        showPre=page!=1?true:false;
        showNext=page!=totalPage?true:false;
        showFirstPage=page!=1?true:false;
        showEndPage=page!=totalPage?true:false;

        //当前页
        currentPage=page;
    }

}
