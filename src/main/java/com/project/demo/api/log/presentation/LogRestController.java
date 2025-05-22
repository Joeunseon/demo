package com.project.demo.api.log.presentation;

import java.util.Map;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.demo.api.log.application.ErrLogService;
import com.project.demo.api.log.application.dto.LogDetailDTO;
import com.project.demo.api.log.application.dto.LogRequestDTO;
import com.project.demo.common.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;

@Tag(name = "Log API", description = "로그 API")
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class LogRestController {

    private final ErrLogService errLogService;

    @GetMapping("/logs")
    @Operation(summary = "로그 목록 조회 API", description = "검색조건을 전달 받아 로그 목록을 조회합니다.")
    public ApiResponse<Map<String, Object>> findAll(@Parameter(description = "로그 목록 조회 진행을 위한 DTO") LogRequestDTO dto) {

        return errLogService.findAll(dto);
    }

    @GetMapping("/log/{logSeq}")
    @Operation(summary = "로그 상세 조회 API", description = "로그 SEQ를 전달 받아 로그 상세를 조회합니다.")
    public ApiResponse<LogDetailDTO> findById(@Parameter(description = "조회할 로그 SEQ") @PathVariable("logSeq") @Min(value = 0, message = "{error.request}") Long logSeq) {

        return errLogService.findById(logSeq);
    }
}
