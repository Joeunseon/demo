package com.project.demo.api.role.application.dto;

import com.project.demo.api.menu.value.MenuType;
import com.project.demo.api.role.domain.MenuRoleEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuRoleDTO {

    private Long menuRoleSeq;

    private Long menuSeq;

    private Integer roleSeq;

    private String menuNm;

    private String menuUrl;

    private MenuType menuType;

    private String menuTypeNm;


    public MenuRoleDTO(MenuRoleEntity entity) {
        this.menuRoleSeq = entity.getMenuRoleSeq();
        this.menuSeq = entity.getMenu().getMenuSeq();
        this.roleSeq = entity.getRole().getRoleSeq();
        this.menuNm = entity.getMenu().getMenuNm();
        this.menuUrl = entity.getMenu().getMenuUrl();
        this.menuType = entity.getMenu().getMenuType();
        this.menuTypeNm = entity.getMenu().getMenuType().getDescription();
    }
}
