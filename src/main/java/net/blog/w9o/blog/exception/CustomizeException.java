package net.blog.w9o.blog.exception;

public class CustomizeException extends RuntimeException {
    private String message;
    private Integer code;
    public  CustomizeException(MyCustomizeErrorCode myCustomizeErrorCode){
        this.message=myCustomizeErrorCode.getMessage();
        this.code=myCustomizeErrorCode.getCode();
    }

    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage(){
        return message;
    }
}

