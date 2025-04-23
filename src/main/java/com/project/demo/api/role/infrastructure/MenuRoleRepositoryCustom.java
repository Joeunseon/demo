package com.project.demo.api.role.infrastructure;

import java.util.List;

import com.project.demo.api.menu.application.dto.MenuDisplayDTO;
import com.project.demo.api.role.domain.MenuRoleEntity;

public interface MenuRoleRepositoryCustom {

    List<MenuDisplayDTO> findDisplayMenus(Integer roleSeq);

    List<MenuRoleEntity> findMenusByRole(Integer roleSeq);
}
