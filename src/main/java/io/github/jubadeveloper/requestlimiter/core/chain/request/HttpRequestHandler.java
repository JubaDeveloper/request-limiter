package io.github.jubadeveloper.requestlimiter.core.chain.request;

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

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Date;

@Component
public class HttpRequestHandler implements HandlerContract, HttpRequestHandlerContract {
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
        if (requests.size() >= 5) {
            this.blockIpForTwentySeconds(originIp);
            response.setStatus(403);
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
    public void blockIpForTwentySeconds(String originIp) {
        BlackListIp blackListIp = new BlackListIp();
        blackListIp.setIp(originIp);
        LocalDateTime expiresAt = LocalDateTime.now().plusSeconds(20);
        blackListIp.setExpiresAt(expiresAt);
        blackListIpService.createNewBlackListedIp(blackListIp);
        requestCaptureService.deleteByIp(originIp);
    }
}
