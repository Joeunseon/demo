package com.project.demo.api.board.application.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BoardListDTO {

    private Long rownum;
    private Long boardSeq;
    private String title;
    private String writerNm;
    private String regDt;
    private Integer viewCnt;

    public BoardListDTO(Long rownum, Long boardSeq, String title, String writerNm, LocalDateTime regDt, Integer viewCnt) {
        this.rownum = rownum;
        this.boardSeq = boardSeq;
        this.title = title;
        this.writerNm = writerNm;
        this.regDt = (regDt != null) ? regDt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) : null;
        this.viewCnt = viewCnt;
    }
}
