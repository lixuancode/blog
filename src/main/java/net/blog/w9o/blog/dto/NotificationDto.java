package net.blog.w9o.blog.dto;

import lombok.Data;

@Data
public class NotificationDto {
    private Long id;
    private Long gmtCreate;
    private Integer status;
    private String notifierName;
    private Long notifier;
    private Long outerid;
    private String outerTitle;
    private String typeName;
    private Integer type;
}
