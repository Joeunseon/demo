package com.project.demo.api.file.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.demo.api.file.domain.FileDtlEntity;

@Repository
public interface FileDtlRepository extends JpaRepository<FileDtlEntity, Long>, FileDtlRepositoryCustom {

}
