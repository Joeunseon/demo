package com.project.demo.api.menu.application.dto;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;

@Getter
public class MenuDisplayDTO {

    private final Long menuSeq;
    private final String menuNm;
    private final String menuUrl;
    private final Long parentSeq;
    
    @QueryProjection
    public MenuDisplayDTO(Long menuSeq, String menuNm, String menuUrl, Long parentSeq) {
        this.menuSeq = menuSeq;
        this.menuNm = menuNm;
        this.menuUrl = menuUrl;
        this.parentSeq = parentSeq;
    }
}
