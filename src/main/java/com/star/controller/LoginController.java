package com.star.controller;


import com.star.entity.User;
import com.star.entity.UserMail;
import com.star.service.RedisService;
import com.star.service.UserMailService;
import com.star.service.UserService;
import com.star.util.MyX;
import com.star.util.SendNoteUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@RequestMapping("/admin")
public class LoginController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserMailService userMailService;



    @Autowired
    private SendNoteUtil sendNoteUtil;

    @Autowired
    private RedisService redisService;

    @Value("${redis.key.prefix.authCode}")
    private String REDIS_KEY_PREFIX_AUTH_CODE;

    /**
     * 跳转界面
     */
    @GetMapping("/api/note")
    public String loginPage1(){
        return "redis/login";
    }

    /**
     * 获取验证码
     * @param phone
     * @param response
     */
    @RequestMapping(value = "/api/note/sendNote/{phone}",method = RequestMethod.GET)
    public void sendNote(@PathVariable("phone") String phone, HttpServletResponse response){
        System.out.println(phone);
        try {
            response.getWriter().write(sendNoteUtil.sendNoteMessgae(phone));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param phone
     * @param authCode
     * @return
     */

    @RequestMapping(value = "/api/note/login/{phone}/{authCode}")
    public String login(@PathVariable("phone") String phone, @PathVariable("authCode") String authCode){
        System.out.println("phonenumber--------------"+phone);
        MyX.phone1 = phone;
        MyX.authCode1 = authCode;
        //验证验证码
        if(!verifyAuthCode(authCode,phone)){
            //System.out.println("1222222222222222");
            return "redirect:/admin";
        }
        //System.out.println("123465789");
        return "admin/index";
    }

    /**
     * 对输入的验证码进行校验
     * 和Redis中的键值对进行比对即可
     * @param authCode
     * @param telephone
     * @return
     */
    private boolean verifyAuthCode(String authCode, String telephone){
        if(StringUtils.isEmpty(authCode)){
            return false;
        }
        String realAuthCode = redisService.get(REDIS_KEY_PREFIX_AUTH_CODE + telephone);
        //System.out.println("real-------------"+realAuthCode);
        return authCode.equals(realAuthCode);
    }

    @GetMapping
    public String loginPage(){
        return "admin/login";
    }
    /**
     * @Description: 登录校验
     * @Param: username:用户名
     * @Param: password:密码
     * @Param: session:session域
     * @Param: attributes:返回页面消息
     * @Return: 登录成功跳转登录成功页面，登录失败返回登录页面
     */
    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        RedirectAttributes attributes){
        //System.out.println("------------username"+username);
        //System.out.println("------------password"+password);

        User user = userService.checkUser(username,password);
        //System.out.println("------>" + user.getId());
        UserMail userMail = userService.checkUserMail(username,password);
        UserMail RealUserMail = userMailService.loginUserMail(userMail);
        if(user!=null){
            session.setAttribute("user",user);
            return "admin/index";
        }else if(RealUserMail!=null){
            MyX.EmailUsername = RealUserMail.getUsername();
            session.setAttribute("user",new User());
            return "admin/index";
        }else{
            attributes.addFlashAttribute("message","用户名或密码错误");
            return "redirect:/admin";
        }

    }

    //跳转到注册界面
    @GetMapping("/registerPage")
    public String registerPage(){
        return "admin/register";
    }

    /**
     * 根据user对象，注册用户
     * @param user  user由前端传入部分，业务层设置时间
     * @return
     */
    @PostMapping("/register")
    public String register(User user){
        int r = userService.insert(user);
        //System.out.println(user.toString());
        if(r == 0){
           return "error";
        }else{
           return "redirect:/admin";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("user");
        return "redirect:/admin";
    }
}
