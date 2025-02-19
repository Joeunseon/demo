package com.project.demo.api.menu.domain;

import java.time.LocalDateTime;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import com.project.demo.api.menu.value.ActiveYn;
import com.project.demo.api.menu.value.MenuType;

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
@Table(name = "menus")
@Getter
@NoArgsConstructor
public class MenuEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long menuSeq;

    @Column(name = "menu_nm", length = 100)
    private String menuNm;

    @Column(name = "menu_url", length = 200)
    private String menuUrl;

    @Column(name = "parent_seq")
    private Long parentSeq;

    @Column(name = "menu_level")
    private Integer menuLevel;

    @Column(name = "menu_order")
    private Integer menuOrder;

    @Enumerated(EnumType.STRING)
    @Column(name = "menu_type", columnDefinition = "menu")
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    private MenuType menuType;

    @Enumerated(EnumType.STRING)
    @Column(name = "active_yn", columnDefinition = "yn")
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    private ActiveYn activeYn;

    @Column(name = "reg_dt")
    private LocalDateTime regDt;

    @Column(name = "reg_seq")
    private Long regSeq;

    @Column(name = "upd_dt")
    private LocalDateTime updDt;

    @Column(name = "upd_seq")
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
