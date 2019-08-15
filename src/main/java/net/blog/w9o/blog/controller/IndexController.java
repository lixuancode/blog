package net.blog.w9o.blog.controller;

import net.blog.w9o.blog.dto.PaginationDto;
import net.blog.w9o.blog.dto.QuestionDto;
import net.blog.w9o.blog.schedule.Hot;
import net.blog.w9o.blog.schedule.HotTagche;
import net.blog.w9o.blog.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller//Controller节点 允许它接收前段内容
public class IndexController {
    @Autowired
    private QuestionService questionService;
    @Autowired
    private HotTagche hotTagche;
    //默认返回index页面
    @GetMapping("/")
    public String index(Model model,
                        @RequestParam(name = "page",defaultValue = "1") Integer page,
                        @RequestParam(name = "size",defaultValue = "10") Integer size,
                        @RequestParam(name = "search",required = false)String search,
                        @RequestParam(name = "tag",required = false)String tag
                        ) {
        PaginationDto pagination = questionService.list(search,tag,page,size);
        List<String> tags = hotTagche.getHots();
       List<QuestionDto> hot = hotTagche.getHotsQuetion();
        model.addAttribute("pagination",pagination);
        model.addAttribute("search",search);
        model.addAttribute("tag",tag);
        System.out.println(pagination);
        model.addAttribute("tags",tags);
        model.addAttribute("hotQuestions",hot);
        return "index";


    }
}
