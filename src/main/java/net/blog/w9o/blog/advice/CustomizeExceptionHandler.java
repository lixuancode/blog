package net.blog.w9o.blog.advice;

import com.alibaba.fastjson.JSON;
import net.blog.w9o.blog.dto.ResultDto;
import net.blog.w9o.blog.exception.CustomizeErrorCode;
import net.blog.w9o.blog.exception.CustomizeException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


@ControllerAdvice
public class CustomizeExceptionHandler {

    @ExceptionHandler(Exception.class)
    Object handle (Throwable e, Model model, HttpServletRequest request,
                   HttpServletResponse response){
        String contentType = request.getContentType();
        if ("application/json".equals(contentType)){
            ResultDto resultDto;
            if (e instanceof CustomizeException){
               resultDto = ResultDto.errorOf((CustomizeException)e);
            }else {
                resultDto = ResultDto.errorOf(CustomizeErrorCode.SYS_ERROR);
            }
            try{
                response.setContentType("application/json");
                response.setStatus(200);
                response.setCharacterEncoding("utf-8");
                PrintWriter writer = response.getWriter();
                writer.write(JSON.toJSONString(resultDto));
                writer.close();

            }catch (IOException o){

            }
            return null;

        }else {
            if (e instanceof CustomizeException){
                model.addAttribute("message",e.getMessage());
            }else {
                model.addAttribute("message",CustomizeErrorCode.SYS_ERROR);
            }
            return new ModelAndView("error");

        }

    }
}
