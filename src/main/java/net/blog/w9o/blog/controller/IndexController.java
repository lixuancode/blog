package net.blog.w9o.blog.controller;

import net.blog.w9o.blog.dto.QuestionDto;
import net.blog.w9o.blog.mapper.QuestionMapper;
import net.blog.w9o.blog.mapper.UserMapper;
import net.blog.w9o.blog.model.Question;
import net.blog.w9o.blog.model.User;
import net.blog.w9o.blog.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller//Controller节点 允许它接收前段内容
public class IndexController {
    @Autowired
    private QuestionService questionService;
    @Autowired
    private UserMapper userMapper;
    //默认返回index页面
    @GetMapping("/")
    public String index(HttpServletRequest request, Model model) {
        Cookie[] cookies = request.getCookies();
        if (cookies!=null && cookies.length!=0){
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")){
                    String token = cookie.getValue();
                    User user = userMapper.findByToken(token);

                    if(user!=null){
                        request.getSession().setAttribute("user",user);
                    }
                    break;
                }
            }
        }
        List<QuestionDto> questionList = questionService.list();
        model.addAttribute("questions",questionList);
        return "index";


    }
}
