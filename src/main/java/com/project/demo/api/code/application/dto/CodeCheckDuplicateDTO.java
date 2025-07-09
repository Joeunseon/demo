package com.project.demo.api.code.application.dto;

import com.project.demo.api.code.value.CodeType;
import com.project.demo.common.validation.ValidationGroups.First;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "중복 확인 진행을 위한 DTO")
public class CodeCheckDuplicateDTO {

    @Schema(description = "중복 확인을 진행할 코드")
    @Size(min = 1, max = 50, message = "{error.length('코드', 1, 50)}")
    private String cd;

    @Schema(description = "중복 확인을 진행할 코드명")
    @Size(min = 1, max = 100, message = "{error.length('코드명', 1, 100)}")
    private String cdNm;

    @Schema(description = "활성화 여부")
    @NotNull(message = "{error.validation('활성화 여부')}", groups = First.class)
    private CodeType codeType;
}
