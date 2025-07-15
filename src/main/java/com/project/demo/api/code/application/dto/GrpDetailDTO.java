package com.project.demo.api.code.application.dto;

import java.util.ArrayList;
import java.util.List;

import com.project.demo.api.code.domain.CmmCdGrpEntity;
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
@Schema(description = "그룹코드 상세 조회를 위한 DTO")
public class GrpDetailDTO {

    @Schema(description = "그룹코드 SEQ")
    private Integer grpSeq;

    @Schema(description = "그룹코드")
    private String grpCd;

    @Schema(description = "그룹코드 명")
    private String grpNm;

    @Schema(description = "그룹코드 설명")
    private String grpDesc;

    @Schema(description = "사용여부")
    @Enumerated(EnumType.STRING)
    private UseYn useYn;

    @Schema(description = "하위 코드 리스트")
    private List<CdDetailDTO> children;

    public static GrpDetailDTO of(CmmCdGrpEntity entity) {

        return GrpDetailDTO.builder()
                            .grpSeq(entity.getGrpSeq())
                            .grpCd(entity.getGrpCd())
                            .grpNm(entity.getGrpNm())
                            .grpDesc(entity.getGrpDesc())
                            .useYn(entity.getUseYn())
                            .children(new ArrayList<>())
                            .build();
    }

    public void ofChildren(List<CdDetailDTO> children) {

        this.children = children;
    }
}
