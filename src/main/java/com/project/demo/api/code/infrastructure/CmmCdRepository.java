package com.project.demo.api.code.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.demo.api.code.domain.CmmCdEntity;
import com.project.demo.api.code.domain.CmmCdGrpEntity;

import java.util.List;


@Repository
public interface CmmCdRepository extends JpaRepository<CmmCdEntity, Long>, CmmCdRepositoryCustom {

    Integer countByCd(String cd);

    Integer countByCdNm(String cdNm);

    List<CmmCdEntity> findByCmmCdGrp(CmmCdGrpEntity cmmCdGrp);
}
