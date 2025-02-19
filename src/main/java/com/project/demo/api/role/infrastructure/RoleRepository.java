package com.project.demo.api.role.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.demo.api.role.domain.RoleEntity;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

}
