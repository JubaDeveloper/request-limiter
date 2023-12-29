package io.github.jubadeveloper.requestlimiter.core.chain.request.contracts;

import io.github.jubadeveloper.requestlimiter.core.entities.RequestCapture;

import java.util.List;

public interface HttpRequestHandlerContract {
    List<RequestCapture> findRequestsByLastFiveSeconds (String originIp);
    void blockIpForTenSeconds (String originIp);
}
