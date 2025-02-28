package com.project.demo.api.menu.application.dto;

import com.querydsl.core.annotations.QueryProjection;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "헤더에서 사용할 동적 메뉴 정보 DTO")
public class MenuDisplayDTO {

    @Schema(description = "메뉴 고유 식별자")
    private final Long menuSeq;

    @Schema(description = "메뉴 이름")
    private final String menuNm;

    @Schema(description = "메뉴 URL")
    private final String menuUrl;

    @Schema(description = "부모 메뉴 ID (최상위 메뉴일 경우 null)")
    private final Long parentSeq;
    
    @QueryProjection
    public MenuDisplayDTO(Long menuSeq, String menuNm, String menuUrl, Long parentSeq) {
        this.menuSeq = menuSeq;
        this.menuNm = menuNm;
        this.menuUrl = menuUrl;
        this.parentSeq = parentSeq;
    }
}
