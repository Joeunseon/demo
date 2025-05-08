package com.project.demo.api.code.domain;

import java.time.LocalDateTime;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import com.project.demo.api.code.value.UseYn;

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
@Table(name = "cmm_cd")
@Getter
@NoArgsConstructor
public class CmmCdEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cdSeq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "grp_seq", nullable = false)
    private CmmCdGrpEntity cmmCdGrp;

    @Column(name = "cd", length = 50)
    private String cd;

    @Column(name = "cd_nm", length = 100)
    private String cdNm;

    @Column(name = "cd_desc", length = 200)
    private String cdDesc;

    @Enumerated(EnumType.STRING)
    @Column(name = "use_yn", columnDefinition = "yn")
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    private UseYn useYn;

    @Column(name = "reg_dt")
    private LocalDateTime regDt;

    @Column(name = "reg_seq")
    private Long regSeq;

    @Column(name = "upd_dt")
    private LocalDateTime updDt;

    @Column(name = "upd_seq")
    private Long updSeq;

    public CmmCdEntity(Integer cdSeq, CmmCdGrpEntity cmmCdGrp, String cd, String cdNm, String cdDesc, UseYn useYn, LocalDateTime regDt, Long regSeq, LocalDateTime updDt, Long updSeq) {

        this.cdSeq = cdSeq;
        this.cmmCdGrp = cmmCdGrp;
        this.cd = cd;
        this.cdNm = cdNm;
        this.cdDesc = cdDesc;
        this.useYn = useYn;
        this.regDt = regDt;
        this.regSeq = regSeq;
        this.updDt = updDt;
        this.updSeq = updSeq;
    }
}
