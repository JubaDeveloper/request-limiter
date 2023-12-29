package io.github.jubadeveloper.requestlimiter.core.chain.request.contracts;

import io.github.jubadeveloper.requestlimiter.core.entities.BlackListIp;

public interface BlackListExpiration {
    long getExpirationSeconds (BlackListIp blackListIp);
}
