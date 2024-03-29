package net.blog.w9o.blog.controller;

import net.blog.w9o.blog.cache.Tagcache;
import net.blog.w9o.blog.dto.QuestionDto;
import net.blog.w9o.blog.mapper.QuestionMapper;
import net.blog.w9o.blog.mapper.UserMapper;
import net.blog.w9o.blog.model.Question;
import net.blog.w9o.blog.model.User;
import net.blog.w9o.blog.service.QuestionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {
    @Autowired
    private QuestionService questionService;

    @GetMapping("/publish/{id}")
    public String edit(@PathVariable(name = "id") Long id,Model model,HttpServletRequest request){
        QuestionDto question = questionService.getById(id);
        User user = (User) request.getSession().getAttribute("user");
        if (user==null || question.getCreator()!=user.getId()){
            return "redirect:/";
        }
        System.out.println(question.getCreator());
        model.addAttribute("title",question.getTitle());
        model.addAttribute("description",question.getDescription());
        model.addAttribute("tag",question.getTag());
        model.addAttribute("id",question.getId());
        model.addAttribute("tags", Tagcache.get());
        return "publish";
    }


    //get请求的方式用来渲染页面
    @GetMapping("/publish")
    public String publish(Model model){
        model.addAttribute("tags", Tagcache.get());
        return "publish";
    }

    //post请求用来执行操作
    @PostMapping("/publish")
    public  String doPublish(
            @RequestParam(value = "title",required = false) String title,
            @RequestParam(value = "description",required = false) String description,
            @RequestParam(value = "tag",required = false) String tag,
            @RequestParam(value = "id",required = false) Long id,
            HttpServletRequest request,
            Model model){
        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);
        model.addAttribute("tags", Tagcache.get());
        if(title==null || title==""){
            model.addAttribute("error","标题不能为空");
            return "publish";
        }
        if(description==null || description==""){
            model.addAttribute("error","问题补充不能为空");
            return "publish";
        }
        if(tag==null || tag==""){
            model.addAttribute("error","标签不能为空");
            return "publish";
        }
        String invalid = Tagcache.filterInvalid(tag);
        if (StringUtils.isNotBlank(invalid)){
            model.addAttribute("error","输入非法标签"+invalid);
            return "publish";
        }

        User user = (User)request.getSession().getAttribute("user");
        if(user==null){
            model.addAttribute("error","用户未登录");
            return "publish";
        }
        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());
        question.setId(id);
        questionService.createUpdate(question);
        return "redirect:/";
    }
}
