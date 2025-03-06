package com.project.demo.api.file.domain;

import java.time.LocalDateTime;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

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
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "file_dtl")
@Getter
@NoArgsConstructor
public class FileDtlEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dtlSeq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "file_seq", nullable = false)
    private FileMstrEntity fileMstr;

    @Column(name = "stre_nm", length = 200)
    private String streNm;

    @Column(name = "ori_nm", length = 200)
    private String oriNm;

    @Column(name = "file_path", length = 300)
    private String filePath;

    @Column(name = "extsn", length = 5)
    private String extsn;

    @Column(name = "file_size")
    private Long fileSize;

    @Column(name = "file_order")
    private Integer fileOrder;

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

    @Builder
    public FileDtlEntity(Long dtlSeq, FileMstrEntity fileMstr, String streNm, String oriNm, String filePath, String extsn, Long fileSize, Integer fileOrder, DelYn delYn, LocalDateTime regDt, Long regSeq, LocalDateTime updDt, Long updSeq) {

        this.dtlSeq = dtlSeq;
        this.fileMstr = fileMstr;
        this.streNm = streNm;
        this.oriNm = oriNm;
        this.filePath = filePath;
        this.extsn = extsn;
        this.fileSize = fileSize;
        this.fileOrder = fileOrder;
        this.delYn = delYn;
        this.regDt = regDt;
        this.regSeq = regSeq;
        this.updDt = updDt;
        this.updSeq = updSeq;
    }
}
