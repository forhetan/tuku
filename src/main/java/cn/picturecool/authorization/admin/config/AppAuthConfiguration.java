package cn.picturecool.authorization.admin.config;

import cn.picturecool.authorization.admin.annotation.Admin;
import cn.picturecool.authorization.admin.interceptor.AdminAuthInterceptor;
import cn.picturecool.authorization.admin.resolvers.CurrentAdminMethodArgumentResolver;
import cn.picturecool.authorization.interceptor.AppAuthInterceptor;
import cn.picturecool.authorization.resolvers.CurrentUserMethodArgumentResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

/**
 * @program: tuku
 * @description:
 * @author: 赵元昊
 * @create: 2020-03-24 20:48
 **/
@Configuration
public class AppAuthConfiguration extends WebMvcConfigurerAdapter {

    //关键，将拦截器作为bean写入配置中
/*
    @Bean
    public AppAuthInterceptor getSecurityInterceptor() {
        return new AppAuthInterceptor();
    }
*/

    @Autowired
    private AdminAuthInterceptor adminAuthInterceptor;
    @Autowired
    private CurrentAdminMethodArgumentResolver currentAdminMethodArgumentResolver;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册拦截器
        InterceptorRegistration ir = registry.addInterceptor(adminAuthInterceptor);
        // 配置拦截的路径
        ir.addPathPatterns("/**");
        // 配置不拦截的路径
//        ir.excludePathPatterns("**/swagger-ui.html");
        // 还可以在这里注册其它的拦截器
//        registry.addInterceptor(new AppAuthInterceptor()).addPathPatterns("/api/**");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(currentAdminMethodArgumentResolver);
        super.addArgumentResolvers(argumentResolvers);
    }

/*    @Bean
    public CurrentUserMethodArgumentResolver currentUserMethodArgumentResolver() {
        return new CurrentUserMethodArgumentResolver();
    }*/

}
