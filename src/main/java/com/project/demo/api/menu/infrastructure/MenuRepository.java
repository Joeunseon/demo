package com.project.demo.api.menu.infrastructure;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.demo.api.menu.domain.MenuEntity;
import com.project.demo.api.menu.value.ActiveYn;

public interface MenuRepository extends JpaRepository<MenuEntity, Long> {

    List<MenuEntity> findByActiveYn(ActiveYn activeYn);
}
