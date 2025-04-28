package com.project.demo.api.role.application.dto;

import java.time.LocalDateTime;

import org.springframework.util.StringUtils;

import com.project.demo.api.role.domain.RoleEntity;
import com.project.demo.common.BaseDTO;
import com.project.demo.common.constant.DelYn;
import com.project.demo.common.validation.ValidationGroups.First;
import com.project.demo.common.validation.ValidationGroups.Fourth;
import com.project.demo.common.validation.ValidationGroups.Second;
import com.project.demo.common.validation.ValidationGroups.Third;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "권한 수정 진행을 위한 DTO")
public class RoleUpdateDTO extends BaseDTO {

    @Schema(description = "수정할 권한 SEQ")
    @Min(value = 0, message = "{error.request}", groups = First.class)
    private Integer roleSeq;

    @Schema(description = "수정할 권한 이름 (50자 이내)")
    @NotBlank(message = "{error.validation('권한')}", groups = Second.class)
    @Size(min = 1, max = 50, message ="{error.length('권한이름', 1, 50)}", groups = Third.class)
    private String roleNm;

    @Schema(description = "수정할 권한 설명 (100자 이내)")
    @Size(min = 0, max = 100, message ="{error.length('권한 설명', 0, 100)}", groups = Fourth.class)
    private String roleDesc;

    public RoleEntity toEntity(Long userSeq) {

        return RoleEntity.builder()
                            .roleSeq(roleSeq)
                            .roleNm(roleNm)
                            .roleDesc( StringUtils.hasText(roleDesc) ? roleDesc : null )
                            .delYn(DelYn.N)
                            .updDt(LocalDateTime.now())
                            .updSeq(userSeq)
                            .build();
    }
}
