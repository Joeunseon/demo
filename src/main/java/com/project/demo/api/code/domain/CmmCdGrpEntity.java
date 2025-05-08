package com.project.demo.api.code.domain;

import java.time.LocalDateTime;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import com.project.demo.api.code.value.UseYn;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cmm_cd_grp")
@Getter
@NoArgsConstructor
public class CmmCdGrpEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer grpSeq;

    @Column(name = "grp_cd", length = 50)
    private String grpCd;

    @Column(name = "grp_nm", length = 100)
    private String grpNm;

    @Column(name = "grp_desc", length = 200)
    private String grpDesc;

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

    @Builder
    public CmmCdGrpEntity(Integer grpSeq, String grpCd, String grpNm, String grpDesc, UseYn useYn, LocalDateTime regDt, Long regSeq, LocalDateTime updDt, Long updSeq) {

        this.grpSeq = grpSeq;
        this.grpCd = grpCd;
        this.grpNm = grpNm;
        this.grpDesc = grpDesc;
        this.useYn = useYn;
        this.regDt = regDt;
        this.regSeq = regSeq;
        this.updDt = updDt;
        this.updSeq = updSeq;
    }
}
