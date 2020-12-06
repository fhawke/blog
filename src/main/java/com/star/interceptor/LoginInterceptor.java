package com.star.interceptor;

import com.star.service.RedisService;
import com.star.util.MyX;
import com.star.util.SendNoteUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * @Description: 登录过滤拦截
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(request.getSession().getAttribute("user") == null&&(MyX.phone1 != "" && MyX.authCode1 != "")){
            System.out.println("----------------------------------进入到了preHandler");
            response.sendRedirect("/admin");
            return false;
        }
        return true;
    }
}
