package net.blog.w9o.blog.dto;

import lombok.Data;

import java.util.List;
@Data
public class TagDto {
    private String categoryName;
    private List<String> tags;
}
