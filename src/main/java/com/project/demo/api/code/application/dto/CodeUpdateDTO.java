package com.project.demo.api.code.application.dto;

import java.time.LocalDateTime;

import com.project.demo.api.code.domain.CmmCdEntity;
import com.project.demo.api.code.domain.CmmCdGrpEntity;
import com.project.demo.api.code.value.UseYn;
import com.project.demo.common.BaseDTO;
import com.project.demo.common.validation.ValidationGroups.Fifth;
import com.project.demo.common.validation.ValidationGroups.First;
import com.project.demo.common.validation.ValidationGroups.Fourth;
import com.project.demo.common.validation.ValidationGroups.Second;
import com.project.demo.common.validation.ValidationGroups.Third;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "코드 수정 진행을 위한 DTO")
public class CodeUpdateDTO extends BaseDTO {

    @Schema(description = "수정할 코드의 그룹 코드 SEQ")
    private Integer grpSeq;

    @Schema(description = "수정할 코드의 SEQ")
    private Integer cdSeq;

    @Schema(description = "수정 코드 (50자 이내)")
    @NotBlank(message = "{error.validation('코드')}", groups = First.class)
    @Size(min = 1, max = 50, message ="{error.length('코드', 1, 50)}", groups = Second.class)
    private String cd;

    @Schema(description = "수정 코드명 (100자 이내)")
    @NotBlank(message = "{error.validation('코드명')}", groups = Third.class)
    @Size(min = 1, max = 100, message ="{error.length('코드', 1, 100)}", groups = Fourth.class)
    private String cdNm;

    @Schema(description = "수정 코드 설명 (200자 이내)")
    @Size(min = 0, max = 200, message ="{error.length('코드', 0, 200)}", groups = Fifth.class)
    private String cdDesc;

    @Schema(description = "사용 여부")
    @Enumerated(EnumType.STRING)
    private UseYn useYn;

    public CmmCdGrpEntity toGrpEntity(Long userSeq) {

        return CmmCdGrpEntity.builder()
                            .grpCd(cd)
                            .grpNm(cdNm)
                            .grpDesc(cdDesc)
                            .useYn(useYn)
                            .updDt(LocalDateTime.now())
                            .updSeq(userSeq)
                            .build();
    }

    public CmmCdEntity toCdEntity(Long userSeq) {

        CmmCdGrpEntity grpEntity = CmmCdGrpEntity.builder().grpSeq(grpSeq).build();
        return CmmCdEntity.builder()
                            .cmmCdGrp(grpEntity)
                            .cd(cd)
                            .cdNm(cdNm)
                            .cdDesc(cdDesc)
                            .useYn(useYn)
                            .updDt(LocalDateTime.now())
                            .updSeq(userSeq)
                            .build();
    }
}
