package com.project.demo.api.code.application.dto;

import com.project.demo.api.code.value.UseYn;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(description = "코드 목록 조회를 위한 DTO")
public class CodeListDTO {

    @Schema(description = "코드 순번")
    private Long rownum;

    @Schema(description = "그룹코드 SEQ")
    private int grpSeq;

    @Schema(description = "그룹코드")
    private String grpCd;

    @Schema(description = "그룹코드명")
    private String grpNm;

    @Schema(description = "그룹코드 사용 여부")
    @Enumerated(EnumType.STRING)
    private UseYn grpUseYn;

    @Schema(description = "코드 SEQ")
    private int cdSeq;

    @Schema(description = "코드")
    private String cd;

    @Schema(description = "코드명")
    private String cdNm;

    @Schema(description = "코드 사용 여부")
    @Enumerated(EnumType.STRING)
    private UseYn codeUseYn;

    @Builder
    public CodeListDTO(Long rownum, int grpSeq, String grpCd, String grpNm, UseYn grpUseYn, int cdSeq, String cd, String cdNm, UseYn codeUseYn) {

        this.rownum = rownum;
        this.grpSeq = grpSeq;
        this.grpCd = grpCd;
        this.grpNm = grpNm;
        this.grpUseYn = grpUseYn;
        this.cdSeq = cdSeq;
        this.cd = cd;
        this.cdNm = cdNm;
        this.codeUseYn = codeUseYn;
    }
}
