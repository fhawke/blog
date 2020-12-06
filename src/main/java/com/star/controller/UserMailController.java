package com.star.controller;

import com.star.entity.UserMail;
import com.star.service.UserMailService;
import com.star.util.MD5Utils;
import com.star.util.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/mail")
public class UserMailController {

    @Autowired
    private UserMailService userMailService;


    @RequestMapping(value = "/returnIndex")
    public String index(){
        return "test/index";
    }

    /**
     * 注册
     */
    @RequestMapping(value = "/registerUserMail")
    public String registerMail(UserMail userMail){
        String password = userMail.getPassword();
        String RealPassword = MD5Utils.code(password);
        userMail.setPassword(RealPassword);
        userMail.setStatus(0);
        String code = UUIDUtils.getUUID() + UUIDUtils.getUUID();
        userMail.setCode(code);
        userMailService.register(userMail);
        return "test/success";
    }

    /**
     * 检验邮箱中的code激活账户
     */
    @RequestMapping(value = "/checkCode")
    public String checkCode(String code){
        UserMail userMail = userMailService.checkCode(code);
        //System.out.println(userMail);
        if(userMail != null){
            userMail.setStatus(1);
            userMail.setCode("");
            userMailService.updateUserStatus(userMail);
        }
        return "admin/login";
    }

    /**
     * 跳转到登陆页面
     */
    @RequestMapping(value = "/loginPage")
    public String MailLoginPage(){
        return "admin/login";
    }


}
