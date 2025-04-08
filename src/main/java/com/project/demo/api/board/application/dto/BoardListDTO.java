package com.project.demo.api.board.application.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "게시글 목록 조회를 위한 DTO")
public class BoardListDTO {

    @Schema(description = "글 순번")
    private Long rownum;

    @Schema(description = "게시글 SEQ")
    private Long boardSeq;

    @Schema(description = "게시글의 제목")
    private String title;

    @Schema(description = "게시글의 작성자")
    private String writerNm;

    @Schema(description = "게시글 등록일시")
    private String regDt;

    @Schema(description = "게시글 조회수")
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
