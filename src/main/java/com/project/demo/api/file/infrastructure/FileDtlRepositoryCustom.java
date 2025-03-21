package com.project.demo.api.file.infrastructure;

import java.util.List;

import com.project.demo.api.file.application.dto.FileDtlListDTO;

public interface FileDtlRepositoryCustom {

    List<FileDtlListDTO> findByFileSeq(Long fileSeq);
}
