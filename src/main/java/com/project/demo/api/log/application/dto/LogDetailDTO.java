package com.project.demo.api.log.application.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.project.demo.api.log.value.ErrLevel;
import com.project.demo.api.log.value.ResolvedStat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(description = "로그 상세 조회를 위한 DTO")
public class LogDetailDTO {

    @Schema(description = "로그 SEQ")
    private Long logSeq;

    @Schema(description = "에러 코드")
    private String errCd;

    @Schema(description = "에러 메시지")
    private String errMsg;

    @Schema(description = "스택트레이스")
    private String stackTrace;

    @Schema(description = "에러 심각도")
    private String errLevel;

    @Schema(description = "발생일시")
    private String occurredDt;

    @Schema(description = "발생 URL")
    private String requestUrl;

    @Schema(description = "발생 Method")
    private String requestMethod;

    @Schema(description = "연관된 사용자ID")
    private String requestUserId;

    @Schema(description = "연관된 사용자명")
    private String requestUserNm;

    @Schema(description = "해결 상태")
    private String resolvedStat;

    @Schema(description = "해결일시")
    private String resolvedDt;

    public LogDetailDTO(Long logSeq, String errCd, String errMsg, String stackTrace, ErrLevel errLevel, LocalDateTime occurredDt, String requestUrl, String requestMethod, String requestUserId, String requestUserNm, LocalDateTime resolvedDt) {

        this.logSeq = logSeq;
        this.errCd = errCd;
        this.errMsg = errMsg;
        this.stackTrace = stackTrace;
        this.errLevel = (errLevel != null ? errLevel.getDescription() : null);
        this.occurredDt = (occurredDt != null ? occurredDt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) : null);
        this.requestUrl = requestUrl;
        this.requestMethod = requestMethod;
        this.requestUserId = requestUserId;
        this.requestUserNm = requestUserNm;
        this.resolvedStat = (resolvedDt != null ? ResolvedStat.Y.getDescription() : ResolvedStat.N.getDescription());
        this.resolvedDt = (resolvedDt != null ? resolvedDt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) : null);
    }
}
