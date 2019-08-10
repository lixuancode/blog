package net.blog.w9o.blog.mapper;

import net.blog.w9o.blog.model.Question;

public interface QuestionExtMapper {
    int incView(Question record);
    int incCommentCount(Question record);
}
