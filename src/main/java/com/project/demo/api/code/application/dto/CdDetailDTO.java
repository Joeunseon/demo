package com.project.demo.api.code.application.dto;

import com.project.demo.api.code.domain.CmmCdEntity;
import com.project.demo.api.code.value.UseYn;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "코드 상세 조회를 위한 DTO")
public class CdDetailDTO {

    @Schema(description = "코드 SEQ")
    private Integer cdSeq;

    @Schema(description = "코드")
    private String cd;

    @Schema(description = "코드 명")
    private String cdNm;

    @Schema(description = "코드 설명")
    private String cdDesc;

    @Schema(description = "사용여부")
    @Enumerated(EnumType.STRING)
    private UseYn useYn;

    public static CdDetailDTO of(CmmCdEntity entity) {

        return CdDetailDTO.builder()
                            .cdSeq(entity.getCdSeq())
                            .cd(entity.getCd())
                            .cdNm(entity.getCdNm())
                            .cdDesc(entity.getCdDesc())
                            .useYn(entity.getUseYn())
                            .build();
    }
}
