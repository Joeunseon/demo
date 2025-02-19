package com.project.demo.api.role.domain;

import java.time.LocalDateTime;

import com.project.demo.common.constant.DelYn;

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
    private Long roleSeq;

    private String roleNm;

    private String roleDesc;
    
    @Enumerated(EnumType.STRING)
    private DelYn delYn;

    private LocalDateTime regDt;

    private Long regSeq;

    private LocalDateTime updDt;

    private Long updSeq;

    @Builder
    public RoleEntity(Long roleSeq, String roleNm, String roleDesc, DelYn delYn, LocalDateTime regDt, Long regSeq, LocalDateTime updDt, Long updSeq) {

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
