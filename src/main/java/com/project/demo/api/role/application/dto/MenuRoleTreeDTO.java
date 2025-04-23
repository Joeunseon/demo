package com.project.demo.api.role.application.dto;

import java.util.ArrayList;
import java.util.List;

import com.project.demo.api.menu.value.MenuType;
import com.project.demo.api.role.domain.MenuRoleEntity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "메뉴와 역할 매핑 정보를 담은 계층 구조 DTO")
public class MenuRoleTreeDTO {

    @Schema(description = "메뉴-역할 매핑 고유 식별자")
    private Long menuRoleSeq;

    @Schema(description = "메뉴 고유 식별자")
    private Long menuSeq;
    
    @Schema(description = "역할(Role) 고유 식별자")
    private Integer roleSeq;

    @Schema(description = "상위 메뉴 고유 식별자")
    private Long parentSeq;

    @Schema(description = "메뉴 이름")
    private String menuNm;

    @Schema(description = "메뉴 URL")
    private String menuUrl;

    @Schema(description = "메뉴 타입 (PAGE, CREATE, ... 등)")
    private MenuType menuType;

    @Schema(description = "메뉴 타입 이름 (페이지, 등록, ... 등)")
    private String menuTypeNm;

    @Schema(description = "하위 메뉴를 담은 DTO 리스트")
    private List<MenuRoleTreeDTO> children = new ArrayList<>();

    public MenuRoleTreeDTO(MenuRoleEntity entity) {
        this.menuRoleSeq = entity.getMenuRoleSeq();
        this.menuSeq = entity.getMenu().getMenuSeq();
        this.roleSeq = entity.getRole().getRoleSeq();
        this.parentSeq = entity.getMenu().getParentSeq();
        this.menuNm = entity.getMenu().getMenuNm();
        this.menuUrl = entity.getMenu().getMenuUrl();
        this.menuType = entity.getMenu().getMenuType();
        this.menuTypeNm = entity.getMenu().getMenuType().getDescription();
    }
}
