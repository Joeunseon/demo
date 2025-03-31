package com.project.demo.api.menu.infrastructure;

import java.util.List;

import com.project.demo.api.menu.application.dto.MenuListDTO;
import com.project.demo.api.menu.application.dto.MenuRequestDTO;

public interface MenuRepositoryCustom {

    Long countBySearch(MenuRequestDTO dto);

    List<MenuListDTO> findBySearch(MenuRequestDTO dto);
}
