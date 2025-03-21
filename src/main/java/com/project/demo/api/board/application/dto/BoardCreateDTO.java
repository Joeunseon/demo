package com.project.demo.api.board.application.dto;

import java.time.LocalDateTime;

import com.project.demo.api.board.domain.BoardEntity;
import com.project.demo.api.file.domain.FileMstrEntity;
import com.project.demo.common.BaseDTO;
import com.project.demo.common.constant.DelYn;
import com.project.demo.common.validation.ValidationGroups.*;

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
@Schema(description = "게시글 등록 진행을 위한 DTO")
public class BoardCreateDTO extends BaseDTO {

    @Schema(description = "등록할 게시글의 파일 SEQ")
    private Long fileSeq;

    @Schema(description = "등록할 게시글의 제목 (100자 이내)")
    @NotBlank(message = "{error.validation('제목')}", groups = First.class)
    @Size(min = 1, max = 100, message ="{error.length('제목', 1, 100)}", groups = Second.class)
    private String title;

    @Schema(description = "등록할 게시글의 내용")
    @NotBlank(message = "{error.validation('내용')}", groups = Third.class)
    private String content;

    public BoardEntity toEntity(Long userSeq, FileMstrEntity fileEntity) {

        return BoardEntity.builder()
                    .fileMstr(fileEntity)
                    .title(title)
                    .content(content)
                    .viewCnt(0)
                    .delYn(DelYn.N)
                    .regDt(LocalDateTime.now())
                    .regSeq(userSeq)
                    .build();
    }
}
