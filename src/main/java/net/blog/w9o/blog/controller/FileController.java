package net.blog.w9o.blog.controller;

import net.blog.w9o.blog.dto.FileDto;
import net.blog.w9o.blog.provider.UcloudProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.codec.multipart.MultipartHttpMessageReader;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Controller
public class FileController {
    @Autowired
    private UcloudProvider ucloudProvider;
    @RequestMapping("/file/upload")
    @ResponseBody
    public FileDto upload(HttpServletRequest request){
        MultipartHttpServletRequest multipartHttpServletRequest =(MultipartHttpServletRequest)request;
        MultipartFile file = multipartHttpServletRequest.getFile("editormd-image-file");
        try {
            String fileName = ucloudProvider.upload(file.getInputStream(), file.getContentType(), file.getOriginalFilename());
            FileDto fileDto = new FileDto();
            fileDto.setSuccess(1);
            fileDto.setUrl(fileName);
            return fileDto;
        } catch (Exception e) {
            e.printStackTrace();
        }
        FileDto fileDto = new FileDto();
        fileDto.setSuccess(1);
        fileDto.setUrl("/images/lx.png");
        return fileDto;
    }
}
