package com.project.demo.api.log.application.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.project.demo.api.log.value.ErrLevel;
import com.project.demo.api.log.value.ResolvedStat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "로그 목록 조회를 위한 DTO")
public class LogListDTO {

    @Schema(description = "순번")
    private Long rownum;

    @Schema(description = "로그 SEQ")
    private Long logSeq;

    @Schema(description = "에러 코드")
    private String errCd;

    @Schema(description = "에러 메세지")
    private String errMsg;

    @Schema(description = "에러 심각도")
    private String errLevel;

    @Schema(description = "발생 메서드")
    private String requestMethod;

    @Schema(description = "발생 일시")
    private String occurredDt;

    @Schema(description = "해결 상태")
    private String resolvedStat;

    public LogListDTO(Long rownum, Long logSeq, String errCd, String errMsg, ErrLevel errLevel, String requestMethod, LocalDateTime occurredDt, LocalDateTime resolvedDt) {

        this.rownum = rownum;
        this.logSeq = logSeq;
        this.errCd = errCd;
        this.errMsg = errMsg;
        this.errLevel = (errLevel != null ? errLevel.getDescription() : null);
        this.requestMethod = requestMethod;
        this.occurredDt = (occurredDt != null ? occurredDt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) : null);
        this.resolvedStat = (resolvedDt != null ? ResolvedStat.Y.getDescription() : ResolvedStat.N.getDescription());
    }
}
