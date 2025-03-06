package com.project.demo.api.board.domain;

import java.time.LocalDateTime;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import com.project.demo.api.file.domain.FileMstrEntity;
import com.project.demo.common.constant.DelYn;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "board")
@Getter
@NoArgsConstructor
public class BoardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardSeq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "file_seq", nullable = false)
    private FileMstrEntity fileMstr;

    @Column(name = "title", length = 100)
    private String title;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Column(name = "view_cnt")
    private Integer viewCnt;

    @Enumerated(EnumType.STRING)
    @Column(name = "del_yn", columnDefinition = "yn")
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    private DelYn delYn;

    @Column(name = "reg_dt")
    private LocalDateTime regDt;

    @Column(name = "reg_seq")
    private Long regSeq;

    @Column(name = "upd_dt")
    private LocalDateTime updDt;

    @Column(name = "upd_seq")
    private Long updSeq;

    public BoardEntity(Long boardSeq, FileMstrEntity fileMstr, String title, String content, Integer viewCnt, DelYn delYn, LocalDateTime regDt, Long regSeq, LocalDateTime updDt, Long updSeq) {

        this.boardSeq = boardSeq;
        this.fileMstr = fileMstr;
        this.title = title;
        this.content = content;
        this.viewCnt = viewCnt;
        this.delYn = delYn;
        this.regDt = regDt;
        this.regSeq = regSeq;
        this.updDt = updDt;
        this.updSeq = updSeq;
    }
}
