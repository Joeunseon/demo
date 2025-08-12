package com.project.demo.api.code.infrastructure;

import java.util.List;

import com.project.demo.api.code.application.dto.CodeListDTO;
import com.project.demo.api.code.application.dto.CodeRequestDTO;
import com.project.demo.api.code.domain.CmmCdEntity;
import com.project.demo.api.common.application.dto.SelectBoxDTO;

public interface CmmCdRepositoryCustom {

    List<SelectBoxDTO> findAllByGrpCd(String grpCd);

    Long countBySearch(CodeRequestDTO dto);

    List<CodeListDTO> findBySearch(CodeRequestDTO dto);

    public Long updateById(CmmCdEntity entity);
}
