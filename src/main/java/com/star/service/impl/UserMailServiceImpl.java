package com.star.service.impl;

import com.star.dao.UserMailDao;
import com.star.entity.UserMail;
import com.star.service.IMailService;
import com.star.service.UserMailService;
import com.star.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserMailServiceImpl implements UserMailService {

    @Autowired
    private UserMailDao userMailDao;

    @Autowired
    private IMailService iMailService;
    /**
     * 用户注册，同时发送一封激活邮件
     * @param userMail
     */
    @Override
    public void register(UserMail userMail) {
        userMailDao.register(userMail);
        //获取激活码
        String code = userMail.getCode();
        System.out.println("code: "+code);
        //主题
        String subject = "来自博客的激活邮件";
        //正文
        String context = "<a href=\"/mail/checkCode?code="+code+"\">激活请点击:"+code+"</a>";
        //发送
        iMailService.sentHtmlMail(userMail.getUseremail(),subject,context);
    }

    /**
     * 根据激活码code进行查询用户，之后修改状态
     * @param code
     * @return
     */
    @Override
    public UserMail checkCode(String code) {
        return userMailDao.checkCode(code);
    }

    /**
     * 激活账户，修改状态
     * @param userMail
     */
    @Override
    public void updateUserStatus(UserMail userMail) {

        userMailDao.updateUserStatus(userMail);
    }

    /**
     * 登陆
     * @param userMail
     * @return
     */
    @Override
    public UserMail loginUserMail(UserMail userMail) {
        UserMail userMail1 = userMailDao.loginUserMail(userMail);
        if(userMail1 != null){
            return userMail1;
        }
        return null;
    }
}
