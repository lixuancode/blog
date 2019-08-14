package net.blog.w9o.blog.dto;

import lombok.Data;

@Data
public class QuestionQueryDto {
    private String search;
    private String tag;
    private Integer page;
    private Integer size;
}
