package com.project.demo.api.encrypt.presentation;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.demo.api.encrypt.application.EncryptionService;
import com.project.demo.api.encrypt.application.dto.JasyptRequestDTO;
import com.project.demo.common.ApiResponse;
import com.project.demo.common.validation.ValidationSequence;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Encryption/Decryption API", description = "암호화/복호화 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class EncryptionRestController {

    private final EncryptionService encryptionService;

    @PostMapping("/jasypt/encrypt")
    @Operation(summary = "Jasypt 암호화 API", description = "Jasypt 정보를 전달 받아 암호화를 진행합니다.")
    public ApiResponse<String> jasyptEncrypt(@Parameter(description = "Jasypt 암호화를 위한 DTO") @Validated(ValidationSequence.class) @RequestBody JasyptRequestDTO dto) {

        return encryptionService.jasyptEncrypt(dto);
    }
}
