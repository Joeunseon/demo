package com.project.demo.api.user.infrastructure;

import java.util.List;

import org.springframework.util.StringUtils;

import com.project.demo.api.role.domain.QRoleEntity;
import com.project.demo.api.user.application.dto.UserDetailDTO;
import com.project.demo.api.user.application.dto.UserListDTO;
import com.project.demo.api.user.application.dto.UserRequestDTO;
import com.project.demo.api.user.domain.QUserEntity;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public Long countBySearch(UserRequestDTO dto) {

        QUserEntity user = QUserEntity.userEntity;

        BooleanBuilder builder = new BooleanBuilder();

        if ( StringUtils.hasText(dto.getSearchKeyword()) ) {
            switch ( dto.getCustomCondition() ) {
                case ALL:
                    builder.and(user.userId.like("%" + dto.getSearchKeyword() + "%")
                            .or(user.userNm.like("%" + dto.getSearchKeyword() + "%")));
                    break;
                case ID:
                    builder.and(user.userId.like("%" + dto.getSearchKeyword() + "%"));
                    break;
                case NAME:
                    builder.and(user.userNm.like("%" + dto.getSearchKeyword() + "%"));
            }
        }

        if ( dto.getRoleSeq() != null )
            builder.and(user.role.roleSeq.eq(dto.getRoleSeq()));

        if ( dto.getActiveYn() != null )
            builder.and(user.activeYn.eq(dto.getActiveYn()));

        return queryFactory.select(user.count())
                            .from(user)
                            .where(builder)
                            .fetchFirst();
    }

    public List<UserListDTO> findBySearch(UserRequestDTO dto) {

        QUserEntity user = QUserEntity.userEntity;
        QRoleEntity role = QRoleEntity.roleEntity;

        dto.calculateOffSet();
        BooleanBuilder builder = new BooleanBuilder();

        if ( StringUtils.hasText(dto.getSearchKeyword()) ) {
            switch ( dto.getCustomCondition() ) {
                case ALL:
                    builder.and(user.userId.like("%" + dto.getSearchKeyword() + "%")
                            .or(user.userNm.like("%" + dto.getSearchKeyword() + "%")));
                    break;
                case ID:
                    builder.and(user.userId.like("%" + dto.getSearchKeyword() + "%"));
                    break;
                case NAME:
                    builder.and(user.userNm.like("%" + dto.getSearchKeyword() + "%"));
            }
        }

        if ( dto.getRoleSeq() != null )
            builder.and(user.role.roleSeq.eq(dto.getRoleSeq()));

        if ( dto.getActiveYn() != null )
            builder.and(user.activeYn.eq(dto.getActiveYn()));

        return queryFactory.select(Projections.constructor(UserListDTO.class,
                                Expressions.numberTemplate(Long.class, "ROW_NUMBER() OVER(ORDER BY {0} ASC)", user.signupDt),
                                user.userSeq,
                                role.roleNm,
                                user.userId,
                                user.userNm,
                                user.activeYn,
                                user.signupDt)
                            )
                            .from(user)
                            .join(user.role, role)
                            .where(builder)
                            .orderBy(Expressions.numberTemplate(Long.class, "ROW_NUMBER() OVER(ORDER BY {0} ASC)", user.signupDt).desc())
                            .limit(dto.getPageScale())
                            .offset(dto.getOffSet())
                            .fetch();
    }

    public UserDetailDTO findByUserSeq(Long userSeq) {

        QUserEntity user = QUserEntity.userEntity;
        QRoleEntity role = QRoleEntity.roleEntity;

        return queryFactory.select(Projections.constructor(UserDetailDTO.class,
                                user.userSeq,
                                role.roleNm,
                                user.userId,
                                user.userNm,
                                user.userEmail,
                                user.profileImg,
                                user.activeYn,
                                user.signupDt
                            ))
                            .from(user)
                            .join(user.role, role)
                            .where(user.userSeq.eq(userSeq))
                            .fetchOne();
    }
}
