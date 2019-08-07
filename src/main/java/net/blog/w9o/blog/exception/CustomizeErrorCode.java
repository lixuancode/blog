package net.blog.w9o.blog.exception;

public enum CustomizeErrorCode implements MyCustomizeErrorCode{
    QUEATION_NOT_FOUND("你找的问题在审核或已被删除，换个问题试试吧！");
    private String message;
    @Override
    public String getMessage(){
        return message;
    }
    CustomizeErrorCode (String message){
        this.message=message;
    }
}
