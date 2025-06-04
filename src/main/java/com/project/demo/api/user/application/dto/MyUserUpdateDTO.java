package com.project.demo.api.user.application.dto;

import java.time.LocalDateTime;

import org.springframework.util.StringUtils;

import com.project.demo.api.user.domain.UserEntity;
import com.project.demo.common.BaseDTO;
import com.project.demo.common.validation.ValidationGroups.First;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "내 정보 수정 진행을 위한 DTO")
public class MyUserUpdateDTO extends BaseDTO {

    @Schema(description = "사용자 이메일")
    @Size(min = 0, max = 100, message ="{error.length('이메일', 0, 100)}", groups = First.class)
    private String userEmail;

    @Schema(description = "사용자 프로필이미지(base64)")
    private String profileImg;
    
    public UserEntity toEntity(Long userSeq) {

        return UserEntity.builder()
                            .userSeq(userSeq)
                            .userEmail(StringUtils.hasText(userEmail) ? userEmail : null)
                            .profileImg(StringUtils.hasText(profileImg) ? profileImg : null)
                            .updDt(LocalDateTime.now())
                            .updSeq(userSeq)
                            .build();
    }
}
