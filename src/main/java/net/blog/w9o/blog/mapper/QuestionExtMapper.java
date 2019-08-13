package net.blog.w9o.blog.mapper;

import net.blog.w9o.blog.dto.QuestionQueryDto;
import net.blog.w9o.blog.model.Question;

import java.util.List;

public interface QuestionExtMapper {
    int incView(Question record);
    int incCommentCount(Question record);
    List<Question> selectRelated(Question question);

    Integer countBySearch(QuestionQueryDto questionQueryDto);

    List<Question> selectBySearch(QuestionQueryDto questionQueryDto);

}
