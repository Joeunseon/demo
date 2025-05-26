package com.project.demo.api.log.infrastructure;

import java.util.List;

import com.project.demo.api.log.application.dto.LogDetailDTO;
import com.project.demo.api.log.application.dto.LogListDTO;
import com.project.demo.api.log.application.dto.LogRequestDTO;
import com.project.demo.api.log.application.dto.LogResolveDTO;

public interface ErrLogRepositoryCustom {

    Long countBySearch(LogRequestDTO dto);

    List<LogListDTO> findBySearch(LogRequestDTO dto);

    LogDetailDTO findByLogSeq(Long logSeq);

    Long updateResolve(LogResolveDTO dto);

    Long updateResolveList(LogResolveDTO dto);
}
