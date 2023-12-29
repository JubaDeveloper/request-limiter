package io.github.jubadeveloper.requestlimiter.core.chain.request;

import io.github.jubadeveloper.requestlimiter.core.chain.request.contracts.HandlerContract;
import io.github.jubadeveloper.requestlimiter.core.entities.BlackListIp;
import io.github.jubadeveloper.requestlimiter.core.services.BlackListIpService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Component
public class HttpRequestBlackListHandler implements HandlerContract {
    HttpRequestHandler httpRequestHandler;
    @Autowired
    BlackListIpService blackListIpService;
    public HttpRequestBlackListHandler (HttpRequestHandler httpRequestHandler) {
        this.httpRequestHandler = httpRequestHandler;
    }
    @Override
    @Transactional
    public boolean execute(HttpServletRequest httpServletRequest,  HttpServletResponse response) throws IOException {
        String originIp = httpServletRequest.getRemoteAddr();
        BlackListIp blackListIp = blackListIpService.getBlackListIpFromIp(originIp);
        if (blackListIp == null) {
            return httpRequestHandler.execute(httpServletRequest, response);
        }
        LocalDateTime currentDate = LocalDateTime.now();
        if (blackListIp.getExpiresAt().isBefore(currentDate)) {
            blackListIpService.deleteBlackListIp(originIp);
            return httpRequestHandler.execute(httpServletRequest, response);
        }
        response.setStatus(429);
        response.setHeader("Retry-After", String.valueOf(blackListIp.getExpiresAt().getSecond() - currentDate.getSecond()));
        return false;
    }
}
