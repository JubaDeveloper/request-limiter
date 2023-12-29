package io.github.jubadeveloper.requestlimiter.config;

import io.github.jubadeveloper.requestlimiter.actors.rest.interceptor.RequestInterceptor;
import io.github.jubadeveloper.requestlimiter.core.chain.request.HttpRequestBlackListHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebMvc
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    HttpRequestBlackListHandler httpRequestBlackListHandler;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry
                .addInterceptor(new RequestInterceptor(httpRequestBlackListHandler))
                .addPathPatterns("/**");
    }
}
