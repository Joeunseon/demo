package com.project.demo.api.file.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.demo.api.file.domain.FileMstrEntity;

@Repository
public interface FileMstrRepository extends JpaRepository<FileMstrEntity, Long> {

}
