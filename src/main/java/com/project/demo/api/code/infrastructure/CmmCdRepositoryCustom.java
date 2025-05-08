package com.project.demo.api.code.infrastructure;

import java.util.List;

import com.project.demo.api.common.application.dto.SelectBoxDTO;

public interface CmmCdRepositoryCustom {

    List<SelectBoxDTO> findSelectOptions(String grpCd);
}
