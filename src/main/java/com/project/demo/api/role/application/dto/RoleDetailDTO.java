package com.project.demo.api.role.application.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.util.StringUtils;

import com.project.demo.common.constant.DelYn;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(description = "권한 상세 조회를 위한 DTO")
public class RoleDetailDTO {

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

    @Schema(description = "수정자")
    private String updNm;

    @Schema(description = "수정일시")
    private String updDt;

    public RoleDetailDTO(Integer roleSeq, String roleNm, String roleDesc, DelYn delYn, String regNm, LocalDateTime regDt, String updNm, LocalDateTime updDt) {

        this.roleSeq = roleSeq;
        this.roleNm = roleNm;
        this.roleDesc = roleDesc;
        this.delYn = delYn;
        this.regNm = regNm;
        this.regDt = (regDt != null) ? regDt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) : null;
        this.updNm = StringUtils.hasText(updNm) ? updNm : regNm;
        this.updDt = (updDt != null) ? updDt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) : this.regDt;
    }
}
