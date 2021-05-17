package com.javaclimb.musicplayer.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/*启动时自动加载继承的类
* 解决跨域问题
* */
//extends WebMvcConfigurerAdapter
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        /*
        * 跨域，允许所有路径， 所有web页面请求， 所有方法， 是否需要验证 true
        * */
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("*")
                .allowCredentials(true);

    }
}
