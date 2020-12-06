package com.star.service.impl;

import com.star.dao.UserDao;
import com.star.entity.User;
import com.star.entity.UserMail;
import com.star.service.UserService;
import com.star.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User checkUser(String username, String password) {
        User user = userDao.findByUsernameAndPassword(username, MD5Utils.code(password));
        return user;
    }

    @Override
    public UserMail checkUserMail(String username, String password) {
        UserMail userMail = userDao.findByUserMail(username,MD5Utils.code(password));
        return userMail;
    }

    @Override
    public int insert(User user) {
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        String realPassword = MD5Utils.code(user.getPassword());
        user.setPassword(realPassword);
        return userDao.insert(user);
    }


}
