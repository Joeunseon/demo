package com.project.demo.api.role.domain;

import java.time.LocalDateTime;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import com.project.demo.api.menu.domain.MenuEntity;
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
@Table(name = "menu_role")
@Getter
@NoArgsConstructor
public class MenuRoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long menuRoleSeq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_seq", nullable = false)
    private MenuEntity menu;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_seq", nullable = false)
    private RoleEntity role;

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
    public MenuRoleEntity(Long menuRoleSeq, MenuEntity menu, RoleEntity role, DelYn delYn, LocalDateTime regDt, Long regSeq, LocalDateTime updDt, Long updSeq) {

        this.menuRoleSeq = menuRoleSeq;
        this.menu = menu;
        this.role = role;
        this.delYn = delYn;
        this.regDt = regDt;
        this.regSeq = regSeq;
        this.updDt = updDt;
        this.updSeq = updSeq;
    }
}
