package com.project.demo.api.menu.application.dto;

import org.springframework.util.StringUtils;

import com.project.demo.api.menu.domain.MenuEntity;
import com.project.demo.api.menu.value.ActiveYn;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "메뉴 상세 조회를 위한 DTO")
public class MenuDetailDTO {

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

    @Schema(description = "메뉴타입 설정")
    private String menuTypeDesc;

    public static MenuDetailDTO of(MenuEntity menu) {

        return MenuDetailDTO
                .builder()
                .menuSeq(menu.getMenuSeq())
                .menuNm(menu.getMenuNm())
                .menuUrl(StringUtils.hasText(menu.getMenuUrl()) ? menu.getMenuUrl() : "")
                .menuOrder(menu.getMenuOrder())
                .activeYn(menu.getActiveYn())
                .menuTypeDesc(menu.getMenuType().getDescription())
                .build();
    }
}
