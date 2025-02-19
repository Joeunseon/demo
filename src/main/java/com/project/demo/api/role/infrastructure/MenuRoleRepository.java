package com.project.demo.api.role.infrastructure;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.demo.api.menu.value.ActiveYn;
import com.project.demo.api.role.domain.MenuRoleEntity;

public interface MenuRoleRepository extends JpaRepository<MenuRoleEntity, Long> {

    List<MenuRoleEntity> findByRole_RoleSeqAndMenu_ActiveYn(Long roleseq, ActiveYn activeYn);
}
