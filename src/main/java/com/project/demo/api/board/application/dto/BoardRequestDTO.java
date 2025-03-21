package com.project.demo.api.board.application.dto;

import com.project.demo.common.BaseDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "게시글 검색 조회를 위한 DTO")
public class BoardRequestDTO extends BaseDTO {

    
}
