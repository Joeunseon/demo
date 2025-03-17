package com.project.demo.api.board.application.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BoardDetailDTO {

    private Long boardSeq;
    private String title;
    private String writerNm;
    private String content;
    private String regDt;
    private Long regSeq;
    private Integer viewCnt;

    public BoardDetailDTO(Long boardSeq, String title, String writerNm, String content, LocalDateTime regDt, Long regSeq, Integer viewCnt) {
        this.boardSeq = boardSeq;
        this.title = title;
        this.writerNm = writerNm;
        this.content = content;
        this.regDt = (regDt != null) ? regDt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) : null;
        this.regSeq = regSeq;
        this.viewCnt = viewCnt;
    }
}
