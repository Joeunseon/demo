package com.project.demo.api.file.presentation;

import java.util.List;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.project.demo.api.file.application.FileService;
import com.project.demo.api.file.application.dto.FileDtlListDTO;
import com.project.demo.common.ApiResponse;
import com.project.demo.common.BaseDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;

@Tag(name = "File API", description = "파일 API")
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class FileRestController {

    private final FileService fileService;

    @GetMapping("/file/{fileSeq}")
    @Operation(summary = "파일 목록 조회 API", description = "파일 SEQ를 전달 받아 파일 목록을 조회합니다.")
    public ApiResponse<List<FileDtlListDTO>> findById(@Parameter(description = "조회할 파일 SEQ") @PathVariable("fileSeq") @Min(value = 0, message = "{error.request}") Long fileSeq) {

        return fileService.findById(fileSeq);
    }

    @PostMapping("/file")
    @Operation(summary = "파일 등록(업로드) API", description = "등록할 파일의 정보를 전달 받아 파일을 등록(업로드)합니다.")
    public ApiResponse<Long> create(@RequestParam("file") MultipartFile[] files, BaseDTO dto) {

         return fileService.create(files, dto);
    }
}
