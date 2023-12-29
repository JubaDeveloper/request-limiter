package io.github.jubadeveloper.requestlimiter.core.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;


@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class BlackListIp {
    @Id @GeneratedValue
    private Long id;
    private String ip;
    @CreatedDate
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
}
