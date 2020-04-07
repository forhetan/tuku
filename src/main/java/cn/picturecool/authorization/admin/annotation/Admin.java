package cn.picturecool.authorization.admin.annotation;

import java.lang.annotation.*;

/**
 * @program: tuku
 * @description: 在controller类中标识需要用户权限的注解
 * @author: 赵元昊
 * @create: 2020-03-24 20:25
 **/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Admin {
}
