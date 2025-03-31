package com.project.demo.api.menu.presentation;

import java.util.Map;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.demo.api.menu.application.MenuService;
import com.project.demo.api.menu.application.dto.MenuRequestDTO;
import com.project.demo.common.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Menu API", description = "메뉴 API")
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MenuRestController {

    private final MenuService menuService;

    @GetMapping("/menus")
    @Operation(summary = "메뉴 목록 조회 API", description = "검색조건을 전달 받아 메뉴 목록을 조회합니다.")
    public ApiResponse<Map<String, Object>> findAll(@Parameter(description = "메뉴 목록 조회 진행을 위한 DTO") MenuRequestDTO dto) {

        return menuService.findAll(dto);
    }
}
