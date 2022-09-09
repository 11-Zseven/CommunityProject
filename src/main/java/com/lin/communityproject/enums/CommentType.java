package com.lin.communityproject.enums;
public enum CommentType {
    Question_TYPE(1),
    COMMENT_TYPE(2)
    ;
    CommentType(Integer type) {
        this.type = type;
    }
    CommentType() {
    }
    private Integer type;
    public Integer getType() {
        return type;
    }
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 用于判断是否是该类型之一
     */
    public static Boolean exist(Integer type){
        for (CommentType value : CommentType.values()) {
            if(type.equals(value.getType()))
                return true;
        }
        return false;
    }
}
