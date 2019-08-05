package net.blog.w9o.blog.controller;

import net.blog.w9o.blog.dto.PaginationDto;
import net.blog.w9o.blog.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@Controller//Controller节点 允许它接收前段内容
public class IndexController {
    @Autowired
    private QuestionService questionService;
    //默认返回index页面
    @GetMapping("/")
    public String index(Model model,
                        @RequestParam(name = "page",defaultValue = "1") Integer page,
                        @RequestParam(name = "size",defaultValue = "5") Integer size
                        ) {
        PaginationDto pagination = questionService.list(page,size);
        model.addAttribute("pagination",pagination);
        return "index";


    }
}
