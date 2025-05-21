package com.project.demo.api.log.infrastructure;

import java.util.List;

import com.project.demo.api.log.application.dto.LogListDTO;
import com.project.demo.api.log.application.dto.LogRequestDTO;

public interface ErrLogRepositoryCustom {

    Long countBySearch(LogRequestDTO dto);

    List<LogListDTO> findBySearch(LogRequestDTO dto);
}
