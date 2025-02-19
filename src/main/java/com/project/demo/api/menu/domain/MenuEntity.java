package com.project.demo.api.menu.domain;

import java.time.LocalDateTime;

import com.project.demo.api.menu.value.ActiveYn;
import com.project.demo.api.menu.value.MenuType;

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
@Table(name = "menus")
@Getter
@NoArgsConstructor
public class MenuEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long menuSeq;

    private String menuNm;

    private String menuUrl;

    private Long parentSeq;

    private Integer menuLevel;

    private Integer menuOrder;

    @Enumerated(EnumType.STRING)
    private MenuType menuType;

    @Enumerated(EnumType.STRING)
    private ActiveYn activeYn;

    private LocalDateTime regDt;

    private Long regSeq;

    private LocalDateTime updDt;

    private Long updSeq;

    @Builder
    public MenuEntity(Long menuSeq, String menuNm, String menuUrl, Long parentSeq, Integer menuLevel, Integer menuOrder, MenuType menuType, ActiveYn activeYn, LocalDateTime regDt, Long regSeq, LocalDateTime updDt, Long updSeq) {

        this.menuSeq = menuSeq;
        this.menuNm = menuNm;
        this.menuUrl = menuUrl;
        this.parentSeq = parentSeq;
        this.menuLevel = menuLevel;
        this.menuOrder = menuOrder;
        this.menuType = menuType;
        this.activeYn = activeYn;
        this.regDt = regDt;
        this.regSeq = regSeq;
        this.updDt = updDt;
        this.updSeq = updSeq;
    }
}
