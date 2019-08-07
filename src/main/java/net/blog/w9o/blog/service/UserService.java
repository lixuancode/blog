package net.blog.w9o.blog.service;

import net.blog.w9o.blog.mapper.UserMapper;
import net.blog.w9o.blog.model.User;
import net.blog.w9o.blog.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;
    public void createUpdate(User user) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andAccountIdEqualTo(user.getAccountId());
        List<User> users = userMapper.selectByExample(userExample);
       if (users.size()==0){
           //数据库是否存在用户 不存在插入
           user.setGmtCreate(System.currentTimeMillis());
           user.setGmtModified(user.getGmtCreate());
           userMapper.insert(user);
       }else{
           //存在 更新token
           User dbUser= users.get(0);
           User updateUser = new User();
           updateUser.setGmtModified(System.currentTimeMillis());
           updateUser.setName(user.getName());
           updateUser.setAvatarUrl(user.getAvatarUrl());
           updateUser.setToken(user.getToken());
           UserExample example = new UserExample();
           example.createCriteria().andIdEqualTo(dbUser.getId());
           userMapper.updateByExampleSelective(updateUser, example);
       }
    }
}
