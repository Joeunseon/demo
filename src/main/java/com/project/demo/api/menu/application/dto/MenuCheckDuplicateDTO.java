package com.project.demo.api.menu.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
public class MenuCheckDuplicateDTO {

    @Schema(description = "중복 확인을 진행할 메뉴 이름")
    @Size(min = 1, max = 100, message = "{error.length('메뉴 이름', 1, 100)}")
    private String menuNm;

    @Schema(description = "중복 확인을 진행할 메뉴 URL")
    @Size(min = 1, max = 200, message = "{error.length('메뉴 URL', 1, 200)}")
    private String menuUrl;
}
