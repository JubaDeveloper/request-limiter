package io.github.jubadeveloper.requestlimiter.core.services.contracts;

import io.github.jubadeveloper.requestlimiter.core.entities.BlackListIp;

public interface BlackListIpServiceContract {
    BlackListIp createNewBlackListedIp (BlackListIp blackListIp);
    BlackListIp getBlackListIpFromIp (String ip);
    void deleteBlackListIp (String ip);
    void releaseExpiredBlackListedIps ();
}
