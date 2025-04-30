package com.project.demo.api.user.domain;

import java.time.LocalDateTime;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import com.project.demo.api.role.domain.RoleEntity;
import com.project.demo.api.user.value.ActiveYn;

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
@Table(name = "users")
@Getter
@NoArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userSeq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_seq", nullable = false)
    private RoleEntity role;

    @Column(name = "user_id", length = 50)
    private String userId;

    @Column(name = "user_pwd", length = 255)
    private String userPwd;

    @Column(name = "user_nm", length = 50)
    private String userNm;

    @Column(name = "user_email", length = 100)
    private String userEmail;

    @Column(name = "profile_img", columnDefinition = "TEXT")
    private String profileImg;

    @Enumerated(EnumType.STRING)
    @Column(name = "active_yn", columnDefinition = "yn")
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    private ActiveYn activeYn;

    @Column(name = "signup_dt")
    private LocalDateTime signupDt;

    @Column(name = "last_pwd_dt")
    private LocalDateTime lastPwdDt;

    @Column(name = "last_login_dt")
    private LocalDateTime lastLoginDt;

    @Column(name = "upd_dt")
    private LocalDateTime updDt;

    @Column(name = "upd_seq")
    private Long updSeq;

    @Builder
    public UserEntity(Long userSeq, RoleEntity role, String userId, String userPwd, String userNm, String userEmail, String profileImg, ActiveYn activeYn, LocalDateTime signupDt, LocalDateTime lastPwdDt, LocalDateTime lastLoginDt, LocalDateTime updDt, Long updSeq) {

        this.userSeq = userSeq;
        this.role = role;
        this.userId = userId;
        this.userPwd = userPwd;
        this.userNm = userNm;
        this.userEmail = userEmail;
        this.profileImg = profileImg;
        this.activeYn = activeYn;
        this.signupDt = signupDt;
        this.lastPwdDt = lastPwdDt;
        this.lastLoginDt = lastLoginDt;
        this.updDt = updDt;
        this.updSeq = updSeq;
    }
}
