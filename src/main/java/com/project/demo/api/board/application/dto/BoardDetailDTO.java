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
@Schema(description = "게시글 상세 조회를 위한 DTO")
public class BoardDetailDTO {

    @Schema(description = "게시글 SEQ")
    private Long boardSeq;
    
    @Schema(description = "파일 SEQ")
    private Long fileSeq;
    
    @Schema(description = "게시글 제목")
    private String title;

    @Schema(description = "게시글 작성자")
    private String writerNm;

    @Schema(description = "게시글 내용")
    private String content;

    @Schema(description = "게시글 등록일시")
    private String regDt;

    @Schema(description = "게시글 등록자 SEQ")
    private Long regSeq;

    @Schema(description = "게시글의 조회수")
    private Integer viewCnt;

    public BoardDetailDTO(Long boardSeq, Long fileSeq, String title, String writerNm, String content, LocalDateTime regDt, Long regSeq, Integer viewCnt) {
        this.boardSeq = boardSeq;
        this.fileSeq = fileSeq;
        this.title = title;
        this.writerNm = writerNm;
        this.content = content;
        this.regDt = (regDt != null) ? regDt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) : null;
        this.regSeq = regSeq;
        this.viewCnt = viewCnt;
    }
}
