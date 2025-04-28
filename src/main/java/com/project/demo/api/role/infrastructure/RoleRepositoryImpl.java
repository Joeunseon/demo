package com.project.demo.api.role.infrastructure;

import java.util.List;

import org.springframework.util.StringUtils;

import com.project.demo.api.role.application.dto.RoleDetailDTO;
import com.project.demo.api.role.application.dto.RoleListDTO;
import com.project.demo.api.role.application.dto.RoleRequestDTO;
import com.project.demo.api.role.domain.QRoleEntity;
import com.project.demo.api.role.domain.RoleEntity;
import com.project.demo.api.user.domain.QUserEntity;
import com.project.demo.common.constant.DelYn;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RoleRepositoryImpl implements RoleRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public Long countBySearch(RoleRequestDTO dto) {

        QRoleEntity role = QRoleEntity.roleEntity;
        BooleanBuilder builder = new BooleanBuilder();

        builder.and(role.delYn.eq(DelYn.N));

        if ( StringUtils.hasText(dto.getSearchKeyword()) ) {
            builder.and(role.roleNm.like("%" + dto.getSearchKeyword() + "%").or(role.roleDesc.like("%" + dto.getSearchKeyword() + "%")));
        }

        return queryFactory.select(role.count())
                            .from(role)
                            .where(builder)
                            .fetchFirst();
    }

    public List<RoleListDTO> findBySearch(RoleRequestDTO dto) {

        QRoleEntity role = QRoleEntity.roleEntity;
        QUserEntity user = QUserEntity.userEntity;
        BooleanBuilder builder = new BooleanBuilder();

        dto.calculateOffSet();
        builder.and(role.delYn.eq(DelYn.N));

        if ( StringUtils.hasText(dto.getSearchKeyword()) ) {
            builder.and(role.roleNm.like("%" + dto.getSearchKeyword() + "%").or(role.roleDesc.like("%" + dto.getSearchKeyword() + "%")));
        }

        return queryFactory.select(Projections.constructor(RoleListDTO.class,
                                Expressions.numberTemplate(Long.class, "ROW_NUMBER() OVER(ORDER BY {0} ASC)", role.regDt),
                                role.roleSeq,
                                role.roleNm,
                                role.roleDesc,
                                role.delYn,
                                JPAExpressions.select(user.userNm)
                                        .from(user)
                                        .where(user.userSeq.eq(role.regSeq)),
                                role.regDt
                            ))
                            .from(role)
                            .where(builder)
                            .orderBy(Expressions.numberTemplate(Long.class, "ROW_NUMBER() OVER(ORDER BY {0} ASC)", role.regDt).asc())
                            .limit(dto.getPageScale())
                            .offset(dto.getOffSet())
                            .fetch();
    }

    public RoleDetailDTO findByRoleSeq(Integer roleSeq) {

        QRoleEntity role = QRoleEntity.roleEntity;
        QUserEntity user = QUserEntity.userEntity;

        return queryFactory.select(Projections.constructor(RoleDetailDTO.class, 
                                role.roleSeq,
                                role.roleNm,
                                role.roleDesc,
                                role.delYn,
                                JPAExpressions.select(user.userNm)
                                        .from(user)
                                        .where(user.userSeq.eq(role.regSeq)),
                                role.regDt,
                                JPAExpressions.select(user.userNm)
                                        .from(user)
                                        .where(user.userSeq.eq(role.updSeq)),
                                role.updDt
                            ))
                            .from(role)
                            .where(role.roleSeq.eq(roleSeq))
                            .fetchOne();
    }

    public Long updateById(RoleEntity entity) {

        QRoleEntity role = QRoleEntity.roleEntity;

        return queryFactory.update(role)
                        .set(role.roleNm, entity.getRoleNm())
                        .set(role.roleDesc, entity.getRoleDesc())
                        .set(role.updDt, entity.getUpdDt())
                        .set(role.updSeq, entity.getUpdSeq())
                        .where(role.roleSeq.eq(entity.getRoleSeq()))
                        .execute();
    }
}
