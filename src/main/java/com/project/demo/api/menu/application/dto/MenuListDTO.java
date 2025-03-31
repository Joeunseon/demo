package com.project.demo.api.menu.application.dto;

import com.project.demo.api.menu.value.ActiveYn;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "메뉴 목록 조회를 위한 DTO")
public class MenuListDTO {

    @Schema(description = "메뉴 순번")
    private Long rownum;

    @Schema(description = "메뉴 SEQ")
    private Long menuSeq;

    @Schema(description = "메뉴 이름")
    private String menuNm;

    @Schema(description = "메뉴 URL")
    private String menuUrl;

    @Schema(description = "메뉴 순서")
    private Integer menuOrder;

    @Schema(description = "활성화 여부")
    @Enumerated(EnumType.STRING)
    private ActiveYn activeYn;
}
