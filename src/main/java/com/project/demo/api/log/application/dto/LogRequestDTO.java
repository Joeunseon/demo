package com.project.demo.api.log.application.dto;

import com.project.demo.api.log.value.CustomCondition;
import com.project.demo.api.log.value.ErrLevel;
import com.project.demo.api.log.value.RequestMethod;
import com.project.demo.api.log.value.ResolvedStat;
import com.project.demo.common.BaseDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "로그 검색 조회를 위한 DTO")
public class LogRequestDTO extends BaseDTO {

    @Schema(description = "로그 검색을 위한 커스텀 검색 조건")
    @Enumerated(EnumType.STRING)
    private CustomCondition customCondition;

    @Schema(description = "에러 심각도")
    @Enumerated(EnumType.STRING)
    private ErrLevel errLevel;

    @Schema(description = "해결 상태")
    @Enumerated(EnumType.STRING)
    private ResolvedStat ResolvedStat;

    @Schema(description = "발생 Method")
    @Enumerated(EnumType.STRING)
    private RequestMethod requestMethod;
}
