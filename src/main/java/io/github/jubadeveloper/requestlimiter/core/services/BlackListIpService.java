package io.github.jubadeveloper.requestlimiter.core.services;

import io.github.jubadeveloper.requestlimiter.core.entities.BlackListIp;
import io.github.jubadeveloper.requestlimiter.core.repository.BlackListIpRepository;
import io.github.jubadeveloper.requestlimiter.core.services.contracts.BlackListIpServiceContract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class BlackListIpService implements BlackListIpServiceContract {
    @Autowired
    private BlackListIpRepository blackListIpRepository;
    @Override
    public BlackListIp createNewBlackListedIp(BlackListIp blackListIp) {
        return blackListIpRepository.save(blackListIp);
    }

    @Override
    public BlackListIp getBlackListIpFromIp(String ip) {
        return blackListIpRepository.findByIp(ip);
    }

    @Override
    public void deleteBlackListIp(String ip) {
        blackListIpRepository.deleteByIp(ip);
    }

    @Override
    @Scheduled(initialDelay = 1000, fixedDelay = 15 * (1000 * 60))
    public void releaseExpiredBlackListedIps() {
        blackListIpRepository.deleteByCreatedAtGreaterThanEqual(new Date(System.currentTimeMillis()));
    }
}
