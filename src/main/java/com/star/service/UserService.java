package com.star.service;

import com.star.entity.User;
import com.star.entity.UserMail;

public interface UserService {
    //核对用户名和密码
    User checkUser(String username,String password);
    int  insert(User user);
    UserMail checkUserMail(String username,String password);
}
