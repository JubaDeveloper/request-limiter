package io.github.jubadeveloper.requestlimiter.core.services;

import io.github.jubadeveloper.requestlimiter.core.entities.RequestCapture;
import io.github.jubadeveloper.requestlimiter.core.repository.RequestCaptureRepository;
import io.github.jubadeveloper.requestlimiter.core.services.contracts.RequestCaptureContract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class RequestCaptureService implements RequestCaptureContract {
    @Autowired
    RequestCaptureRepository requestCaptureRepository;
    @Override
    public void saveRequest(RequestCapture requestCapture) {
        requestCaptureRepository.save(requestCapture);
    }

    @Override
    public void deleteByIp(String ip) {
        requestCaptureRepository.deleteByOriginIP(ip);
    }
    @Override
    public List<RequestCapture> findRequestsByIpAndPeriod(String ip, LocalDateTime startDate, LocalDateTime endDate) {
        return requestCaptureRepository
                .findByOriginIPAndCreatedAtGreaterThanEqualAndCreatedAtLessThanEqual(ip, startDate, endDate);
    }
    @Override
    public List<RequestCapture> findRequestsByOriginIp(String ip) {
        return requestCaptureRepository.findByOriginIP(ip);
    }

    @Override
    @Scheduled(fixedRate = 1000 * 60) // Release after each 1 minute
    public void releaseRequestsCapture() {
        Date date = new Date();
        int oneMinute = (60 * 1000);
        date.setTime(date.getTime() - oneMinute);
        requestCaptureRepository.deleteByCreatedAtLessThanEqual(date);
    }
}
