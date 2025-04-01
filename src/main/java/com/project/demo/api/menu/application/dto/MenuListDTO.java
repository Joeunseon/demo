package com.project.demo.api.menu.application.dto;

import com.project.demo.api.menu.value.ActiveYn;
import com.project.demo.api.menu.value.MenuType;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
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

    @Schema(description = "메뉴 유형")
    @Enumerated(EnumType.STRING)
    private MenuType menuType;

    @Schema(description = "활성화 여부")
    @Enumerated(EnumType.STRING)
    private ActiveYn activeYn;

    @Schema(description = "메뉴 유형 설명")
    private String menuTypeDesc;

    @Builder
    public MenuListDTO(Long rownum, Long menuSeq, String menuNm, String menuUrl, Integer menuOrder, MenuType menuType, ActiveYn activeYn) {

        this.rownum = rownum;
        this.menuSeq = menuSeq;
        this.menuNm = menuNm;
        this.menuUrl = menuUrl;
        this.menuOrder = menuOrder;
        this.menuType = menuType;
        this.activeYn = activeYn;
        this.menuTypeDesc = menuType.getDescription();
    }
}
