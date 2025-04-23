package com.project.demo.api.role.presentation;

import java.util.List;
import java.util.Map;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.demo.api.role.application.MenuRoleService;
import com.project.demo.api.role.application.RoleService;
import com.project.demo.api.role.application.dto.MenuRoleTreeDTO;
import com.project.demo.api.role.application.dto.RoleDetailDTO;
import com.project.demo.api.role.application.dto.RoleRequestDTO;
import com.project.demo.common.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;

@Tag(name = "Role API", description = "권한 API")
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class RoleRestController {

    private final RoleService roleService;
    private final MenuRoleService menuRoleService;

    @GetMapping("/roles")
    @Operation(summary = "권한 목록 조회 API", description = "검색조건을 전달 받아 권한 목록을 조회합니다.")
    public ApiResponse<Map<String, Object>> findAll(@Parameter(description = "권한 목록 조회 진행을 위한 DTO") RoleRequestDTO dto) {

        return roleService.findAll(dto);
    }

    @GetMapping("/role/{roleSeq}")
    @Operation(summary = "권한 상세 조회 API", description = "권한 SEQ을 전달 받아 메뉴 상세를 조회합니다.")
    public ApiResponse<RoleDetailDTO> findById(@Parameter(description = "조회할 권한 SEQ") @PathVariable("roleSeq") @Min(value = 0, message = "{error.request}") Integer roleSeq) {

        return roleService.findById(roleSeq);
    }

    @GetMapping("/role/{roleSeq}/menus")
    @Operation(summary = "권한 상세 조회 API", description = "권한 SEQ을 전달 받아 권한별 메뉴 목록을 조회합니다.")
    public ApiResponse<List<MenuRoleTreeDTO>> findMenusById(@Parameter(description = "조회할 권한 SEQ") @PathVariable("roleSeq") @Min(value = 0, message = "{error.request}") Integer roleSeq) {

        return menuRoleService.findMenusById(roleSeq);
    }
}
