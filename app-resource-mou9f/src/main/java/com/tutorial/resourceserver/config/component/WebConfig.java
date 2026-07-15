package com.tutorial.resourceserver.config.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//@Configuration
public class WebConfig  {

//    @Autowired
//    @Lazy
//    private SubscriptionInterceptor subscriptionInterceptor;
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(subscriptionInterceptor)
//                .addPathPatterns("/open/**")
//                .excludePathPatterns(
//                        "/open/addClient/**",
//                        "/auth/**",
//                        "/error/**",
//                        "/error",
//                        "/swagger-ui/**",
//                        "/v3/api-docs/**"
//                );
//    }
}
