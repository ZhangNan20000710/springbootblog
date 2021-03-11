package com.example.demo.config;

import com.example.demo.controller.Interceptors;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class MyMvcConfig extends WebMvcConfigurerAdapter {

    @Bean
    public WebMvcConfigurerAdapter getWebMvcConfigurerAdapter(){
        return new WebMvcConfigurerAdapter(){
            @Override
            public void addViewControllers(ViewControllerRegistry registry) {
//                registry.addViewController().setViewName();
            }

            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(new Interceptors())
                        .addPathPatterns("/*").excludePathPatterns("/register","/login","/","/Logout","/sign","/index/*");
            }
        };
    }
}
