package net.blog.w9o.blog.service;

import net.blog.w9o.blog.mapper.UserMapper;
import net.blog.w9o.blog.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;
    public void createUpdate(User user) {
       User dbUser= userMapper.findByAccountId(user.getAccountId());
       if (dbUser==null){
           //数据库是否存在用户 不存在插入
           user.setGmtCreate(System.currentTimeMillis());
           user.setGmtModified(user.getGmtCreate());
           userMapper.insert(user);
       }else{
           //存在 更新token
           dbUser.setGmtModified(System.currentTimeMillis());
           dbUser.setName(user.getName());
           dbUser.setAvatarUrl(user.getAvatarUrl());
           dbUser.setToken(user.getToken());
           userMapper.update(dbUser);

       }
    }
}
