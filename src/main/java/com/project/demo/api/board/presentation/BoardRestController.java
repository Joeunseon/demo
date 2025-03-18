package com.project.demo.api.board.presentation;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.demo.api.board.application.BoardService;
import com.project.demo.api.board.application.dto.BoardCreateDTO;
import com.project.demo.api.board.application.dto.BoardRequestDTO;
import com.project.demo.api.board.application.dto.BoardUpdateDTO;
import com.project.demo.common.ApiResponse;
import com.project.demo.common.BaseDTO;
import com.project.demo.common.validation.ValidationSequence;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.Min;
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

    @GetMapping("/board/{boardSeq}")
    @Operation(summary = "게시글 상세 조회 API", description = "게시글 SEQ를 전달 받아 게시글 상세를 조회합니다.")
    public ApiResponse<Map<String, Object>> findById(@Parameter(description = "조회할 게시글 SEQ") @PathVariable("boardSeq") @Min(value = 0, message = "{error.request}") Long boardSeq, HttpSession session, BaseDTO baseDTO) {

        ApiResponse<Void> viewCntResponse = boardService.updateViewCnt(boardSeq, session);
        if ( viewCntResponse.getStatus() != HttpStatus.OK )
            return ApiResponse.error(viewCntResponse.getStatus(), viewCntResponse.getMessage());

        return boardService.findById(boardSeq, baseDTO);
    }

    @PostMapping("/board")
    @Operation(summary = "게시글 등록 API", description = "등록할 게시글의 정보를 전달 받아 게시글을 등록합니다.")
    public ApiResponse<Long> create(@Parameter(description = "게시글 등록을 위한 DTO") @Validated(ValidationSequence.class) @RequestBody BoardCreateDTO dto) {

        return boardService.create(dto);
    }

    @DeleteMapping("/board/{boardSeq}")
    @Operation(summary = "게시글 삭제 API", description = "게시글 SEQ를 전달 받아 게시글 삭제를 진행합니다.")
    public ApiResponse<Void> delete(@Parameter(description = "삭제할 게시글 SEQ") @PathVariable("boardSeq") @Min(value = 0, message = "{error.request}") Long boardSeq, BaseDTO baseDTO) {

        return boardService.delete(boardSeq, baseDTO);
    }

    @PatchMapping("/board")
    @Operation(summary = "게시글 수정 API", description = "수정할 게시글의 정보를 전달 받아 수정을 진행합니다.")
    public ApiResponse<Long> update(@Parameter(description = "게시글 수정을 위한 DTO") @Validated(ValidationSequence.class) @RequestBody BoardUpdateDTO dto) {

        return boardService.update(dto);
    }
}
