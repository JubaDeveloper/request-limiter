package io.github.jubadeveloper.requestlimiter.core.services.contracts;

import io.github.jubadeveloper.requestlimiter.core.entities.RequestCapture;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface RequestCaptureContract {
    void saveRequest (RequestCapture requestCapture);
    void deleteByIp (String ip);
    List<RequestCapture> findRequestsByOriginIp (String ip);
    List<RequestCapture> findRequestsByIpAndPeriod (String ip, LocalDateTime startDate, LocalDateTime endDate);
    void releaseRequestsCapture ();
}
