package net.blog.w9o.blog.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.awt.*;

@Controller
@RequestMapping("${server.error.path:${error.path:/error}}")
public class CustomizeErrorController implements ErrorController {
    @Override
    public String getErrorPath() {
        return "error";
    }
    @RequestMapping(produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView errorHtml(HttpServletRequest request, Model model){
        HttpStatus status = getStatus(request);
        if (status.is4xxClientError()){
            model.addAttribute("message","这个请求跑到外星去了，稍等在试试吧！");
        }
        if (status.is5xxServerError()){
            model.addAttribute("message","程序员跑路了！服务器冒烟了，稍等在试试吧！");
        }
        return new ModelAndView("error");
    }
    private HttpStatus getStatus(HttpServletRequest request){
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode==null){
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        try {
            return HttpStatus.valueOf(statusCode);
        }catch (Exception ex){
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }
}
