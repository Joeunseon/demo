package com.project.demo.api.user.application.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.project.demo.api.user.value.ActiveYn;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(description = "사용자 상세 조회를 위한 DTO")
public class UserDetailDTO {

    @Schema(description = "사용자 SEQ")
    private Long userSeq;

    @Schema(description = "권한 SEQ")
    private Integer roleSeq;

    @Schema(description = "권한")
    private String roleNm;

    @Schema(description = "사용자 ID")
    private String userId;

    @Schema(description = "사용자 이름")
    private String userNm;

    @Schema(description = "사용자 이메일")
    private String userEmail;

    @Schema(description = "프로필 이미지")
    private String profileImg;

    @Schema(description = "활성화 여부")
    @Enumerated(EnumType.STRING)
    private ActiveYn activeYn;

    @Schema(description = "가입일시")
    private String signupDt;

    public UserDetailDTO(Long userSeq, Integer roleSeq, String roleNm, String userId, String userNm, String userEmail, String profileImg, ActiveYn activeYn, LocalDateTime signupDt) {

        this.userSeq = userSeq;
        this.roleSeq = roleSeq;
        this.roleNm = roleNm;
        this.userId = userId;
        this.userNm = userNm;
        this.userEmail = userEmail;
        this.profileImg = profileImg;
        this.activeYn = activeYn;
        this.signupDt = (signupDt != null ? signupDt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) : null);
    }
}
