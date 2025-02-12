package com.project.demo.config.security.application.dto;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.project.demo.api.user.domain.UserEntity;
import com.project.demo.api.user.value.ActiveYn;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomUserDetails implements UserDetails {

    private static final long serialVersionUID = 1L;

    private Long userSeq;
    private Long roleSeq;
    private String userId;
    private String userPwd;
    private String userNm;
    private String profileImg;
    private ActiveYn activeYn;
    private LocalDateTime lastPwdDt;

    public static CustomUserDetails of(UserEntity user) {
        return CustomUserDetails.builder()
                    .userSeq(user.getUserSeq())
                    .roleSeq(user.getRoleSeq())
                    .userId(user.getUserId())
                    .userPwd(user.getUserPwd())
                    .userNm(user.getUserNm())
                    .profileImg(user.getProfileImg())
                    .activeYn(user.getActiveYn())
                    .lastPwdDt(user.getLastPwdDt())
                    .build();
    }
    
    public UserEntity toEntity() {
        return UserEntity.builder()
                .userSeq(this.userSeq)
                .roleSeq(this.roleSeq)
                .userId(this.userId)
                .userPwd(this.userPwd)
                .userNm(this.userNm)
                .profileImg(this.profileImg)
                .activeYn(this.activeYn)
                .build();
    }

    
    @Override
    public String getUsername() {
        return this.userId;
    }

    @Override
    public String getPassword() {
        return this.userPwd;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return activeYn.isFlag();
    }
}
