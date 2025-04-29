package com.project.demo.api.user.presentation;

import java.util.Map;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.demo.api.user.application.UserService;
import com.project.demo.api.user.application.dto.UserRequestDTO;
import com.project.demo.common.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "User API", description = "사용자 API")
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserRestController {

    private final UserService userService;

    @GetMapping("/users")
    @Operation(summary = "사용자 목록 조회 API", description = "검색조건을 전달 받아 사용자 목록을 조회합니다.")
    public ApiResponse<Map<String, Object>> findAll(@Parameter(description = "사용자 목록 조회 진행을 위한 DTO") UserRequestDTO dto) {

        return userService.findAll(dto);
    }
}
