package com.project.demo.api.user.domain;

import java.time.LocalDateTime;

import com.project.demo.api.user.value.ActiveYn;

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
@Table(name = "users")
@Getter
@NoArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userSeq;

    private Long roleSeq;

    private String userId;

    private String userPwd;

    private String userNm;

    private String userEmail;

    private String profileImg;

    @Enumerated(EnumType.STRING)
    private ActiveYn activeYn;

    private LocalDateTime joinDt;

    private LocalDateTime lastPwdDt;

    private LocalDateTime lastLoginDt;

    private LocalDateTime updDt;

    private Long updSeq;

    @Builder
    public UserEntity(Long userSeq, Long roleSeq, String userId, String userPwd, String userNm, String userEmail, String profileImg, ActiveYn activeYn, LocalDateTime joninDt, LocalDateTime lastPwdDt, LocalDateTime lastLoginDt, LocalDateTime updDt, Long updSeq) {

        this.userSeq = userSeq;
        this.roleSeq = roleSeq;
        this.userId = userId;
        this.userPwd = userPwd;
        this.userNm = userNm;
        this.userEmail = userEmail;
        this.profileImg = profileImg;
        this.activeYn = activeYn;
        this.joinDt = joninDt;
        this.lastPwdDt = lastPwdDt;
        this.lastLoginDt = lastLoginDt;
        this.updDt = updDt;
        this.updSeq = updSeq;
    }
}
