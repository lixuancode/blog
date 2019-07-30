package net.blog.w9o.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller//Controller节点 允许它接收前段内容
public class HelloController {
    @GetMapping("/hello")
    public String hello(@RequestParam(name ="name") String name, Model model){ //RequestParam接收参数 (name ="name")= key name=values  Model 传递到指定页面
        model.addAttribute("name",name); //接收到值后 添加到model中
        return "hello";//会去templates查找同名的文件
    }
}
