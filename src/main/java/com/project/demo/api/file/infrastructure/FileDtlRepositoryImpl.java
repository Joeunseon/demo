package com.project.demo.api.file.infrastructure;

import java.time.LocalDateTime;
import java.util.List;

import com.project.demo.api.file.application.dto.FileDtlListDTO;
import com.project.demo.api.file.application.dto.FileDtlUpdateDTO;
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
                                fileDtl.filePath,
                                fileDtl.fileSize,
                                fileDtl.fileOrder
                            ))
                        .from(fileDtl)
                        .where(fileDtl.fileMstr.fileSeq.eq(fileSeq)
                        .and(fileDtl.delYn.eq(DelYn.N)))
                        .orderBy(fileDtl.fileOrder.asc())
                        .fetch();
    }

    public Long softDelete(FileDtlUpdateDTO dto) {

        QFileDtlEntity fileDtl = QFileDtlEntity.fileDtlEntity;

        return queryFactory.update(fileDtl)
                            .set(fileDtl.delYn, DelYn.Y)
                            .set(fileDtl.updDt, LocalDateTime.now())
                            .set(fileDtl.updSeq, dto.getUserSessionDTO().getUserSeq())
                            .where(fileDtl.fileMstr.fileSeq.eq(dto.getFileSeq()).and(fileDtl.dtlSeq.notIn(dto.getDtlSeq())))
                            .execute();
    }

    public Integer findMaxOrderByFilSeq(Long fileSeq) {

        QFileDtlEntity fileDtl = QFileDtlEntity.fileDtlEntity;

        return queryFactory.select(fileDtl.fileOrder.max())
                            .from(fileDtl)
                            .where(fileDtl.fileMstr.fileSeq.eq(fileSeq).and(fileDtl.delYn.eq(DelYn.N)))
                            .fetchOne();
    }
}
