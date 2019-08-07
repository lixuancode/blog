package net.blog.w9o.blog.exception;

public class CustomizeException extends RuntimeException {
    private String message;
    public  CustomizeException(MyCustomizeErrorCode myCustomizeErrorCode){
        this.message=myCustomizeErrorCode.getMessage();
    }

    @Override
    public String getMessage(){
        return message;
    }
}

