package com.project.demo.api.user.application.dto;

import com.project.demo.api.user.value.ActiveYn;
import com.project.demo.api.user.value.CustomCondition;
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
@Schema(description = "사용자 검색 조회를 위한 DTO")
public class UserRequestDTO extends BaseDTO {

    @Schema(description = "사용자 검색을 위한 커스텀 검색 조건")
    @Enumerated(EnumType.STRING)
    private CustomCondition customCondition;

    @Schema(description = "사용자 검색을 위한 권한 SEQ")
    private Integer roleSeq;

    @Schema(description = "사용자 검색을 위한 활성화 여부")
    @Enumerated(EnumType.STRING)
    private ActiveYn activeYn;
}
