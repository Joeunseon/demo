package com.project.demo.api.menu.application.dto;

import java.util.List;

import org.springframework.util.StringUtils;

import com.project.demo.api.menu.domain.MenuEntity;
import com.project.demo.api.menu.value.ActiveYn;
import com.project.demo.api.menu.value.MenuType;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "하위 메뉴 조회를 위한 DTO")
public class MenuChildrenDTO {

    @Schema(description = "메뉴 SEQ")
    private Long menuSeq;

    @Schema(description = "메뉴 이름")
    private String menuNm;

    @Schema(description = "메뉴 URL")
    private String menuUrl;

    @Schema(description = "부모 SEQ")
    private Long parentSeq;

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

    @Schema(description = "하위 메뉴 리스트")
    private List<MenuChildrenDTO> children;

    public static MenuChildrenDTO of(MenuEntity menu) {

        return MenuChildrenDTO.builder()
                                .menuSeq(menu.getMenuSeq())
                                .menuNm(menu.getMenuNm())
                                .menuUrl(StringUtils.hasText(menu.getMenuUrl()) ? menu.getMenuUrl() : "")
                                .parentSeq(menu.getParentSeq())
                                .menuOrder(menu.getMenuOrder())
                                .menuType(menu.getMenuType())
                                .activeYn(menu.getActiveYn())
                                .menuTypeDesc(menu.getMenuType().getDescription())
                                .children(null)
                                .build();
    }

    public void ofChildren(List<MenuChildrenDTO> children) {

        this.children = children;
    }
}