package com.project.demo.api.log.application.dto;

import java.util.List;

import com.project.demo.common.BaseDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(description = "로그 해결 상태 수정을 위한 DTO")
public class LogResolveDTO extends BaseDTO {

    @Schema(description = "로그 SEQ")
    private Long logSeq;

    @Schema(description = "로그 SEQ 리스트")
    private List<Long> logSeqList;
}
