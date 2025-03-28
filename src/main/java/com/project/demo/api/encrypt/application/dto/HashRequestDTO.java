package com.project.demo.api.encrypt.application.dto;

import com.project.demo.api.encrypt.value.HashAlgorithm;
import com.project.demo.common.validation.ValidationGroups.First;

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
@Schema(description = "Hash 암호화/일치확인 처리를 위한 DTO")
public class HashRequestDTO {

    @Schema(description = "암호화/일치확인 처리 대상 텍스트")
    @NotBlank(message = "{error.validation('평문 문자열')}", groups = First.class)
    private String targetText;

    @Enumerated(EnumType.STRING)
    private HashAlgorithm algorithm;

    @Schema(description = "일치확인 처리 대상 해시값")
    private String targetHash;
}
