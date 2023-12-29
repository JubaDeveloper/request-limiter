package io.github.jubadeveloper.requestlimiter.config;

import io.github.jubadeveloper.requestlimiter.core.chain.request.HttpRequestBlackListHandler;
import io.github.jubadeveloper.requestlimiter.core.chain.request.HttpRequestHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

@Configuration
public class HttpHandlerConfig {
    @Bean
    HttpRequestHandler httpRequestHandler () {
        return new HttpRequestHandler();
    }
    @DependsOn("httpRequestHandler")
    @Bean
    HttpRequestBlackListHandler httpRequestBlackListHandler (@Autowired HttpRequestHandler httpRequestHandler) {
        return new HttpRequestBlackListHandler(httpRequestHandler);
    }
}
