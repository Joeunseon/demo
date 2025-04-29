package com.project.demo.api.user.application.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.project.demo.api.user.value.ActiveYn;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "사용자 목록 조회를 위한 DTO")
public class UserListDTO {

    @Schema(description = "순번")
    private Long rownum;

    @Schema(description = "사용자 SEQ")
    private Long userSeq;

    @Schema(description = "권한 이름")
    private String roleNm;

    @Schema(description = "사용자 ID")
    private String userId;

    @Schema(description = "사용자 이름")
    private String userNm;

    @Schema(description = "활성화 여부")
    private String activeYn;

    @Schema(description = "가입 일시")
    private String signupDt;

    public UserListDTO(Long rownum, Long userSeq, String roleNm, String userId, String userNm, ActiveYn activeYn, LocalDateTime signupDt) {

        this.rownum = rownum;
        this.userSeq = userSeq;
        this.roleNm = roleNm;
        this.userId = userId;
        this.userNm = userNm;
        this.activeYn = activeYn.getDescription();
        this.signupDt = (signupDt != null ? signupDt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) : null);
    }
}
