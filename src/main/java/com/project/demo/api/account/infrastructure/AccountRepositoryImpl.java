package com.project.demo.api.account.infrastructure;

import java.time.LocalDateTime;

import com.project.demo.api.user.domain.QUserEntity;
import com.project.demo.api.user.domain.UserEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AccountRepositoryImpl implements AccountRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public Long updatePasswordDelay(Long userSeq) {

        QUserEntity user = QUserEntity.userEntity;

        return queryFactory.update(user)
                            .set(user.lastPwdDt, LocalDateTime.now())
                            .where(user.userSeq.eq(userSeq))
                            .execute();
    }

    public Long updatePassowrd(UserEntity entity) {

        QUserEntity user = QUserEntity.userEntity;

        return queryFactory.update(user)
                            .set(user.userPwd, entity.getUserPwd())
                            .set(user.lastPwdDt, entity.getLastPwdDt())
                            .where(user.userSeq.eq(entity.getUserSeq()))
                            .execute();
    }
}
