package com.project.demo.api.code.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.demo.api.code.domain.CmmCdGrpEntity;

@Repository
public interface CmmCdGrpRepository extends JpaRepository<CmmCdGrpEntity, Long>, CmmCdGrpRepositoryCustom {

    Integer countByGrpCd(String grpCd);

    Integer countByGrpNm(String grpNm);
}
