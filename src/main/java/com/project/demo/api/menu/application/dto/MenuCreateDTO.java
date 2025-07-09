package com.project.demo.api.menu.application.dto;

import com.project.demo.api.menu.value.ActiveYn;
import com.project.demo.api.menu.value.MenuType;
import com.project.demo.common.BaseDTO;
import com.project.demo.common.validation.ValidationGroups.Fifth;
import com.project.demo.common.validation.ValidationGroups.First;
import com.project.demo.common.validation.ValidationGroups.Fourth;
import com.project.demo.common.validation.ValidationGroups.Second;
import com.project.demo.common.validation.ValidationGroups.Sixth;
import com.project.demo.common.validation.ValidationGroups.Third;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "메뉴 등록 진행을 위한 DTO")
public class MenuCreateDTO extends BaseDTO {

    @Schema(description = "메뉴 이름")
    @NotBlank(message = "{error.validation('메뉴 이름')}", groups = First.class)
    @Size(min = 1, max = 100, message = "{error.length('메뉴 이름', 1, 100)}", groups = Second.class)
    private String menuNm;

    @Schema(description = "메뉴 URL")
    @Size(min = 1, max = 200, message = "{error.length('메뉴 URL', 1, 200)}", groups = Third.class)
    private String menuUrl;

    @Schema(description = "부모 SEQ")
    @NotNull(message = "{error.validation('상위 메뉴')}", groups = Fourth.class)
    private Long parentSeq;

    @Schema(description = "메뉴 유형")
    @NotNull(message = "{error.validation('메뉴 유형')}", groups = Fifth.class)
    @Enumerated(EnumType.STRING)
    private MenuType menuType;

    @Schema(description = "활성화 여부")
    @NotNull(message = "{error.validation('활성화 여부')}", groups = Sixth.class)
    @Enumerated(EnumType.STRING)
    private ActiveYn activeYn;
}
