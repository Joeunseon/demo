package com.project.demo.api.user.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "중복 확인 진행을 위한 DTO")
public class SignupCheckDuplicateDTO {

    @Schema(description = "중복 확인을 진행할 아이디")
    @Pattern(regexp = "^[a-zA-Z0-9_-]{5,20}$", message = "{error.signup.id_pattern}") 
    private String userId;

    @Schema(description = "중복 확인을 진행할 이메일")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "{error.signup.email_pattern}") 
    private String userEmail;
}
