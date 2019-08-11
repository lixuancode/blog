package net.blog.w9o.blog.mapper;

import net.blog.w9o.blog.model.Comment;

public interface CommentExtMapper {
    int incCommentCount(Comment comment);
}
