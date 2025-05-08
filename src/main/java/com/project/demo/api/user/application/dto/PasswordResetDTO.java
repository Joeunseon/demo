package com.project.demo.api.user.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(description = "사용자 비밀번호 초기화를 위한 DTO")
public class PasswordResetDTO {

    private Long userSeq;

    private Long updSeq;

    private String encodePwd;

    @Builder
    public PasswordResetDTO(Long userSeq, Long updSeq, String encodePwd) {

        this.userSeq = userSeq;
        this.updSeq = updSeq;
        this.encodePwd = encodePwd;
    }
}
