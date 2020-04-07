package cn.picturecool.authorization.admin.resolvers;

import cn.picturecool.DTO.AdminDTO;
import cn.picturecool.DTO.PictureUserDTO;
import cn.picturecool.authorization.admin.annotation.CurrentAdmin;
import cn.picturecool.authorization.annotation.CurrentUser;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

/**
 * @program: tuku
 * @description:
 * @author: 赵元昊
 * @create: 2020-03-24 20:42
 **/
@Component
public class CurrentAdminMethodArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().isAssignableFrom(AdminDTO.class) && parameter.hasParameterAnnotation(CurrentAdmin.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        AdminDTO adminDTO = (AdminDTO) webRequest.getAttribute("currentAdmin", RequestAttributes.SCOPE_REQUEST);
        if (adminDTO != null) {
            return adminDTO;
        }
        throw new MissingServletRequestPartException("currentAdmin");
    }
}
