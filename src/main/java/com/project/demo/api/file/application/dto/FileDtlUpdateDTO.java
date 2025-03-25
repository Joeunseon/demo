package com.project.demo.api.file.application.dto;

import com.project.demo.common.BaseDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(description = "파일 상세 수정 진행을 위한 DTO")
public class FileDtlUpdateDTO extends BaseDTO {

    @Schema(description = "파일 SEQ")
    private Long fileSeq;

    @Schema(description = "파일 상세 SEQ")
    private Long[] dtlSeq;
}
