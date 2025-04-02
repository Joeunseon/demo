package com.project.demo.api.menu.infrastructure;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.demo.api.menu.domain.MenuEntity;
import com.project.demo.api.menu.value.ActiveYn;

@Repository
public interface MenuRepository extends JpaRepository<MenuEntity, Long>, MenuRepositoryCustom {

    List<MenuEntity> findByActiveYn(ActiveYn activeYn);

    List<MenuEntity> findByParentSeqOrderByMenuOrderAsc(Long parentSeq);

    List<MenuEntity> findAllByOrderByMenuLevelAscParentSeqAscMenuOrderAsc();
}
