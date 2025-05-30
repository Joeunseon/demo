package com.project.demo.api.account.application.dto;

import com.project.demo.common.BaseDTO;
import com.project.demo.common.validation.ValidationGroups.First;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "비밀번호 일치 확인 진행을 위한 DTO")
public class AccountPasswordVerifyDTO extends BaseDTO {

    @Schema(description = "사용자 비밀번호")
    @NotBlank(message = "{error.validation('비밀번호')}", groups = First.class)
    private String userPwd;
}
