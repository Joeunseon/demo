package com.project.demo.api.board.application.dto;

import java.time.LocalDateTime;

import com.project.demo.api.board.domain.BoardEntity;
import com.project.demo.common.BaseDTO;
import com.project.demo.common.constant.DelYn;
import com.project.demo.common.validation.ValidationGroups.*;

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
@Schema(description = "게시글 수정 진행을 위한 DTO")
public class BoardUpdateDTO extends BaseDTO {

    @Schema(description = "게시글 SEQ")
    @Min(value = 0, message = "{error.request}", groups = First.class)
    private Long boardSeq;

    @Schema(description = "수정할 게시글의 제목 (100자 이내)")
    @NotBlank(message = "{error.validation('제목')}", groups = Second.class)
    @Size(min = 1, max = 100, message ="{error.length('제목', 1, 100)}", groups = Third.class)
    private String title;

    @Schema(description = "수정할 게시글의 내용")
    @NotBlank(message = "{error.validation('내용')}", groups = Fourth.class)
    private String content;

    public BoardEntity toEntity(Long userSeq) {
        return BoardEntity.builder()
                    .boardSeq(boardSeq)
                    .title(title)
                    .content(content)
                    .delYn(DelYn.N)
                    .updDt(LocalDateTime.now())
                    .updSeq(userSeq)
                    .build();
    }
}
