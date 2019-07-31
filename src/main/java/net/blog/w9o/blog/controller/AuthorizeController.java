package net.blog.w9o.blog.controller;

import net.blog.w9o.blog.dto.AccessTokenDto;
import net.blog.w9o.blog.dto.GithubUser;
import net.blog.w9o.blog.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

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
    @GetMapping("/callback")
    public String callback(@RequestParam(name="code")String code,
                           @RequestParam(name="state")String state, HttpServletRequest request)
    { //RequestParam接收参数
        AccessTokenDto accessTokenDto = new AccessTokenDto();
        accessTokenDto.setClient_id(clientId);
        accessTokenDto.setClient_secret(clientSecret);
        accessTokenDto.setCode(code);
        accessTokenDto.setRedirect_url(RedirectUrl);
        accessTokenDto.setState(state);
        String accessToken = githubProvider.getAccessToken(accessTokenDto);
        GithubUser user = githubProvider.getUser(accessToken);
        if(user!=null){
            //登录成功写cookie 与 session
            request.getSession().setAttribute("user",user);
            return  "redirect:/";//重定向跳转到index页面

        }else {
            //登录失败
            return "redirect:/";//重定向跳转到index页面
        }
    }
}
