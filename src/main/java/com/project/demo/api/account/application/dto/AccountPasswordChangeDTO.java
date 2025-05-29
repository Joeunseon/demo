package com.project.demo.api.account.application.dto;

import java.time.LocalDateTime;

import com.project.demo.api.user.domain.UserEntity;
import com.project.demo.common.BaseDTO;
import com.project.demo.common.validation.ValidationGroups.First;
import com.project.demo.common.validation.ValidationGroups.Second;
import com.project.demo.common.validation.ValidationGroups.Third;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "비밀번호 변경 진행을 위한 DTO")
public class AccountPasswordChangeDTO extends BaseDTO {

    @Schema(description = "기존 비밀번호")
    @NotBlank(message = "{error.validation('비밀번호')}", groups = First.class)
    private String userPwd;

    @Schema(description = "변경할 비밀번호 (대문자, 소문자, 숫자, 특수문자를 포함해야 함)")
    @NotBlank(message = "{error.validation('비밀번호')}", groups = Second.class)
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=\\[\\]{}:;\"'<>,.?/~`]).{8,16}$", message = "{error.signup.pwd_pattern}", groups = Third.class)
    private String newPwd;

    public UserEntity toEntity(Long userSeq, String pwdEncrypt) {

        return UserEntity.builder()
                            .userSeq(userSeq)
                            .userPwd(pwdEncrypt)
                            .lastPwdDt(LocalDateTime.now())
                            .build();
    }
}
