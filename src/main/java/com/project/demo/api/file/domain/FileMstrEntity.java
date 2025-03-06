package com.project.demo.api.file.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "file_mstr")
@Getter
@NoArgsConstructor
public class FileMstrEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fileSeq;

    @Column(name = "reg_dt")
    private LocalDateTime regDt;

    @Column(name = "reg_seq")
    private Long regSeq;

    @Builder
    public FileMstrEntity(Long fileSeq, LocalDateTime regDt, Long regSeq) {

        this.fileSeq = fileSeq;
        this.regDt = regDt;
        this.regSeq = regSeq;
    }
}
