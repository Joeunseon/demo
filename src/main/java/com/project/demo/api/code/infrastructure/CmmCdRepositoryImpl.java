package com.project.demo.api.code.infrastructure;

import java.util.List;

import com.project.demo.api.code.domain.QCmmCdEntity;
import com.project.demo.api.code.domain.QCmmCdGrpEntity;
import com.project.demo.api.code.value.UseYn;
import com.project.demo.api.common.application.dto.SelectBoxDTO;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CmmCdRepositoryImpl implements CmmCdRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public List<SelectBoxDTO> findSelectOptions(String grpCd) {

        QCmmCdEntity cmmCd = QCmmCdEntity.cmmCdEntity;
        QCmmCdGrpEntity cmmCdGrp = QCmmCdGrpEntity.cmmCdGrpEntity;

        return queryFactory.select(Projections.constructor(SelectBoxDTO.class, 
                                cmmCd.cd,
                                cmmCd.cdNm
                            ))
                            .from(cmmCd)
                            .join(cmmCd.cmmCdGrp, cmmCdGrp)
                            .where(cmmCd.useYn.eq(UseYn.Y)
                                .and(cmmCdGrp.useYn.eq(UseYn.Y))
                                .and(cmmCdGrp.grpCd.eq(grpCd))
                            )
                            .orderBy(cmmCd.regDt.asc())
                            .fetch();
    }
}
