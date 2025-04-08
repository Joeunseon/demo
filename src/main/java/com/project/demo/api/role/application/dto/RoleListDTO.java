package com.project.demo.api.role.application.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.project.demo.common.constant.DelYn;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(description = "권한 목록 조회를 위한 DTO")
public class RoleListDTO {

    @Schema(description = "권한 순번")
    private Long rownum;

    @Schema(description = "권한 SEQ")
    private Integer roleSeq;

    @Schema(description = "권한")
    private String roleNm;

    @Schema(description = "권한 설명")
    private String roleDesc;

    @Schema(description = "삭제 여부")
    @Enumerated(EnumType.STRING)
    private DelYn delYn;

    @Schema(description = "등록자")
    private String regNm;

    @Schema(description = "등록일시")
    private String regDt;

    public RoleListDTO(Long rownum, Integer roleSeq, String roleNm, String roleDesc, DelYn delYn, String regNm, LocalDateTime regDt) {

        this.rownum = rownum;
        this.roleSeq = roleSeq;
        this.roleNm = roleNm;
        this.roleDesc = roleDesc;
        this.delYn = delYn;
        this.regNm = regNm;
        this.regDt = (regDt != null) ? regDt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) : null;
    }
}
