package com.project.demo.api.user.application.dto;

import java.time.LocalDateTime;

import org.springframework.util.StringUtils;

import com.project.demo.api.role.domain.RoleEntity;
import com.project.demo.api.user.domain.UserEntity;
import com.project.demo.api.user.value.ActiveYn;
import com.project.demo.common.BaseDTO;
import com.project.demo.common.validation.ValidationGroups.First;
import com.project.demo.common.validation.ValidationGroups.Second;
import com.project.demo.common.validation.ValidationGroups.Third;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "사용자 수정 진행을 위한 DTO")
public class UserUpdateDTO extends BaseDTO {

    @Schema(description = "사용자 SEQ")
    @Min(value = 0, message = "{error.request}", groups = First.class)
    private Long userSeq;

    @Schema(description = "권한 SEQ")
    @Min(value = 0, message = "{error.request}", groups = Second.class)
    private Integer roleSeq;

    @Schema(description = "사용자 이메일")
    @Size(min = 0, max = 100, message ="{error.length('이메일', 0, 100)}", groups = Third.class)
    private String userEmail;

    @Schema(description = "사용자 프로필이미지(base64)")
    private String profileImg;

    @Schema(description = "활성화 여부")
    @Enumerated(EnumType.STRING)
    private ActiveYn activeYn;

    public UserEntity toEntity(Long loginSeq) {

        RoleEntity role =  RoleEntity.builder().roleSeq(roleSeq).build();

        return UserEntity.builder()
                            .userSeq(userSeq)
                            .role(role)
                            .userEmail(StringUtils.hasText(userEmail) ? userEmail : null)
                            .profileImg(StringUtils.hasText(profileImg) ? profileImg : null)
                            .activeYn(activeYn)
                            .updDt(LocalDateTime.now())
                            .updSeq(loginSeq)
                            .build();
    }
}
