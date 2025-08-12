package com.project.demo.api.code.infrastructure;

import java.util.List;

import com.project.demo.api.code.domain.CmmCdGrpEntity;
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

    public Long updateById(CmmCdGrpEntity entity) {

        QCmmCdGrpEntity cmmCdGrp = QCmmCdGrpEntity.cmmCdGrpEntity;

        return queryFactory.update(cmmCdGrp)
                            .set(cmmCdGrp.grpCd, entity.getGrpCd())
                            .set(cmmCdGrp.grpNm, entity.getGrpNm())
                            .set(cmmCdGrp.grpDesc, entity.getGrpDesc())
                            .set(cmmCdGrp.useYn, entity.getUseYn())
                            .set(cmmCdGrp.updDt, entity.getUpdDt())
                            .set(cmmCdGrp.updSeq, entity.getUpdSeq())
                            .where(cmmCdGrp.grpSeq.eq(entity.getGrpSeq()))
                            .execute();
    }
}
