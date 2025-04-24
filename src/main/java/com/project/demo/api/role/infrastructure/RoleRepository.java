package com.project.demo.api.role.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.demo.api.role.domain.RoleEntity;
import com.project.demo.common.constant.DelYn;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long>, RoleRepositoryCustom {

    Integer countByRoleNmAndDelYn(String roleNm, DelYn delYn);
}
