package com.project.demo.api.board.infrastructure;

import java.util.List;

import com.project.demo.api.board.application.dto.BoardListDTO;
import com.project.demo.api.board.application.dto.BoardRequestDTO;

public interface BoardRepositoryCustom {

    Long countBySearch(BoardRequestDTO dto);

    List<BoardListDTO> findBySearch(BoardRequestDTO dto);
}
