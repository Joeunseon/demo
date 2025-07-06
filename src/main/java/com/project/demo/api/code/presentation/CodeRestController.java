package com.project.demo.api.code.presentation;

import java.util.Map;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.demo.api.code.application.CodeService;
import com.project.demo.api.code.application.dto.CodeRequestDTO;
import com.project.demo.common.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Code API", description = "코드 API")
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CodeRestController {

    private final CodeService codeService;

    @GetMapping("/codes")
    @Operation(summary = "코드 목록 조회 API", description = "검색조건을 전달 받아 코드 목록을 조회합니다.")
    public ApiResponse<Map<String, Object>> findAll(@Parameter(description = "코드 목록 조회 진행을 위한 DTO") CodeRequestDTO dto) {
        
        return codeService.findAll(dto);
    }
}
