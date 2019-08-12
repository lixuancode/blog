package net.blog.w9o.blog.controller;

import net.blog.w9o.blog.dto.NotificationDto;
import net.blog.w9o.blog.dto.PaginationDto;
import net.blog.w9o.blog.enums.NotificationTypeEnum;
import net.blog.w9o.blog.model.User;
import net.blog.w9o.blog.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class NotificationController {
    @Autowired
    private NotificationService notificationService;
    @GetMapping("notification/{id}")
    public String profile(HttpServletRequest request,@PathVariable(name = "id") Long id, Model model){
        User user = (User)request.getSession().getAttribute("user");
        if(user==null){
            return "redirect:/";
        }

       NotificationDto notificationDto = notificationService.read(id,user);
        if (NotificationTypeEnum.REPLY_COMMENT.getType() == notificationDto.getType() ||
                NotificationTypeEnum.REPLY_QUESTION.getType() == notificationDto.getType()){

            return "redirect:/question/"+notificationDto.getOuterid();
        }else {
            return "redirect:/";
        }
    }
}
