package com.project.demo.api.role.domain;

import java.time.LocalDateTime;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import com.project.demo.common.constant.DelYn;

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
@Table(name = "roles")
@Getter
@NoArgsConstructor
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer roleSeq;

    @Column(name = "role_nm", length = 50)
    private String roleNm;

    @Column(name = "role_desc", length = 100)
    private String roleDesc;
    
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
    public RoleEntity(Integer roleSeq, String roleNm, String roleDesc, DelYn delYn, LocalDateTime regDt, Long regSeq, LocalDateTime updDt, Long updSeq) {

        this.roleSeq = roleSeq;
        this.roleNm = roleNm; 
        this.roleDesc = roleDesc;
        this.delYn = delYn;
        this.regDt = regDt;
        this.regSeq = regSeq;
        this.updDt = updDt;
        this.updSeq = updSeq;
    }
}
