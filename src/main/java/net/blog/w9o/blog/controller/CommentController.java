package net.blog.w9o.blog.controller;

import net.blog.w9o.blog.dto.CommentCreateDto;
import net.blog.w9o.blog.dto.CommentDto;
import net.blog.w9o.blog.dto.ResultDto;
import net.blog.w9o.blog.enums.CommentTypeEnum;
import net.blog.w9o.blog.exception.CustomizeErrorCode;
import net.blog.w9o.blog.model.Comment;
import net.blog.w9o.blog.model.User;
import net.blog.w9o.blog.service.CommentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class CommentController {
    @Autowired
    private CommentService commentService;
    @ResponseBody
    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    public Object post(@RequestBody CommentCreateDto commentCreateDto, HttpServletRequest request){
        User user=(User) request.getSession().getAttribute("user");
        if (user==null){
            return ResultDto.errorOf(CustomizeErrorCode.NOT_LOGIN);
        }
        if (commentCreateDto == null || StringUtils.isBlank(commentCreateDto.getContent())){
            return ResultDto.errorOf(CustomizeErrorCode.CONTENT_IS_EMPTY);
        }
        Comment comment = new Comment();
        comment.setContent(commentCreateDto.getContent());
        comment.setParentId(commentCreateDto.getParentId());
        comment.setType(commentCreateDto.getType());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(comment.getGmtCreate());
        comment.setCommentator(user.getId());
        comment.setLikeCount(0);
        commentService.insert(comment);
        return ResultDto.okOf();
    }
    @ResponseBody
    @RequestMapping(value = "/comment/{id}",method = RequestMethod.GET)
    public ResultDto <List<CommentDto>> comments(@PathVariable(name = "id")Long id){
        List<CommentDto> commentDtos = commentService.listByTargetId(id, CommentTypeEnum.COMMENT);
        return ResultDto.okOf(commentDtos);
    }
}
