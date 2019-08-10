package net.blog.w9o.blog.dto;

import lombok.Data;
//提交问题使用
@Data
public class CommentCreateDto {
    private Long parentId;
    private String content;
    private Integer type;
}
