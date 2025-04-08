package com.project.demo.api.role.infrastructure;

import java.util.List;

import com.project.demo.api.role.application.dto.RoleListDTO;
import com.project.demo.api.role.application.dto.RoleRequestDTO;

public interface RoleRepositoryCustom {

    Long countBySearch(RoleRequestDTO dto);

    List<RoleListDTO> findBySearch(RoleRequestDTO dto);
}
