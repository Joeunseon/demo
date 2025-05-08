package com.project.demo.api.common.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "select box option 조회를 위한 DTO")
public class SelectBoxDTO {

    @Schema(description = "옵션 값")
    private String value;

    @Schema(description = "옵션 라벨")
    private String label;
}
