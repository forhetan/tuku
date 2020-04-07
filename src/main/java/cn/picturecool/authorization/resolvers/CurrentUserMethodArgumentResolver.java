package cn.picturecool.authorization.resolvers;

import cn.picturecool.DTO.PictureUserDTO;
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
public class CurrentUserMethodArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().isAssignableFrom(PictureUserDTO.class) && parameter.hasParameterAnnotation(CurrentUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        PictureUserDTO userDTO = (PictureUserDTO) webRequest.getAttribute("currentUser", RequestAttributes.SCOPE_REQUEST);
        if (userDTO != null) {
            return userDTO;
        }
        throw new MissingServletRequestPartException("currentUser");
    }
}
