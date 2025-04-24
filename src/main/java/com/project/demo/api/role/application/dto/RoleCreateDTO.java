package com.project.demo.api.role.application.dto;

import java.time.LocalDateTime;

import org.springframework.util.StringUtils;

import com.project.demo.api.role.domain.RoleEntity;
import com.project.demo.common.BaseDTO;
import com.project.demo.common.constant.DelYn;
import com.project.demo.common.validation.ValidationGroups.First;
import com.project.demo.common.validation.ValidationGroups.Second;
import com.project.demo.common.validation.ValidationGroups.Third;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "권한 등록 진행을 위한 DTO")
public class RoleCreateDTO extends BaseDTO {

    @Schema(description = "등록할 권한 이름 (50자 이내)")
    @NotBlank(message = "{error.validation('권한')}", groups = First.class)
    @Size(min = 1, max = 50, message ="{error.length('권한이름', 1, 50)}", groups = Second.class)
    private String roleNm;

    @Schema(description = "등록할 권한 설명 (100자 이내)")
    @Size(min = 0, max = 100, message ="{error.length('권한 설명', 0, 100)}", groups = Third.class)
    private String roleDesc;

    public RoleEntity toEntity(Long userSeq) {

        return RoleEntity.builder()
                            .roleNm(roleNm)
                            .roleDesc( StringUtils.hasText(roleDesc) ? roleDesc : null )
                            .delYn(DelYn.N)
                            .regDt(LocalDateTime.now())
                            .regSeq(userSeq)
                            .build();
    }
}
