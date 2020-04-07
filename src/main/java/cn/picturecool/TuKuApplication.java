package cn.picturecool;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @program: tuku
 * @description:
 * @author: 赵元昊
 * @create: 2020-02-24 22:37
 **/
@MapperScan("cn.picturecool.mapper")
@SpringBootApplication
public class TuKuApplication {
    public static void main(String[] args) {
        SpringApplication.run(TuKuApplication.class,args);
    }
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**");
            }
        };
    }
}
