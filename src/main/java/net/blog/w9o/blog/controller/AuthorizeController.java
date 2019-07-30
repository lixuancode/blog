package net.blog.w9o.blog.controller;

import net.blog.w9o.blog.dto.AccessTokenDto;
import net.blog.w9o.blog.dto.GithubUser;
import net.blog.w9o.blog.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorizeController {
    @Autowired
    private GithubProvider githubProvider;
    @GetMapping("/callback")
    public String callback(@RequestParam(name="code")String code,
                           @RequestParam(name="state")String state){ //RequestParam接收参数
        AccessTokenDto accessTokenDto = new AccessTokenDto();
        accessTokenDto.setClient_id("Iv1.7b3265842a4b3ab9");
        accessTokenDto.setClient_secret("54c1735f4b7ecf93717b6d433649aee02c5940e7");
        accessTokenDto.setCode(code);
        accessTokenDto.setRedirect_url("http://localhost:8887/callback");
        accessTokenDto.setState(state);
        String accessToken = githubProvider.getAccessToken(accessTokenDto);
        GithubUser user = githubProvider.getUser(accessToken);
        System.out.println(user.getName());
        return "index";//登录成功之后返回index页面
    }
}
