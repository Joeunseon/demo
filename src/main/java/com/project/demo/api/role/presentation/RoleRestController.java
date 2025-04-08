package com.project.demo.api.role.presentation;

import java.util.Map;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.demo.api.role.application.RoleService;
import com.project.demo.api.role.application.dto.RoleRequestDTO;
import com.project.demo.common.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Role API", description = "권한 API")
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class RoleRestController {

    private final RoleService roleService;
    //private final MenuRoleService menuRoleService;

    @GetMapping("/roles")
    @Operation(summary = "권한 목록 조회 API", description = "검색조건을 전달 받아 권한 목록을 조회합니다.")
    public ApiResponse<Map<String, Object>> findAll(@Parameter(description = "권한 목록 조회 진행을 위한 DTO") RoleRequestDTO dto) {

        return roleService.findAll(dto);
    }
}
