package com.project.demo.api.log.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.demo.api.log.domain.ErrLogEntity;

@Repository
public interface ErrLogRepositroy extends JpaRepository<ErrLogEntity, Long> {

}
