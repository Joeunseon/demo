package com.project.demo.api.board.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.demo.api.board.domain.BoardEntity;

@Repository
public interface BoardRepository extends JpaRepository<BoardEntity, Long>, BoardRepositoryCustom {

}
