package com.project.demo.api.code.infrastructure;

import java.util.List;

import org.springframework.util.StringUtils;

import com.project.demo.api.code.application.dto.CodeListDTO;
import com.project.demo.api.code.application.dto.CodeRequestDTO;
import com.project.demo.api.code.domain.CmmCdEntity;
import com.project.demo.api.code.domain.QCmmCdEntity;
import com.project.demo.api.code.domain.QCmmCdGrpEntity;
import com.project.demo.api.code.value.UseYn;
import com.project.demo.api.common.application.dto.SelectBoxDTO;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CmmCdRepositoryImpl implements CmmCdRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public List<SelectBoxDTO> findAllByGrpCd(String grpCd) {

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

    public Long countBySearch(CodeRequestDTO dto) {

        QCmmCdEntity cmmCd = QCmmCdEntity.cmmCdEntity;
        QCmmCdGrpEntity cmmCdGrp = QCmmCdGrpEntity.cmmCdGrpEntity;
        BooleanBuilder builder = new BooleanBuilder();

        if ( StringUtils.hasText(dto.getSearchKeyword()) ) {
            String keyword = dto.getSearchKeyword();
            builder.and(cmmCdGrp.grpCd.containsIgnoreCase(keyword)
                .or(cmmCdGrp.grpNm.containsIgnoreCase(keyword))
                .or(cmmCd.cd.containsIgnoreCase(keyword))
                .or(cmmCd.cdNm.containsIgnoreCase(keyword))
            );
        }

        return queryFactory.select(cmmCdGrp.count())
                            .from(cmmCdGrp)
                            .leftJoin(cmmCd).on(cmmCdGrp.grpSeq.eq(cmmCd.cmmCdGrp.grpSeq))
                            .where(builder)
                            .fetchFirst();
    }

    public List<CodeListDTO> findBySearch(CodeRequestDTO dto) {

        QCmmCdEntity cmmCd = QCmmCdEntity.cmmCdEntity;
        QCmmCdGrpEntity cmmCdGrp = QCmmCdGrpEntity.cmmCdGrpEntity;
        BooleanBuilder builder = new BooleanBuilder();

        dto.calculateOffSet();

        if ( StringUtils.hasText(dto.getSearchKeyword()) ) {
            String keyword = dto.getSearchKeyword();
            builder.and(cmmCdGrp.grpCd.containsIgnoreCase(keyword)
                .or(cmmCdGrp.grpNm.containsIgnoreCase(keyword))
                .or(cmmCd.cd.containsIgnoreCase(keyword))
                .or(cmmCd.cdNm.containsIgnoreCase(keyword))
            );
        }

        return queryFactory.select(Projections.constructor(CodeListDTO.class,
                                Expressions.numberTemplate(Long.class, "ROW_NUMBER() OVER(ORDER BY {0} ASC)", cmmCdGrp.regDt),
                                cmmCdGrp.grpSeq,
                                cmmCdGrp.grpCd,
                                cmmCdGrp.grpNm,
                                cmmCdGrp.useYn,
                                cmmCd.cdSeq,
                                cmmCd.cd,
                                cmmCd.cdNm,
                                cmmCd.useYn
                            ))
                            .from(cmmCdGrp)
                            .leftJoin(cmmCd).on(cmmCdGrp.grpSeq.eq(cmmCd.cmmCdGrp.grpSeq))
                            .where(builder)
                            .orderBy(Expressions.numberTemplate(Long.class, "ROW_NUMBER() OVER(ORDER BY {0} ASC)", cmmCdGrp.regDt).desc())
                            .limit(dto.getPageScale())
                            .offset(dto.getOffSet())
                            .fetch();
    }

    public Long updateById(CmmCdEntity entity) {

        QCmmCdEntity cmmCd = QCmmCdEntity.cmmCdEntity;

        return queryFactory.update(cmmCd)
                            .set(cmmCd.cmmCdGrp.grpCd, entity.getCmmCdGrp().getGrpCd())
                            .set(cmmCd.cd, entity.getCd())
                            .set(cmmCd.cdNm, entity.getCdNm())
                            .set(cmmCd.cdDesc, entity.getCdDesc())
                            .set(cmmCd.useYn, entity.getUseYn())
                            .set(cmmCd.updDt, entity.getUpdDt())
                            .set(cmmCd.updSeq, entity.getUpdSeq())
                            .where(cmmCd.cdSeq.eq(entity.getCdSeq()))
                            .execute();
    }
}
