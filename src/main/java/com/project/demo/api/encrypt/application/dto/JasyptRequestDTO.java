package com.project.demo.api.encrypt.application.dto;

import com.project.demo.api.encrypt.value.JasyptAlgorithm;
import com.project.demo.common.validation.ValidationGroups.First;
import com.project.demo.common.validation.ValidationGroups.Second;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Jasypt 암호화/복호화 처리를 위한 DTO")
public class JasyptRequestDTO {

    @Schema(description = "암호화/복호화 처리 대상 텍스트")
    @NotBlank(message = "{error.validation('평문 문자열')}", groups = First.class)
    private String targetText;

    @Schema(description = "암호화/복호화 비밀번호")
    @NotBlank(message = "{error.validation('비밀번호')}", groups = Second.class)
    private String secretKey;

    @Enumerated(EnumType.STRING)
    private JasyptAlgorithm algorithm;
}
