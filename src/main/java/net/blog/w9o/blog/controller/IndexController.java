package net.blog.w9o.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller//Controller节点 允许它接收前段内容
public class IndexController {
    //默认返回index页面
    @GetMapping("/")
    public String index(){
        return "index";
    }
}
