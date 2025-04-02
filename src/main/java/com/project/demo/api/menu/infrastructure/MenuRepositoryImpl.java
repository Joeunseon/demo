package com.project.demo.api.menu.infrastructure;

import java.util.List;

import org.springframework.util.StringUtils;

import com.project.demo.api.menu.application.dto.MenuListDTO;
import com.project.demo.api.menu.application.dto.MenuRequestDTO;
import com.project.demo.api.menu.domain.MenuEntity;
import com.project.demo.api.menu.domain.QMenuEntity;
import com.project.demo.api.menu.value.ActiveYn;
import com.project.demo.api.menu.value.MenuType;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MenuRepositoryImpl implements MenuRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public Long countBySearch(MenuRequestDTO dto) {

        QMenuEntity menu = QMenuEntity.menuEntity;
        QMenuEntity subMenu = new QMenuEntity("subMenu");
        QMenuEntity minSubMenu = new QMenuEntity("minSubMenu");
        BooleanBuilder builder = new BooleanBuilder();

        var subQuery = JPAExpressions
            .select(subMenu.menuUrl)
            .from(subMenu)
            .where(subMenu.parentSeq.eq(menu.menuSeq)
                .and(subMenu.menuOrder.eq(
                    JPAExpressions.select(minSubMenu.menuOrder.min())
                        .from(minSubMenu)
                        .where(minSubMenu.parentSeq.eq(subMenu.parentSeq)
                            .and(minSubMenu.activeYn.eq(ActiveYn.Y)))
                ))
            );

        StringExpression menuUrlExpression = Expressions.stringTemplate(
                                                    "case when {0} is null then ({1}) else {0} end",
                                                    menu.menuUrl,
                                                    subQuery
                                                );

        //builder.and(menu.menuType.eq(MenuType.MENU));
        builder.and(menu.parentSeq.eq(1L));

        if ( StringUtils.hasText(dto.getSearchKeyword()) ) {
            builder.and(menu.menuNm.like("%" + dto.getSearchKeyword() + "%")
                    .or(menuUrlExpression.like("%" + dto.getSearchKeyword() + "%")));
        }

        return queryFactory.select(menu.count())
                            .from(menu)
                            .where(builder)
                            .fetchFirst();
    }

    public List<MenuListDTO> findBySearch(MenuRequestDTO dto) {

        QMenuEntity menu = QMenuEntity.menuEntity;
        QMenuEntity subMenu = new QMenuEntity("subMenu");
        QMenuEntity minSubMenu = new QMenuEntity("minSubMenu");
        BooleanBuilder builder = new BooleanBuilder();

        dto.calculateOffSet();

        var subQuery = JPAExpressions
                        .select(subMenu.menuUrl)
                        .from(subMenu)
                        .where(subMenu.parentSeq.eq(menu.menuSeq)
                            .and(subMenu.menuOrder.eq(
                                    JPAExpressions.select(minSubMenu.menuOrder.min())
                                                .from(minSubMenu)
                                                .where(minSubMenu.parentSeq.eq(subMenu.parentSeq)
                                                .and(minSubMenu.activeYn.eq(ActiveYn.Y)))
                                    )
                                ));

        StringExpression menuUrlExpression = Expressions.stringTemplate(
                                                    "case when {0} is null then ({1}) else {0} end",
                                                    menu.menuUrl,
                                                    subQuery
                                                );

        //builder.and(menu.menuType.eq(MenuType.MENU));
        builder.and(menu.parentSeq.eq(1L));

        if ( StringUtils.hasText(dto.getSearchKeyword()) ) {
            builder.and(menu.menuNm.like("%" + dto.getSearchKeyword() + "%")
                    .or(menuUrlExpression.like("%" + dto.getSearchKeyword() + "%")));
        }

        return queryFactory.select(Projections.constructor(MenuListDTO.class
                                    , Expressions.numberTemplate(Long.class, "ROW_NUMBER() OVER(ORDER BY {0} ASC)", menu.menuOrder)
                                    , menu.menuSeq
                                    , menu.menuNm
                                    , menuUrlExpression
                                    , menu.menuOrder
                                    , menu.menuType
                                    , menu.activeYn))
                            .from(menu)
                            .where(builder)
                            .orderBy(Expressions.numberTemplate(Long.class, "ROW_NUMBER() OVER(ORDER BY {0} ASC)", menu.menuOrder).asc())
                            .limit(dto.getPageScale())
                            .offset(dto.getOffSet())
                            .fetch();
    }

    public List<MenuEntity> findAllForTreeView() {

        QMenuEntity menu = QMenuEntity.menuEntity;

        return queryFactory.select(menu)
                            .from(menu)
                            .where(menu.menuLevel.lt(4).and(menu.menuType.in(MenuType.MENU, MenuType.PAGE, MenuType.TOOL)))
                            .orderBy(menu.menuOrder.asc(), menu.parentSeq.asc(), menu.menuOrder.asc())
                            .fetch();
    }
}
