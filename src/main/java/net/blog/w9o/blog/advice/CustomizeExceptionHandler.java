package net.blog.w9o.blog.advice;

import net.blog.w9o.blog.exception.CustomizeException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;


@ControllerAdvice
public class CustomizeExceptionHandler {

    @ExceptionHandler(Exception.class)
    ModelAndView handle (Throwable e, Model model){
        if (e instanceof CustomizeException){
            model.addAttribute("message",e.getMessage());
        }else {
            model.addAttribute("message","程序员跑路了！服务器冒烟了，稍等在试试吧！");
        }
        return new ModelAndView("error");
    }
}
