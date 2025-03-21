package com.project.demo.api.file.infrastructure;

import java.util.List;

import com.project.demo.api.file.application.dto.FileDtlListDTO;
import com.project.demo.api.file.domain.QFileDtlEntity;
import com.project.demo.common.constant.DelYn;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FileDtlRepositoryImpl implements FileDtlRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public List<FileDtlListDTO> findByFileSeq(Long fileSeq) {

        QFileDtlEntity fileDtl = QFileDtlEntity.fileDtlEntity;

        return queryFactory.select(Projections.constructor(FileDtlListDTO.class,
                                fileDtl.dtlSeq,
                                fileDtl.oriNm,
                                fileDtl.fileSize,
                                fileDtl.fileOrder
                            ))
                        .from(fileDtl)
                        .where(fileDtl.fileMstr.fileSeq.eq(fileSeq)
                        .and(fileDtl.delYn.eq(DelYn.N)))
                        .orderBy(fileDtl.fileOrder.asc())
                        .fetch();
    }
}
