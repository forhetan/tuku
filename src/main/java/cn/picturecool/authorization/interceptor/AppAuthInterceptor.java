package cn.picturecool.authorization.interceptor;

import cn.picturecool.DTO.PictureUserDTO;
import cn.picturecool.authorization.annotation.Authorization;
import cn.picturecool.service.user.PictureUserService;
import cn.picturecool.utils.token.UserToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @program: tuku
 * @description:
 * @author: 赵元昊
 * @create: 2020-03-24 20:29
 **/
@Component
public class AppAuthInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private PictureUserService pictureUserService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse httpServletResponse, Object handler) throws Exception {

        // 如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        String requestPath = request.getRequestURI();
//        log.debug("requestIp: " + getIpAddress(request));
        /*log.debug("Method: " + method.getName() + ", IgnoreSecurity: " + method.isAnnotationPresent(IgnoreSecurity.class));
        log.debug("requestPath: " + requestPath);*/
        /*if (requestPath.contains("/v2/api-docs") || requestPath.contains("/swagger") || requestPath.contains("/configuration/ui")) {
            return true;
        }
        if (requestPath.contains("/error")) {
            return true;
        }*/
        if (!method.isAnnotationPresent(Authorization.class)) {
            return true;
        }
        /*System.out.println(requestPath);*/
        String token = request.getHeader("TOKEN");

        /*System.out.println(token);*/
        if (StringUtils.isEmpty(token)) {
            throw new Exception("空token");
        }
        PictureUserDTO userDTO = pictureUserService.findUserById(UserToken.getUser(token).getUserId());
        request.setAttribute("currentUser", userDTO);
        return true;

    }
}
