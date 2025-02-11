package com.project.demo.config.security.application.dto;

import java.io.Serializable;

import com.project.demo.api.user.domain.UserEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserSessionDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long userSeq;
    private Long roleSeq;
    private String userId;
    private String userNm;
    private String profileImg;

    public static UserSessionDTO of(UserEntity user) {
        return UserSessionDTO.builder()
                    .userSeq(user.getUserSeq())
                    .roleSeq(user.getRoleSeq())
                    .userId(user.getUserId())
                    .userNm(user.getUserNm())
                    .profileImg(user.getProfileImg())
                    .build();
    }
}
