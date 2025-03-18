package com.project.demo.api.board.infrastructure;

import java.util.List;

import com.project.demo.api.board.application.dto.BoardDetailDTO;
import com.project.demo.api.board.application.dto.BoardListDTO;
import com.project.demo.api.board.application.dto.BoardRequestDTO;
import com.project.demo.api.board.domain.BoardEntity;

public interface BoardRepositoryCustom {

    Long countBySearch(BoardRequestDTO dto);

    List<BoardListDTO> findBySearch(BoardRequestDTO dto);

    BoardDetailDTO findDetailById(Long boardSeq);

    Long updateViewCnt(Long boardSeq);

    Long softDelete(Long boardSeq, Long userSeq);

    Long updateById(BoardEntity entity);
}
