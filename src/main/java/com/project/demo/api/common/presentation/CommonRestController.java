package com.project.demo.api.common.presentation;

import java.util.List;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.demo.api.code.application.CodeService;
import com.project.demo.api.common.application.EnumService;
import com.project.demo.api.common.application.dto.SelectBoxDTO;
import com.project.demo.api.role.application.RoleService;
import com.project.demo.common.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Common API", description = "공통 API")
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommonRestController {

    private final EnumService enumService;
    private final CodeService codeService;
    private final RoleService roleService;

    @GetMapping("/selectbox/enum")
    @Operation(summary = "셀렉트박스 조회 API (enum)", description = "enum 경로를 전달 받아 옵션 목록을 조회합니다.")
    public ApiResponse<List<SelectBoxDTO>> getEnum(@Parameter(description = "select box option 조회를 위한 enum 경로") @RequestParam(value = "enumFullPath") String enumFullPath) {

        return enumService.getEnum(enumFullPath);
    }

    @GetMapping("/selectbox/code")
    @Operation(summary = "셀렉트박스 조회 API (code)", description = "그룹코드를 전달 받아 옵션 목록을 조회합니다.")
    public ApiResponse<List<SelectBoxDTO>> getCode(@Parameter(description = "select box option 조회를 위한 그룹코드") @RequestParam(value = "grpCd") String grpCd) {

        return codeService.getCode(grpCd);
    }

    @GetMapping("/selectbox/role")
    @Operation(summary = "셀렉트박스 조회 API (role)", description = "권한 옵션 목록을 조회합니다.")
    public ApiResponse<List<SelectBoxDTO>> getRole() {

        return roleService.getRole();
    }
}
