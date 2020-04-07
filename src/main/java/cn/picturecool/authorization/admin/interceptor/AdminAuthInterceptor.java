package cn.picturecool.authorization.admin.interceptor;

import cn.picturecool.DTO.AdminDTO;
import cn.picturecool.DTO.PictureUserDTO;
import cn.picturecool.authorization.admin.annotation.Admin;
import cn.picturecool.service.admin.PictureAdminService;
import cn.picturecool.utils.token.AdminToken;
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
public class AdminAuthInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private PictureAdminService pictureAdminService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse httpServletResponse, Object handler) throws Exception {

        // 如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        String requestPath = request.getRequestURI();
        if (!method.isAnnotationPresent(Admin.class)) {
            return true;
        }
        String token = request.getHeader("ADMIN");

        if (StringUtils.isEmpty(token)) {
            throw new Exception("空token");
        }
        AdminDTO adminDTO = pictureAdminService.findAdminByName(AdminToken.getUser(token).getAdminName());
        request.setAttribute("currentAdmin", adminDTO);
        return true;

    }
}
