package com.project.demo.api.encrypt.application.dto;

import com.project.demo.common.validation.ValidationGroups.First;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "BCrypt 암호화 처리를 위한 DTO")
public class BcryptRequestDTO {

    @Schema(description = "암호화 처리 대상 텍스트")
    @NotBlank(message = "{error.validation('평문 문자열')}", groups = First.class)
    private String targetText;
}
