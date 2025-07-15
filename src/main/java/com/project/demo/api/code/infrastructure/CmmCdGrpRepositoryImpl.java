package com.project.demo.api.code.infrastructure;

import java.util.List;

import com.project.demo.api.code.domain.QCmmCdGrpEntity;
import com.project.demo.api.common.application.dto.SelectBoxDTO;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CmmCdGrpRepositoryImpl implements CmmCdGrpRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public List<SelectBoxDTO> findAllOrderByRegDtAsc() {

        QCmmCdGrpEntity cmmCdGrp = QCmmCdGrpEntity.cmmCdGrpEntity;

        return queryFactory.select(Projections.constructor(SelectBoxDTO.class, 
                                Expressions.stringTemplate("CAST({0} AS string)", cmmCdGrp.grpSeq),
                                cmmCdGrp.grpNm
                            ))
                            .from(cmmCdGrp)
                            .orderBy(cmmCdGrp.regDt.asc())
                            .fetch();
    }
}
