package com.project.demo.api.log.infrastructure;

import java.util.List;

import org.springframework.util.StringUtils;

import com.project.demo.api.log.application.dto.LogDetailDTO;
import com.project.demo.api.log.application.dto.LogListDTO;
import com.project.demo.api.log.application.dto.LogRequestDTO;
import com.project.demo.api.log.domain.QErrLogEntity;
import com.project.demo.api.log.value.RequestMethod;
import com.project.demo.api.log.value.ResolvedStat;
import com.project.demo.api.user.domain.QUserEntity;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ErrLogRepositoryImpl implements ErrLogRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public Long countBySearch(LogRequestDTO dto) {

        QErrLogEntity errLog = QErrLogEntity.errLogEntity;

        BooleanBuilder builder = new BooleanBuilder();

        if ( StringUtils.hasText(dto.getSearchKeyword()) ) {
            switch ( dto.getCustomCondition() ) {
                case ALL:
                    builder.and(errLog.errMsg.like("%" + dto.getSearchKeyword() + "%")
                            .or(errLog.requestUrl.like("%" + dto.getSearchKeyword() + "%")));
                    break;
                case MSG:
                    builder.and(errLog.errMsg.like("%" + dto.getSearchKeyword() + "%"));
                    break;
                case URL:
                    builder.and(errLog.requestUrl.like("%" + dto.getSearchKeyword() + "%"));
            }
        }

        if ( dto.getErrLevel() != null ) {
            builder.and(errLog.errLevel.eq(dto.getErrLevel()));
        }

        if ( dto.getResolvedStat() == ResolvedStat.Y ) {
            builder.and(errLog.resolvedDt.isNotNull());
        }
        
        if ( dto.getResolvedStat() == ResolvedStat.N ) {
            builder.and(errLog.resolvedDt.isNull());
        }

        if ( dto.getRequestMethod() != null && dto.getRequestMethod() != RequestMethod.ALL ) {
            builder.and(errLog.requestMethod.eq(dto.getRequestMethod().toString()));
        }

        return queryFactory.select(errLog.count())
                            .from(errLog)
                            .where(builder)
                            .fetchFirst();
    }

    public List<LogListDTO> findBySearch(LogRequestDTO dto) {

        QErrLogEntity errLog = QErrLogEntity.errLogEntity;

        dto.calculateOffSet();
        BooleanBuilder builder = new BooleanBuilder();

        if ( StringUtils.hasText(dto.getSearchKeyword()) ) {
            switch ( dto.getCustomCondition() ) {
                case ALL:
                    builder.and(errLog.errMsg.like("%" + dto.getSearchKeyword() + "%")
                            .or(errLog.requestUrl.like("%" + dto.getSearchKeyword() + "%")));
                    break;
                case MSG:
                    builder.and(errLog.errMsg.like("%" + dto.getSearchKeyword() + "%"));
                    break;
                case URL:
                    builder.and(errLog.requestUrl.like("%" + dto.getSearchKeyword() + "%"));
            }
        }

        if ( dto.getErrLevel() != null ) {
            builder.and(errLog.errLevel.eq(dto.getErrLevel()));
        }

        if ( dto.getResolvedStat() != null ) {
            if ( dto.getResolvedStat() == ResolvedStat.Y ) {
                builder.and(errLog.resolvedDt.isNotNull());
            }
            
            if ( dto.getResolvedStat() == ResolvedStat.N ) {
                builder.and(errLog.resolvedDt.isNull());
            }
        }

        if ( dto.getRequestMethod() != null && dto.getRequestMethod() != RequestMethod.ALL ) {
            builder.and(errLog.requestMethod.eq(dto.getRequestMethod().toString()));
        }

        return queryFactory.select(Projections.constructor(LogListDTO.class,
                                Expressions.numberTemplate(Long.class, "ROW_NUMBER() OVER(ORDER BY {0} ASC)", errLog.occurredDt),
                                errLog.logSeq,
                                errLog.errCd,
                                errLog.errLevel,
                                errLog.requestMethod,
                                errLog.occurredDt,
                                errLog.resolvedDt
                                )
                            )
                            .from(errLog)
                            .where(builder)
                            .orderBy(Expressions.numberTemplate(Long.class, "ROW_NUMBER() OVER(ORDER BY {0} ASC)", errLog.occurredDt).desc())
                            .limit(dto.getPageScale())
                            .offset(dto.getOffSet())
                            .fetch();
    }

    public LogDetailDTO findByLogSeq(Long logSeq) {

        QErrLogEntity errLog = QErrLogEntity.errLogEntity;
        QUserEntity user = QUserEntity.userEntity;

        var subQuery = JPAExpressions
                            .select(user.userId)
                            .from(user)
                            .where(user.userSeq.eq(errLog.requestSeq));

        return queryFactory.select(Projections.constructor(LogDetailDTO.class, 
                                    errLog.logSeq,
                                    errLog.errCd,
                                    errLog.errMsg,
                                    errLog.errLevel,
                                    errLog.occurredDt,
                                    errLog.requestUrl,
                                    errLog.requestMethod,
                                    subQuery,
                                    errLog.resolvedDt
                                )
                            )
                            .from(errLog)
                            .where(errLog.logSeq.eq(logSeq))
                            .fetchOne();
    }
}
