package com.project.demo.api.menu.presentation;

import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.demo.api.menu.application.MenuService;
import com.project.demo.api.menu.application.dto.MenuCheckDuplicateDTO;
import com.project.demo.api.menu.application.dto.MenuChildrenDTO;
import com.project.demo.api.menu.application.dto.MenuCreateDTO;
import com.project.demo.api.menu.application.dto.MenuDetailDTO;
import com.project.demo.api.menu.application.dto.MenuRequestDTO;
import com.project.demo.api.menu.application.dto.MenuTreeDTO;
import com.project.demo.common.ApiResponse;
import com.project.demo.common.constant.CommonMsgKey;
import com.project.demo.common.util.MsgUtil;
import com.project.demo.common.validation.ValidationSequence;

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

    private final MsgUtil msgUtil;

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

    @GetMapping("/menus/tree")
    @Operation(summary = "메뉴 트리 API", description = "전체 메뉴를 계층 구조(트리)로 조회합니다.")
    public ApiResponse<List<MenuTreeDTO>> findAllAsTree() {

        return menuService.findAllAsTree();
    }

    @PostMapping("/menu")
    @Operation(summary = "메뉴 등록 API", description = "등록할 메뉴의 정보를 전달 받아 메뉴을 등록합니다.")
    public ApiResponse<Long> create(@Parameter(description = "메뉴 등록을 위한 DTO") @Validated(ValidationSequence.class) @RequestBody MenuCreateDTO dto) {

        return menuService.create(dto);
    }

    @GetMapping("/menu/check-duplicate")
    @Operation(summary = "메뉴 중복체크 API", description = "메뉴이름 혹은 메뉴URL을 전달하면 중복 여부를 확인합니다.")
    public ApiResponse<Void> checkDuplicate(@Parameter(description = "중복 확인 진행을 위한 DTO") MenuCheckDuplicateDTO dto) {

        if ( StringUtils.hasText(dto.getMenuNm()) )
            return menuService.checkDuplicateMenuNm(dto.getMenuNm().trim());

        if ( StringUtils.hasText(dto.getMenuUrl()))
            return menuService.checkDuplicateMenuUrl(dto.getMenuUrl().trim());

        return ApiResponse.error(msgUtil.getMessage(CommonMsgKey.FAILED_REQUEST.getKey()));
    }
}
