package com.project.demo.api.menu.infrastructure;

import java.util.List;

import com.project.demo.api.menu.application.dto.MenuListDTO;
import com.project.demo.api.menu.application.dto.MenuRequestDTO;
import com.project.demo.api.menu.domain.MenuEntity;

public interface MenuRepositoryCustom {

    Long countBySearch(MenuRequestDTO dto);

    List<MenuListDTO> findBySearch(MenuRequestDTO dto);

    List<MenuEntity> findAllForTreeView();
}
