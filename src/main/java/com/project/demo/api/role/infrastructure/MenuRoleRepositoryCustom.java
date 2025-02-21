package com.project.demo.api.role.infrastructure;

import java.util.List;

import com.project.demo.api.menu.application.dto.MenuDisplayDTO;

public interface MenuRoleRepositoryCustom {

    List<MenuDisplayDTO> findDisplayMenus(Integer roleSeq);
}
