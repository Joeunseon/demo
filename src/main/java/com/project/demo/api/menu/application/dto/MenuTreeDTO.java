package com.project.demo.api.menu.application.dto;

import java.util.ArrayList;
import java.util.List;

import com.project.demo.api.menu.domain.MenuEntity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "메뉴 트리 조회를 위한 DTO")
public class MenuTreeDTO {

    @Schema(description = "메뉴 SEQ")
    private Long menuSeq;

    @Schema(description = "메뉴 이름")
    private String menuNm;

    @Schema(description = "하위 메뉴 리스트")
    private List<MenuTreeDTO> children;

    public static MenuTreeDTO of(MenuEntity menu) {

        return MenuTreeDTO.builder()
                            .menuSeq(menu.getMenuSeq())
                            .menuNm(menu.getMenuNm())
                            .children(new ArrayList<>())
                            .build();
    }

    public void ofChildren(List<MenuTreeDTO> children) {

        this.children = children;
    }
}
