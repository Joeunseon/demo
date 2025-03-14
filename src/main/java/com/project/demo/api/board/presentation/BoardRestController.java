package com.project.demo.api.board.presentation;

import java.util.Map;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.demo.api.board.application.BoardService;
import com.project.demo.api.board.application.dto.BoardCreateDTO;
import com.project.demo.api.board.application.dto.BoardRequestDTO;
import com.project.demo.common.ApiResponse;
import com.project.demo.common.validation.ValidationSequence;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Board API", description = "게시판 API")
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BoardRestController {

    private final BoardService boardService;

    @GetMapping("/boards")
    @Operation(summary = "게시판 목록 조회 API", description = "검색조건을 전달 받아 게시판 목록을 조회합니다.")
    public ApiResponse<Map<String, Object>> findAll(@Parameter(description = "게시판 목록 조회 진행을 위한 DTO") BoardRequestDTO dto) {

        return boardService.findAll(dto);
    }

    @PostMapping("/board")
    @Operation(summary = "게시글 등록 API", description = "등록할 게시글의 정보를 전달 받아 게시글을 등록합니다.")
    public ApiResponse<Long> create(@Parameter(description = "게시글 등록을 위한 DTO") @Validated(ValidationSequence.class) @RequestBody BoardCreateDTO dto) {

        return boardService.create(dto);
    }
}
