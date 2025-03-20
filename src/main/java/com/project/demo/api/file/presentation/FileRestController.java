package com.project.demo.api.file.presentation;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.project.demo.api.file.application.FileService;
import com.project.demo.common.ApiResponse;
import com.project.demo.common.BaseDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "File API", description = "파일 API")
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class FileRestController {

    private final FileService fileService;

    @PostMapping("/file")
    @Operation(summary = "파일 등록(업로드) API", description = "등록할 파일의 정보를 전달 받아 파일을 등록(업로드)합니다.")
    public ApiResponse<Long> create(@RequestParam("file") MultipartFile[] files, BaseDTO dto) {

         return fileService.create(files, dto);
    }
}
