package com.project.demo.api.menu.presentation;

import java.util.List;
import java.util.Map;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.demo.api.menu.application.MenuService;
import com.project.demo.api.menu.application.dto.MenuChildrenDTO;
import com.project.demo.api.menu.application.dto.MenuDetailDTO;
import com.project.demo.api.menu.application.dto.MenuRequestDTO;
import com.project.demo.common.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
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

    @GetMapping("/menu/{menuSeq}")
    @Operation(summary = "메뉴 상세 조회 API", description = "메뉴 SEQ을 전달 받아 메뉴 상세를 조회합니다.")
    public ApiResponse<MenuDetailDTO> findById(@Parameter(description = "조회할 메뉴 SEQ") @PathVariable("menuSeq") @Min(value = 0, message = "{error.request}") Long menuSeq) {

        return menuService.findById(menuSeq);
    }

    @GetMapping("/menus/{parentSeq}/children")
    @Operation(summary = "하위 메뉴 조회 API", description = "메뉴 SEQ을 전달 받아 하위 메뉴를 조회합니다.")
    public ApiResponse<List<MenuChildrenDTO>> findAllByParentId(@Parameter(description = "조회할 메뉴 SEQ") @PathVariable("parentSeq") @Min(value = 0, message = "{error.request}") Long parentSeq) {

        return menuService.findAllByParentId(parentSeq);
    }
}
