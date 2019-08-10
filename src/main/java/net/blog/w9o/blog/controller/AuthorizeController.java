package net.blog.w9o.blog.controller;

import net.blog.w9o.blog.dto.AccessTokenDto;
import net.blog.w9o.blog.dto.GithubUser;
import net.blog.w9o.blog.mapper.UserMapper;
import net.blog.w9o.blog.model.User;
import net.blog.w9o.blog.provider.GithubProvider;
import net.blog.w9o.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class AuthorizeController {
    @Autowired
    private GithubProvider githubProvider;
    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.url}")
    private String RedirectUrl;
    @Autowired
    private UserService userService;
    @GetMapping("/callback")
    public String callback(@RequestParam(name="code")String code,
                           @RequestParam(name="state")String state,
                           HttpServletResponse response)
    { //RequestParam接收参数
        AccessTokenDto accessTokenDto = new AccessTokenDto();
        accessTokenDto.setClient_id(clientId);
        accessTokenDto.setClient_secret(clientSecret);
        accessTokenDto.setCode(code);
        accessTokenDto.setRedirect_url(RedirectUrl);
        accessTokenDto.setState(state);
        String accessToken = githubProvider.getAccessToken(accessTokenDto);
        GithubUser githubUser = githubProvider.getUser(accessToken);
        if(githubUser!=null && githubUser.getId()!=null){
            //登录成功写cookie 与 session
            //数据库写入信息
            User user = new User();
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(githubUser.getName());
            user.setAccountId(String.valueOf(githubUser.getId()));//String.valueOf 强制转换
            user.setAvatarUrl(githubUser.getAvatarUrl());
            userService.createUpdate(user);
            //cookie
            response.addCookie(new Cookie("token",token));

            return  "redirect:/";//重定向跳转到index页面

        }else {
            //登录失败
            return "redirect:/";//重定向跳转到index页面
        }
    }
    @GetMapping("/logout")
    public String logout(HttpServletRequest request,HttpServletResponse response){
            request.getSession().removeAttribute("user");//清空Session
            Cookie cookie = new Cookie("token", null);
            cookie.setMaxAge(0);
            response.addCookie(cookie);
        return "redirect:/";
    }
}
