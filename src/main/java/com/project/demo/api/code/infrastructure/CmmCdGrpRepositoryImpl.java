package com.project.demo.api.code.infrastructure;

import java.util.List;

import com.project.demo.api.code.domain.QCmmCdGrpEntity;
import com.project.demo.api.common.application.dto.SelectBoxDTO;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CmmCdGrpRepositoryImpl implements CmmCdGrpRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public List<SelectBoxDTO> findSelectOptions() {

        QCmmCdGrpEntity cmmCdGrp = QCmmCdGrpEntity.cmmCdGrpEntity;

        return queryFactory.select(Projections.constructor(SelectBoxDTO.class, 
                                cmmCdGrp.grpCd,
                                cmmCdGrp.grpNm
                            ))
                            .from(cmmCdGrp)
                            .orderBy(cmmCdGrp.regDt.asc())
                            .fetch();
    }
}
