package net.blog.w9o.blog.controller;

import net.blog.w9o.blog.dto.CommentDto;
import net.blog.w9o.blog.dto.QuestionDto;
import net.blog.w9o.blog.service.CommentService;
import net.blog.w9o.blog.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class QuestionController {
    @Autowired
    private QuestionService questionService;
    @Autowired
    private CommentService commentService;
    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id")Long id, Model model){
        QuestionDto questionDto= questionService.getById(id);
       List<CommentDto> comments= commentService.listByQuestionId(id);
        //阅读数
        questionService.incView(id);

        model.addAttribute("question",questionDto);
        model.addAttribute("comments",comments);
        return "question";
    }
}
