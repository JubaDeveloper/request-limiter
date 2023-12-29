package io.github.jubadeveloper.requestlimiter.core.chain.request;

import io.github.jubadeveloper.requestlimiter.core.chain.request.contracts.BlackListExpiration;
import io.github.jubadeveloper.requestlimiter.core.chain.request.contracts.HandlerContract;
import io.github.jubadeveloper.requestlimiter.core.chain.request.contracts.HttpRequestHandlerContract;
import io.github.jubadeveloper.requestlimiter.core.entities.BlackListIp;
import io.github.jubadeveloper.requestlimiter.core.entities.RequestCapture;
import io.github.jubadeveloper.requestlimiter.core.services.BlackListIpService;
import io.github.jubadeveloper.requestlimiter.core.services.RequestCaptureService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class HttpRequestHandler implements HandlerContract, BlackListExpiration, HttpRequestHandlerContract {
    @Autowired
    RequestCaptureService requestCaptureService;
    @Autowired
    BlackListIpService blackListIpService;
    @Override
    @Transactional
    public boolean execute(HttpServletRequest httpServletRequest,  HttpServletResponse response) {
        RequestCapture requestCapture = new RequestCapture();
        String originIp = httpServletRequest.getRemoteAddr();
        requestCapture.setOriginIP(originIp);
        requestCaptureService.saveRequest(requestCapture);
        List<RequestCapture> requests = this.findRequestsByLastFiveSeconds(originIp);
        if (requests.size() > 5) {
            BlackListIp blackListIp = this.blockIpForTwentySeconds(originIp);
            response.setStatus(429);
            response.setHeader("Retry-After", String.valueOf(this.getExpirationSeconds(blackListIp)));
            return false;
        }
        return true;
    }

    @Override
    public List<RequestCapture> findRequestsByLastFiveSeconds(String originIp) {
        // Should get requests from last 5 seconds
        LocalDateTime startDate = LocalDateTime.now().minusSeconds(5);
        LocalDateTime endDate = LocalDateTime.now();
        return requestCaptureService.findRequestsByIpAndPeriod(originIp, startDate, endDate);
    }

    @Override
    public BlackListIp blockIpForTwentySeconds(String originIp) {
        BlackListIp blackListIp = new BlackListIp();
        blackListIp.setIp(originIp);
        LocalDateTime expiresAt = LocalDateTime.now().plusSeconds(20);
        blackListIp.setExpiresAt(expiresAt);
        BlackListIp blackListIp1 = blackListIpService.createNewBlackListedIp(blackListIp);
        requestCaptureService.deleteByIp(originIp);
        return blackListIp1;
    }
    @Override
    public long getExpirationSeconds (BlackListIp blackListIp) {
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());
        Timestamp expiration = Timestamp.valueOf(blackListIp.getExpiresAt());
        return (expiration.getTime() - now.getTime()) / 1000;
    }
}
