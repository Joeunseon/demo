package com.project.demo.api.role.infrastructure;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.project.demo.api.menu.value.ActiveYn;
import com.project.demo.api.role.domain.MenuRoleEntity;

@Repository
public interface MenuRoleRepository extends JpaRepository<MenuRoleEntity, Long> {

    @Query("SELECT mr FROM MenuRoleEntity mr JOIN FETCH mr.menu WHERE mr.role.roleSeq = :roleSeq AND mr.menu.activeYn = :activeYn")
    List<MenuRoleEntity> findByRole_RoleSeqAndMenu_ActiveYn(@Param("roleSeq") Integer roleSeq, @Param("activeYn") ActiveYn activeYn);

}
