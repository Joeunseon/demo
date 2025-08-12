package com.project.demo.api.code.infrastructure;

import java.util.List;

import com.project.demo.api.code.domain.CmmCdGrpEntity;
import com.project.demo.api.common.application.dto.SelectBoxDTO;

public interface CmmCdGrpRepositoryCustom {

    public List<SelectBoxDTO> findAllOrderByRegDtAsc();

    public Long updateById(CmmCdGrpEntity entity);
}
