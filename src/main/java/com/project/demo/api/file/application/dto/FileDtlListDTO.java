package com.project.demo.api.file.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "파일 상세 리스트 조회를 위한 DTO")
public class FileDtlListDTO {

    @Schema(description = "파일 상세 SEQ")
    private Long dtlSeq;

    @Schema(description = "원본 파일 명")
    private String oriNm;

    @Schema(description = "파일 사이즈")
    private Long fileSize;

    @Schema(description = "파일 정렬 순서")
    private Integer fileOrder;
}
