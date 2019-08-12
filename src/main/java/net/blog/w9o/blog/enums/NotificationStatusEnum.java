package net.blog.w9o.blog.enums;

public enum NotificationStatusEnum {
    UNREAD(0),READ(1);
    private int status;
    NotificationStatusEnum(int stauts){
        this.status=stauts;
    }

    public int getStatus() {
        return status;
    }
}
