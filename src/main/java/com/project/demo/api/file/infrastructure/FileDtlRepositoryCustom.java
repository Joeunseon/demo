package com.project.demo.api.file.infrastructure;

import java.util.List;

import com.project.demo.api.file.application.dto.FileDtlListDTO;
import com.project.demo.api.file.application.dto.FileDtlUpdateDTO;

public interface FileDtlRepositoryCustom {

    List<FileDtlListDTO> findByFileSeq(Long fileSeq);

    Long softDelete(FileDtlUpdateDTO dto);

    Integer findMaxOrderByFilSeq(Long fileSeq);
}
