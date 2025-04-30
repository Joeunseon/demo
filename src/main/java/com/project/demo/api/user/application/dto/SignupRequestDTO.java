package com.project.demo.api.user.application.dto;

import java.time.LocalDateTime;

import com.project.demo.api.role.domain.RoleEntity;
import com.project.demo.api.user.domain.UserEntity;
import com.project.demo.api.user.value.ActiveYn;
import com.project.demo.common.validation.ValidationGroups.*;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "회원가입 진행을 위한 DTO")
public class SignupRequestDTO {

    @Schema(description = "회원가입 시 사용할 아이디 (5~20자의 영문, 숫자, _ 또는 - 포함 가능)")
    @NotBlank(message = "{error.validation('아이디')}", groups = First.class)
    @Pattern(regexp = "^[a-zA-Z0-9_-]{5,20}$", message = "{error.signup.id_pattern}", groups = Second.class)
    private String userId;

    @Schema(description = "회원가입 시 사용할 비밀번호 (대문자, 소문자, 숫자, 특수문자를 포함해야 함)")
    @NotBlank(message = "{error.validation('비밀번호')}", groups = Third.class)
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=\\[\\]{}:;\"'<>,.?/~`]).{8,16}$", message = "{error.signup.pwd_pattern}", groups = Fourth.class)
    private String userPwd;

    @Schema(description = "회원의 이름 (2~50자)")
    @NotBlank(message = "{error.validation('이름')}", groups = Fifth.class)
    @Size(min = 2, max = 50, message = "{error.length('이름', 2, 50)}", groups = Sixth.class)
    private String userNm;

    @Schema(description = "회원가입 시 사용할 이메일 주소")
    @NotBlank(message = "{error.validation('이메일')}", groups = Seventh.class)
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "{error.signup.email_pattern}", groups = Eighth.class)
    private String userEmail;

    public UserEntity toEntity(String pwdEncrypt) {
        return UserEntity.builder()
                    .role(RoleEntity.builder().roleSeq(3).build())
                    .userId(userId)
                    .userPwd(pwdEncrypt)
                    .userNm(userNm)
                    .userEmail(userEmail)
                    .activeYn(ActiveYn.Y)
                    .signupDt(LocalDateTime.now())
                    .lastPwdDt(LocalDateTime.now())
                    .build();
    }
}
