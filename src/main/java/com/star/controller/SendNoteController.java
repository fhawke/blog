//package com.star.controller;
//
//import com.star.service.RedisService;
//import com.star.util.SendNoteUtil;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.*;
//import org.thymeleaf.util.StringUtils;
//
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//@Controller
//@RequestMapping("api/note")
//public class SendNoteController {
//
//    @Autowired
//    private SendNoteUtil sendNoteUtil;
//
//    @Autowired
//    private RedisService redisService;
//
//    @Value("${redis.key.prefix.authCode}")
//    private String REDIS_KEY_PREFIX_AUTH_CODE;
//
//    /**
//     * 跳转界面
//     */
//    @GetMapping
//    public String loginPage(){
//        return "redis/login";
//    }
//
//    /**
//     * 获取验证码
//     * @param phone
//     * @param response
//     */
//    @RequestMapping(value = "/sendNote/{phone}",method = RequestMethod.GET)
//    public void sendNote(@PathVariable("phone") String phone, HttpServletResponse response){
//        System.out.println(phone);
//        try {
//            response.getWriter().write(sendNoteUtil.sendNoteMessgae(phone));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     *
//     * @param phone
//     * @param authCode
//     * @return
//     */
//    @RequestMapping(value = "/login/{phone}/{authCode}")
//    public String login(@PathVariable("phone") String phone, @PathVariable("authCode") String authCode){
//        System.out.println("phonenumber--------------"+phone);
//        //验证验证码
//        if(!verifyAuthCode(authCode,phone)){
//            //System.out.println("1222222222222222");
//            return "redirect:/admin";
//        }
//        //System.out.println("123465789");
//        return "admin/index";
//    }
//
//    /**
//     * 对输入的验证码进行校验
//     * 和Redis中的键值对进行比对即可
//     * @param authCode
//     * @param telephone
//     * @return
//     */
//    private boolean verifyAuthCode(String authCode, String telephone){
//        if(StringUtils.isEmpty(authCode)){
//            return false;
//        }
//        String realAuthCode = redisService.get(REDIS_KEY_PREFIX_AUTH_CODE + telephone);
//        //System.out.println("real-------------"+realAuthCode);
//        return authCode.equals(realAuthCode);
//    }
//}
