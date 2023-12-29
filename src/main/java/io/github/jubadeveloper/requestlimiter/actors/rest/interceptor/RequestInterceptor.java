package io.github.jubadeveloper.requestlimiter.actors.rest.interceptor;

import io.github.jubadeveloper.requestlimiter.core.chain.request.HttpRequestBlackListHandler;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class RequestInterceptor implements HandlerInterceptor {
    HttpRequestBlackListHandler httpRequestBlackListHandler;
    public RequestInterceptor (@Autowired HttpRequestBlackListHandler httpRequestBlackListHandler) {
        this.httpRequestBlackListHandler = httpRequestBlackListHandler;
    }
    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) throws Exception {
        return httpRequestBlackListHandler.execute(request, response);
    }
}
