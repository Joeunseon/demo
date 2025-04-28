package com.project.demo.api.role.infrastructure;

import java.util.List;

import com.project.demo.api.role.application.dto.RoleDetailDTO;
import com.project.demo.api.role.application.dto.RoleListDTO;
import com.project.demo.api.role.application.dto.RoleRequestDTO;
import com.project.demo.api.role.domain.RoleEntity;

public interface RoleRepositoryCustom {

    Long countBySearch(RoleRequestDTO dto);

    List<RoleListDTO> findBySearch(RoleRequestDTO dto);

    RoleDetailDTO findByRoleSeq(Integer roleSeq);

    Long updateById(RoleEntity entity);

    Long softDelete(Integer roleSeq, Long userSeq);
}
