package com.project.demo.api.role.infrastructure;

import java.util.List;

import com.project.demo.api.menu.application.dto.MenuDisplayDTO;
import com.project.demo.api.menu.application.dto.QMenuDisplayDTO;
import com.project.demo.api.menu.domain.QMenuEntity;
import com.project.demo.api.menu.value.ActiveYn;
import com.project.demo.api.menu.value.MenuType;
import com.project.demo.api.role.domain.QMenuRoleEntity;
import com.project.demo.common.constant.DelYn;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MenuRoleRepositoryImpl implements MenuRoleRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<MenuDisplayDTO> findDisplayMenus(Integer roleSeq) {

        QMenuRoleEntity menuRole = QMenuRoleEntity.menuRoleEntity;
        QMenuEntity menu = QMenuEntity.menuEntity;
        QMenuEntity subMenu = new QMenuEntity("subMenu");
        QMenuEntity minSubMenu = new QMenuEntity("minSubMenu");

        var subQuery = JPAExpressions
                        .select(subMenu.menuUrl)
                        .from(subMenu)
                        .where(subMenu.parentSeq.eq(menu.menuSeq)
                            .and(subMenu.activeYn.eq(ActiveYn.Y))
                            .and(subMenu.menuType.eq(MenuType.PAGE))
                            .and(subMenu.menuOrder.eq(
                                    JPAExpressions.select(minSubMenu.menuOrder.min())
                                                .from(minSubMenu)
                                                .where(minSubMenu.parentSeq.eq(subMenu.parentSeq)
                                                .and(minSubMenu.activeYn.eq(ActiveYn.Y))
                                                .and(minSubMenu.menuType.eq(MenuType.PAGE)))
                                    )
                                ));

        var menuUrlExpression = new CaseBuilder()
                                        .when(menu.menuUrl.isNull())
                                        .then(subQuery)
                                        .otherwise(menu.menuUrl);

        return queryFactory
                .select(new QMenuDisplayDTO(menu.menuSeq, menu.menuNm, menuUrlExpression, menu.parentSeq))
                .from(menuRole)
                .join(menuRole.menu, menu)
                .where(menuRole.role.roleSeq.eq(roleSeq)
                    .and(menuRole.delYn.eq(DelYn.N))
                    .and(menu.activeYn.eq(ActiveYn.Y))
                    .and(menu.menuType.eq(MenuType.PAGE))
                    .and(menu.parentSeq.eq(1L)))
                .orderBy(menu.menuOrder.asc())
                .fetch();
    }
}
