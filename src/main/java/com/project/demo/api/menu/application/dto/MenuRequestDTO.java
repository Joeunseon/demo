package com.project.demo.api.menu.application.dto;

import com.project.demo.common.BaseDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "메뉴 검색 조회를 위한 DTO")
public class MenuRequestDTO extends BaseDTO {

}
