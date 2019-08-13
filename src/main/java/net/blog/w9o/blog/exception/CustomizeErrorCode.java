package net.blog.w9o.blog.exception;

public enum CustomizeErrorCode implements MyCustomizeErrorCode{
    QUESTION_NOT_FOUND(2001,"你找的问题在审核或已被删除，换个问题试试吧！"),
    TARGET_PARAM_NOT_FOUND(2002,"未选中任何问题或评论进行回复！"),
    NOT_LOGIN(2003,"当前未登录，不可执行该操作！"),
    SYS_ERROR(2004,"程序员跑路了！服务器冒烟了，稍等在试试吧！"),
    TYPE_PARAM_WRONG(2005,"评论类型错误或不存在！"),
    COMMENT_NOT_FOUND(2006,"你操作的评论在审核或已被删除，换个问题试试吧！"),
    CONTENT_IS_EMPTY(2007,"输入内容不能为空！"),
    READ_NOTIFICATION_FAIL(2008,"兄dei，这不是你的评论哦！！"),
    NOTIFICATION_NOT_FOUND(2009,"消息莫非不翼而飞了？"),
    FILE_UPLOAD_FAIL(2010,"图片上传失败！");
    private String message;
    private Integer code;

    CustomizeErrorCode(Integer code, String message) {
        this.message = message;
        this.code = code;
    }


    @Override
    public String getMessage(){
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }
}
