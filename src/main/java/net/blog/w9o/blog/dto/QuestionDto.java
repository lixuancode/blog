package net.blog.w9o.blog.dto;

import lombok.Data;
import net.blog.w9o.blog.model.User;

@Data
public class  QuestionDto {
    private Long id;
    private String title;
    private String description;
    private String tag;
    private Long gmtCreate;
    private Long gmtModified;
    private Long creator;
    private Integer viewCount;
    private  Integer likeCount;
    private Integer commentCount;
    private User user;
}
