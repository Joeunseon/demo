package com.project.demo.common.constant;

import java.util.List;

/** 
 * 공통 상수
 */
public class CommonConstant {

    /** 결과 */
    public static class RESULT {
        /** 정상 */
        public static final boolean OK = true;

        /** 에러 */
        public static final boolean ERR = false;

        /** 결과 */
        public static final String RESULT = "result";

        /** 메시지 */
        public static final String MSG = "message";

        /** 메시지 키 */
        public static final String MSG_KEY = "msgKey";
    }

    /** 세션키 */
    public static class SESSION_KEY {
        /** 프런트 */
        public static final String FRONT = "SESSION_KEY_FRONT";

        /** 게시판 */
        public static final String BOARD = "SESSION_KEY_BOARD";
    }

    /** Cache 키 */
    public static class CACHE_KEY {
        /** 시큐리티 */
        public static final String SECURITY = "permitAllUrls";
    }

    /** ROLE 키 */
    public static class ROLE_KEY {
        /** 시스템 */
        public static final Integer SYSTEM = 1;

        /** 관리자 */
        public static final Integer ADMIN = 2;

        /** 사용자 */
        public static final Integer USER = 3;

        /** 게스트 */
        public static final Integer GUEST = 4;
    }

    /** Model 속성 키 */
    public static class MODEL_KEY {
        /** 리다이렉트 URL */
        public static final String REDIRECT_URL = "redirectUrl";

        /** 메뉴 리스트 */
        public static final String MENU_LIST = "menuList";

        /** 로그인 정보 */
        public static final String LOGIN_INFO = "loginInfo";

        /** 페이지 플러그인 */
        public static final String PAGE_PLUGIN = "pagePlugin";

        /** 결과 리스트 */
        public static final String RESULT_LIST = "resultList";

        /** total count */
        public static final String TOTAL_CNT = "totalCnt";

        /** 현재 페이지 */
        public static final String CURRENT_PAGE = "currentPage";

        /** 페이지 스케일 */
        public static final String PAGE_SCALE = "pageScale";

        /** DATA */
        public static final String DATA = "data";

        /** 수정/삭제 권한 */
        public static final String EDITABLE = "editable";
    }

    /** 제외할 URL 목록 */
    public static class EXCLUDE_URL {
        /** 에러페이지 */
        public static final String ERROR = "/error";

        /** pagination */
        public static final String PAGE = "/pagination";

        /** API */
        public static final String API = "/api";

        /** 회원가입 */
        public static final String SIGNUP = "/signup";

        /** 로그인 */
        public static final String LOGIN = "/login";

        /** 리스트 적용 */
        public static final List<String> EXCLUDE_URL_LIST = List.of(ERROR, API, PAGE);
    }
}
