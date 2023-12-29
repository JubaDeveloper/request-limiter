package io.github.jubadeveloper.requestlimiter.core.repository;

import io.github.jubadeveloper.requestlimiter.core.entities.BlackListIp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface BlackListIpRepository extends JpaRepository<BlackListIp, Long> {
    void deleteByCreatedAtGreaterThanEqual (Date date);
    BlackListIp findByIp (String ip);
    void deleteByIp (String ip);
}
