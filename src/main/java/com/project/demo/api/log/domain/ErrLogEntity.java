package com.project.demo.api.log.domain;

import java.time.LocalDateTime;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import com.project.demo.api.log.value.ErrLevel;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "err_log")
@Getter
@NoArgsConstructor
public class ErrLogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long logSeq;

    @Column(name = "err_cd", length = 50)
    private String errCd;

    @Column(name = "err_msg", columnDefinition = "TEXT")
    private String errMsg;

    @Column(name = "stack_trace", columnDefinition = "TEXT")
    private String stackTrace;

    @Enumerated(EnumType.STRING)
    @Column(name = "err_level", columnDefinition = "err_level")
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    private ErrLevel errLevel;

    @Column(name = "occurred_dt", nullable = false, updatable = false)
    private LocalDateTime occurredDt;

    @Column(name = "request_url", length = 255)
    private String requestUrl;

    @Column(name = "request_method", length = 10)
    private String requestMethod;

    @Column(name = "request_seq")
    private Long requestSeq;

    @Column(name = "resolved_dt")
    private LocalDateTime resolvedDt;

    @Builder
    public ErrLogEntity(Long logSeq, String errCd, String errMsg, String stackTrace, ErrLevel errLevel, LocalDateTime occurredDt, String requestUrl, String requestMethod, Long requestSeq, LocalDateTime resolvedDt) {

        this.logSeq = logSeq;
        this.errCd = errCd;
        this.errMsg = errMsg;
        this.stackTrace = stackTrace;
        this.errLevel = errLevel;
        this.occurredDt = occurredDt;
        this.requestUrl = requestUrl;
        this.requestMethod = requestMethod;
        this.requestSeq = requestSeq;
        this.resolvedDt = resolvedDt;
    }

    @PrePersist
    protected void onCreate() {
        if (this.occurredDt == null) {
            this.occurredDt = LocalDateTime.now();
        }
    }
}
