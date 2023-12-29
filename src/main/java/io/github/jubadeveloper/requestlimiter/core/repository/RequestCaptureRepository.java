package io.github.jubadeveloper.requestlimiter.core.repository;

import io.github.jubadeveloper.requestlimiter.core.entities.RequestCapture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public interface RequestCaptureRepository extends JpaRepository<RequestCapture, Long> {
    List<RequestCapture> findByOriginIP (String ip);
    List<RequestCapture> findByOriginIPAndCreatedAtGreaterThanEqualAndCreatedAtLessThanEqual (String ip, LocalDateTime startDate, LocalDateTime endDate);
    void deleteByCreatedAtLessThanEqual (Date date);
    void deleteByOriginIP (String ip);
}
