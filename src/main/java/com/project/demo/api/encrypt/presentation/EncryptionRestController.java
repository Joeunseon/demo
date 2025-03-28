package com.project.demo.api.encrypt.presentation;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.demo.api.encrypt.application.EncryptionService;
import com.project.demo.api.encrypt.application.dto.Base64RequestDTO;
import com.project.demo.api.encrypt.application.dto.BcryptRequestDTO;
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

    @PostMapping("/jasypt/decrypt")
    @Operation(summary = "Jasypt 복호화 API", description = "Jasypt 정보를 전달 받아 복호화를 진행합니다.")
    public ApiResponse<String> jasyptDecrypt(@Parameter(description = "Jasypt 복호화를 위한 DTO") @Validated(ValidationSequence.class) @RequestBody JasyptRequestDTO dto) {

        return encryptionService.jasyptDecrypt(dto);
    }

    @PostMapping("/bcrypt/encrypt")
    @Operation(summary = "BCrypt 암호화 API", description = "BCrypt 정보를 전달 받아 암호화를 진행합니다.")
    public ApiResponse<String> bcryptEncrypt(@Parameter(description = "BCrypt 암호화를 위한 DTO") @Validated(ValidationSequence.class) @RequestBody BcryptRequestDTO dto) {

        return encryptionService.bcryptEncrypt(dto);
    }

    @PostMapping("/bcrypt/matches")
    @Operation(summary = "BCrypt 일치확인 API", description = "BCrypt 정보를 전달 받아 일치 확인을 진행합니다.")
    public ApiResponse<Void> bcryptMatches(@Parameter(description = "BCrypt 일치 확인을 위한 DTO") @Validated(ValidationSequence.class) @RequestBody BcryptRequestDTO dto) {

        return encryptionService.bcryptMatches(dto);
    }

    @PostMapping("/base64/encode")
    @Operation(summary = "base64 인코딩 API", description = "Base64 정보를 전달 받아 인코딩을 진행합니다.")
    public ApiResponse<String> base64Encode(@Parameter(description = "Base64 인코딩을 위한 DTO") @Validated(ValidationSequence.class) @RequestBody Base64RequestDTO dto) {

        return encryptionService.base64Encode(dto);
    }

    @PostMapping("/base64/decode")
    @Operation(summary = "base64 디코딩 API", description = "Base64 정보를 전달 받아 디코딩을 진행합니다.")
    public ApiResponse<String> base64Decode(@Parameter(description = "Base64 디코딩을 위한 DTO") @Validated(ValidationSequence.class) @RequestBody Base64RequestDTO dto) {

        return encryptionService.base64Decode(dto);
    }
}
