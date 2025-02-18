package com.project.demo.common;

import java.io.Serializable;

import com.project.demo.common.constant.SearchCondition;
import com.project.demo.config.security.application.dto.UserSessionDTO;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BaseDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final int PAGE = 1;
    private static final int DEFAULT_PAGE_SCALE = 10;

    /**
     * 로그인 사용자 정보
     */
    private UserSessionDTO userSessionDTO;

    /**
     * 조회페이지
     */
    private Integer page = PAGE;

    /**
     * 페이지당 게시물수
     */
    private Integer pageScale = DEFAULT_PAGE_SCALE;

    /**
     * 페이지 조회 시작 번호
     */
    private Integer offSet;

    /**
     * 검색 기간
     */
    private String searchStartDt;
    private String searchEndDt;

    /**
     * 검색 조건
     */
    @Enumerated(EnumType.STRING)
    private SearchCondition searchCondition;

    /**
     * 검색 키워드
     */
    private String searchKeyword;

    public void calculateOffSet() {
        this.offSet = (this.page - 1) * this.pageScale;
    }

    public void setUserSession(UserSessionDTO userSessionDTO) {
        this.userSessionDTO = userSessionDTO;
    }
}
