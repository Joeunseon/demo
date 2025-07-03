TRUNCATE TABLE users;
ALTER SEQUENCE user_sequence RESTART WITH 1;

TRUNCATE TABLE menu_role;
ALTER SEQUENCE menu_role_sequence RESTART WITH 1;

TRUNCATE TABLE roles;
ALTER SEQUENCE role_sequence RESTART WITH 1;

TRUNCATE TABLE menus;
ALTER SEQUENCE menu_sequence RESTART WITH 1;

TRUNCATE TABLE cmm_cd;
ALTER SEQUENCE cd_sequence RESTART WITH 1;

TRUNCATE TABLE cmm_cd_grp;
ALTER SEQUENCE cd_grp_sequence RESTART WITH 1;

TRUNCATE TABLE err_log;
ALTER SEQUENCE err_log_sequence RESTART WITH 1;

TRUNCATE TABLE board;
ALTER SEQUENCE board_sequence RESTART WITH 1;

TRUNCATE TABLE file_dtl;
ALTER SEQUENCE file_dtl_sequence RESTART WITH 1;

TRUNCATE TABLE file_mstr;
ALTER SEQUENCE file_sequence RESTART WITH 1;


/**
* 권한 등록
*/
-- 시스템 관리자
INSERT INTO public.roles
(role_nm, role_desc, reg_seq)
VALUES
('SYSTEM', '시스템관리자', 1);
-- 기본 관리자
INSERT INTO public.roles
(role_nm, role_desc, reg_seq)
VALUES
('ADMIN', '기본관리자', 1);
-- 기본사용자(로그인)
INSERT INTO public.roles
(role_nm, role_desc, reg_seq)
VALUES
('USER', '기본사용자(로그인)', 1);
-- 기본사용자(비로그인)
INSERT INTO public.roles
(role_nm, role_desc, reg_seq)
VALUES
('GUEST', '기본사용자(비로그인)', 1);


/**
* 사용자 등록
*/
-- 시스템 권한
INSERT INTO users
(role_seq, user_id, user_pwd, user_nm)
VALUES
(1, 'system', '$2a$12$6R5b2Om09VMfduzFtX2mQOQHE6NkaD1VHxCehYN4PNGTdG8lm2Oa6', '시스템');
-- 관리자 권한
INSERT INTO users
(role_seq, user_id, user_pwd, user_nm)
VALUES
(2, 'admin', '$2a$12$6R5b2Om09VMfduzFtX2mQOQHE6NkaD1VHxCehYN4PNGTdG8lm2Oa6', '관리자');
-- 사용자 권한
INSERT INTO users
(role_seq, user_id, user_pwd, user_nm)
VALUES
(3, 'user', '$2a$12$6R5b2Om09VMfduzFtX2mQOQHE6NkaD1VHxCehYN4PNGTdG8lm2Oa6', '사용자');


/**
* 메뉴 등록
*/
-- 메인
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES
('ROOT', '/', NULL, 1, 1, 'PAGE', 1);

-- 로그인
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES
('로그인', '/login', 1, 2, 10, 'PAGE', 1);
-- 로그인 API
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES('로그인 API', '/api/login', (SELECT menu_seq FROM menus WHERE menu_nm = '로그인' LIMIT 1), 3, 1, 'CREATE', 1);
-- 로그아웃 API
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES('로그아웃 API', '/api/loginout', (SELECT menu_seq FROM menus WHERE menu_nm = '로그인' LIMIT 1), 3, 2, 'DELETE', 1);

-- 회원가입
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES
('회원가입', '/signup', 1, 2, 20, 'PAGE', 1);
-- 회원가입 API
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES('회원가입 API', '/api/signup', (SELECT menu_seq FROM menus WHERE menu_nm = '회원가입' LIMIT 1), 3, 1, 'CREATE', 1);
-- 회원가입 중복체크 API
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES('회원가입 중복체크 API', '/api/signup/check-duplicate', (SELECT menu_seq FROM menus WHERE menu_nm = '회원가입' LIMIT 1), 3, 2, 'READ', 1);

-- 게시판
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES('게시판', NULL, 1, 2, 30, 'MENU', 1);
-- 게시판 목록
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES('게시판 목록', '/board/list', (SELECT menu_seq FROM menus WHERE menu_nm = '게시판' LIMIT 1), 3, 1, 'PAGE', 1);
-- 게시판 등록
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES('게시판 등록', '/board/regist', (SELECT menu_seq FROM menus WHERE menu_nm = '게시판' LIMIT 1), 3, 2, 'PAGE', 1);
-- 게시판 상세
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES('게시판 상세', '/board/info', (SELECT menu_seq FROM menus WHERE menu_nm = '게시판' LIMIT 1), 3, 3, 'PAGE', 1);
-- 게시판 수정
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES('게시판 수정', '/board/edit', (SELECT menu_seq FROM menus WHERE menu_nm = '게시판' LIMIT 1), 3, 4, 'PAGE', 1);
-- 게시판 목록 API
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES('게시판 목록 API', '/api/boards', (SELECT menu_seq FROM menus WHERE menu_nm = '게시판 목록' LIMIT 1), 4, 1, 'READ', 1);
-- 게시판 등록 API
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES('게시판 등록 API', '/api/board', (SELECT menu_seq FROM menus WHERE menu_nm = '게시판 등록' LIMIT 1),, 4, 1, 'CREATE', 1);
-- 게시판 상세 API
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES('게시판 상세 API', '/api/board/{boardSeq}', (SELECT menu_seq FROM menus WHERE menu_nm = '게시판 상세' LIMIT 1), 4, 1, 'READ', 1);
-- 게시판 수정 API
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES('게시판 수정 API', '/api/board', (SELECT menu_seq FROM menus WHERE menu_nm = '게시판 수정' LIMIT 1), 4, 1, 'UPDATE', 1);
-- 게시판 삭제 API
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES('게시판 삭제 API', '/api/board/{boardSeq}', (SELECT menu_seq FROM menus WHERE menu_nm = '게시판 상세' LIMIT 1), 4, 2, 'DELETE', 1);

-- 암호화 관리
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES('암호화 관리', '/encryption', 1, 2, 40, 'MENU', 1);
-- 암호화 API (jasypt)
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES('암호화 API (jasypt)', '/api/jasypt/encrypt', (SELECT menu_seq FROM menus WHERE menu_nm = '암호화 관리' LIMIT 1), 3, 1, 'CREATE', 1);
-- 복호화 API (jasypt)
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES('복호화 API (jasypt)', '/api/jasypt/decrypt', (SELECT menu_seq FROM menus WHERE menu_nm = '암호화 관리' LIMIT 1), 3, 2, 'CREATE', 1);
-- 암호화 API (bcrypt)
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES('암호화 API (bcrypt)', '/api/bcrypt/encrypt', (SELECT menu_seq FROM menus WHERE menu_nm = '암호화 관리' LIMIT 1), 3, 3, 'CREATE', 1);
-- 일치확인 API (bcrypt)
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES('일치확인 API (bcrypt)', '/api/bcrypt/matches', (SELECT menu_seq FROM menus WHERE menu_nm = '암호화 관리' LIMIT 1), 3, 4, 'CREATE', 1);
-- 인코딩 API (base64)
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES('인코딩 API (base64)', '/api/base64/encode', (SELECT menu_seq FROM menus WHERE menu_nm = '암호화 관리' LIMIT 1), 3, 5, 'CREATE', 1);
-- 디코딩 API (base64)
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES('디코딩 API (base64)', '/api/base64/decode', (SELECT menu_seq FROM menus WHERE menu_nm = '암호화 관리' LIMIT 1), 3, 6, 'CREATE', 1);
-- 암호화 API (hash)
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES('암호화 API (hash)', '/api/hash/encrypt', (SELECT menu_seq FROM menus WHERE menu_nm = '암호화 관리' LIMIT 1), 3, 7, 'CREATE', 1);
-- 일치확인 API (hash)
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES('일치확인 API (hash)', '/api/hash/matches', (SELECT menu_seq FROM menus WHERE menu_nm = '암호화 관리' LIMIT 1), 3, 8, 'CREATE', 1);
-- 암호화 API (RSA)
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES('암호화 API (RSA)', '/api/rsa/encrypt', (SELECT menu_seq FROM menus WHERE menu_nm = '암호화 관리' LIMIT 1), 3, 9, 'CREATE', 1);
-- 복호화 API (RSA)
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES('복호화 API (RSA)', '/api/rsa/decrypt', (SELECT menu_seq FROM menus WHERE menu_nm = '암호화 관리' LIMIT 1), 3, 10, 'CREATE', 1);

-- 메뉴 관리
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES('메뉴 관리', NULL, 1, 2, 50, 'MENU', 1);
-- 메뉴 목록
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES('메뉴 목록', '/menu/list', (SELECT menu_seq FROM menus WHERE menu_nm = '메뉴 관리' LIMIT 1), 3, 1, 'PAGE', 1);
-- 메뉴 등록
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES('메뉴 등록', '/menu/regist', (SELECT menu_seq FROM menus WHERE menu_nm = '메뉴 관리' LIMIT 1), 3, 2, 'PAGE', 1);
-- 메뉴 상세
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES('메뉴 상세', '/menu/info', (SELECT menu_seq FROM menus WHERE menu_nm = '메뉴 관리' LIMIT 1), 3, 3, 'PAGE', 1);
-- 메뉴 수정
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES('메뉴 수정', '/menu/edit', (SELECT menu_seq FROM menus WHERE menu_nm = '메뉴 관리' LIMIT 1), 3, 4, 'PAGE', 1);
-- 메뉴 목록 API
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES('메뉴 목록 API', '/api/menus', (SELECT menu_seq FROM menus WHERE menu_nm = '메뉴 목록' LIMIT 1), 4, 1, 'READ', 1);
-- 메뉴 등록 API
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES('메뉴 등록 API', '/api/menu', (SELECT menu_seq FROM menus WHERE menu_nm = '메뉴 등록' LIMIT 1), 4, 1, 'CREATE', 1);
-- 메뉴 중복체크 API
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES('메뉴 중복체크 API', '/api/menu/check-duplicate', (SELECT menu_seq FROM menus WHERE menu_nm = '메뉴 등록' LIMIT 1), 4, 2, 'READ', 1);
-- 메뉴 상세 API
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES('메뉴 상세 API', '/api/menu/{menuSeq}', (SELECT menu_seq FROM menus WHERE menu_nm = '메뉴 상세' LIMIT 1), 4, 1, 'READ', 1);
-- 하위 메뉴 API
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES('하위 메뉴 API', '/api/menus/{parentSeq}/children', (SELECT menu_seq FROM menus WHERE menu_nm = '메뉴 상세' LIMIT 1), 4, 2, 'READ', 1);
-- 메뉴 수정 API
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES('메뉴 수정 API', '/api/menu', (SELECT menu_seq FROM menus WHERE menu_nm = '메뉴 수정' LIMIT 1), 4, 1, 'UPDATE', 1);
-- 메뉴 순서 변경 API
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES('메뉴 순서 변경 API', '/api/order', (SELECT menu_seq FROM menus WHERE menu_nm = '메뉴 목록' LIMIT 1), 4, 2, 'UPDATE', 1);
-- 메뉴 트리 API
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES('메뉴 트리 API', '/api/menus/tree', (SELECT menu_seq FROM menus WHERE menu_nm = '메뉴 목록' LIMIT 1), 4, 3, 'READ', 1);

-- 권한 관리
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES('권한 관리', NULL, 1, 2, 60, 'MENU', 1);
-- 권한 목록
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES('권한 목록', '/role/list', (SELECT menu_seq FROM menus WHERE menu_nm = '권한 관리' LIMIT 1), 3, 1, 'PAGE', 1);
-- 권한 등록
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES('권한 등록', '/role/regist', (SELECT menu_seq FROM menus WHERE menu_nm = '권한 관리' LIMIT 1), 3, 2, 'PAGE', 1);
-- 권한 상세
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES('권한 상세', '/role/info', (SELECT menu_seq FROM menus WHERE menu_nm = '권한 관리' LIMIT 1), 3, 3, 'PAGE', 1);
-- 권한 수정
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES('권한 수정', '/role/edit', (SELECT menu_seq FROM menus WHERE menu_nm = '권한 관리' LIMIT 1), 3, 4, 'PAGE', 1);
-- 권한 목록 API
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES('권한 목록 API', '/api/roles', (SELECT menu_seq FROM menus WHERE menu_nm = '권한 목록' LIMIT 1), 4, 1, 'READ', 1);
-- 권한 등록 API
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES('권한 등록 API', '/api/role', (SELECT menu_seq FROM menus WHERE menu_nm = '권한 등록' LIMIT 1), 4, 1, 'CREATE', 1);
-- 권한 중복체크 API
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES('권한 중복체크 API', '/api/role/check-duplicate', (SELECT menu_seq FROM menus WHERE menu_nm = '권한 등록' LIMIT 1), 4, 2, 'READ', 1);
-- 권한 상세 API
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES('권한 상세 API', '/api/role/{roleSeq}', (SELECT menu_seq FROM menus WHERE menu_nm = '권한 상세' LIMIT 1), 4, 1, 'READ', 1);
-- 권한 수정 API
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES('권한 수정 API', '/api/role', (SELECT menu_seq FROM menus WHERE menu_nm = '권한 수정' LIMIT 1), 4, 1, 'UPDATE', 1);
-- 권한 삭제 API
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES('권한 삭제 API', '/api/role/{roleSeq}', (SELECT menu_seq FROM menus WHERE menu_nm = '권한 상세' LIMIT 1), 4, 2, 'DELETE', 1);
-- 권한별 메뉴 조회 API
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES('권한별 메뉴 조회 API', '/api/role/{roleSeq}/menus', (SELECT menu_seq FROM menus WHERE menu_nm = '권한 상세' LIMIT 1), 4, 3, 'READ', 1);

-- 사용자 관리
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES('사용자 관리', NULL, 1, 2, 70, 'MENU', 1);
-- 사용자 목록
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES('사용자 목록', '/user/list', (SELECT menu_seq FROM menus WHERE menu_nm = '사용자 관리' LIMIT 1), 3, 1, 'PAGE', 1);
-- 사용자 등록
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES('사용자 등록', '/user/regist', (SELECT menu_seq FROM menus WHERE menu_nm = '사용자 관리' LIMIT 1), 3, 2, 'PAGE', 1);
-- 사용자 상세
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES('사용자 상세', '/user/info', (SELECT menu_seq FROM menus WHERE menu_nm = '사용자 관리' LIMIT 1), 3, 3, 'PAGE', 1);
-- 사용자 수정
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES('사용자 수정', '/user/edit', (SELECT menu_seq FROM menus WHERE menu_nm = '사용자 관리' LIMIT 1), 3, 4, 'PAGE', 1);
-- 사용자 목록 API
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES('사용자 목록 API', '/api/users', (SELECT menu_seq FROM menus WHERE menu_nm = '사용자 목록' LIMIT 1), 4, 1, 'READ', 1);
-- 사용자 등록 API
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES('사용자 등록 API', '/api/user', (SELECT menu_seq FROM menus WHERE menu_nm = '사용자 등록' LIMIT 1), 4, 1, 'CREATE', 1);
-- 사용자 상세 API
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES('사용자 상세 API', '/api/user/{userSeq}', (SELECT menu_seq FROM menus WHERE menu_nm = '사용자 상세' LIMIT 1), 4, 1, 'READ', 1);
-- 사용자 비밀번호 초기화 API (관리자)
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES('사용자 비밀번호 초기화 API (관리자)', '/api/user/{userSeq}/password/init', (SELECT menu_seq FROM menus WHERE menu_nm = '사용자 상세' LIMIT 1), 4, 2, 'UPDATE', 1);
-- 사용자 수정 API
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES('사용자 수정 API', '/api/user', (SELECT menu_seq FROM menus WHERE menu_nm = '사용자 수정' LIMIT 1), 4, 1, 'UPDATE', 1);
-- 사용자 삭제 API
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES('사용자 삭제 API', '/api/user/{userSeq}', (SELECT menu_seq FROM menus WHERE menu_nm = '사용자 상세' LIMIT 1), 4, 2, 'DELETE', 1);

-- 로그 관리
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES('로그 관리', NULL, 1, 2, 80, 'MENU', 1);
-- 로그 목록
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES('로그 목록', '/log/list', (SELECT menu_seq FROM menus WHERE menu_nm = '로그 관리' LIMIT 1), 3, 1, 'PAGE', 1);
-- 로그 상세
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES('로그 상세', '/log/info', (SELECT menu_seq FROM menus WHERE menu_nm = '로그 관리' LIMIT 1), 3, 2, 'PAGE', 1);
-- 로그 수정
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES('로그 수정', '/log/edit', (SELECT menu_seq FROM menus WHERE menu_nm = '로그 관리' LIMIT 1), 3, 3, 'PAGE', 1);
-- 로그 목록 API
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES('로그 목록 API', '/api/logs', (SELECT menu_seq FROM menus WHERE menu_nm = '로그 목록' LIMIT 1), 4, 1, 'READ', 1);
-- 로그 상세 API
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES('로그 상세 API', '/api/log/{logSeq}', (SELECT menu_seq FROM menus WHERE menu_nm = '로그 상세' LIMIT 1), 4, 1, 'READ', 1);
-- 로그 수정 API
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES('로그 수정 API', '/api/log', (SELECT menu_seq FROM menus WHERE menu_nm = '로그 수정' LIMIT 1), 4, 1, 'UPDATE', 1);
-- 로그 상태 수정 API
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES('로그 상태 수정 API', '/api/log/resolve', (SELECT menu_seq FROM menus WHERE menu_nm = '로그 수정' LIMIT 1), 4, 2, 'UPDATE', 1);

-- 관리자 도구
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES ('관리자 도구', NULL, 1, 2, 90, 'TOOL', 1);
-- Swagger API UI
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES('Swagger API UI', '/swagger-ui', (SELECT menu_seq FROM menus WHERE menu_nm = '관리자 도구' LIMIT 1), 3, 1, 'TOOL', 1);
-- Swagger API ALL
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES ('Swagger API ALL', '/swagger-ui/**', (SELECT menu_seq FROM menus WHERE menu_nm = 'Swagger API UI' LIMIT 1), 4, 1, 'TOOL', 1);
-- Swagger API Docs
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES ('Swagger API Docs', '/v3/api-docs', (SELECT menu_seq FROM menus WHERE menu_nm = 'Swagger API UI' LIMIT 1), 4, 2, 'TOOL', 1);
-- Swagger API All
INSERT INTO menus (menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq) 
VALUES ('Swagger API Docs All', '/v3/api-docs/**', (SELECT menu_seq FROM menus WHERE menu_nm = 'Swagger API UI' LIMIT 1), 4, 3, 'TOOL', 1);
-- Swagger Resources
INSERT INTO menus (menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq) 
VALUES ('Swagger Resources', '/swagger-resources', (SELECT menu_seq FROM menus WHERE menu_nm = 'Swagger API UI' LIMIT 1), 4, 4, 'TOOL', 1);
-- Swagger Resources All
INSERT INTO menus (menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq) 
VALUES ('Swagger Resources All', '/swagger-resources/**', (SELECT menu_seq FROM menus WHERE menu_nm = 'Swagger API UI' LIMIT 1), 4, 5, 'TOOL', 1);
-- Swagger Resources WebJars
INSERT INTO menus (menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq) 
VALUES ('Swagger WebJars', '/webjars/**', (SELECT menu_seq FROM menus WHERE menu_nm = 'Swagger API UI' LIMIT 1), 4, 6, 'TOOL', 1);

-- 내 정보
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES
('내 정보', null, 1, 2, 100, 'MENU', 1);
-- 비밀번호 일치 확인
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES
('비밀번호 일치 확인', '/account/verify-password', (SELECT menu_seq FROM menus WHERE menu_nm = '내 정보' LIMIT 1), 3, 1, 'PAGE', 1);
-- 내 정보 상세(마이페이지)
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES
('내 정보 상세(마이페이지)', '/account/mypage', (SELECT menu_seq FROM menus WHERE menu_nm = '내 정보' LIMIT 1), 3, 2, 'PAGE', 1);
-- 내 정보 수정
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES
('내 정보 수정', '/account/profile/edit', (SELECT menu_seq FROM menus WHERE menu_nm = '내 정보' LIMIT 1), 3, 3, 'PAGE', 1);
-- 비밀번호 변경
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES
('비밀번호 변경', '/account/password', (SELECT menu_seq FROM menus WHERE menu_nm = '내 정보' LIMIT 1), 3, 4, 'PAGE', 1);
-- 비밀번호 일치 확인 API
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES
('비밀번호 일치 확인 API', '/api/account/password/verify', (SELECT menu_seq FROM menus WHERE menu_nm = '비밀번호 일치 확인' LIMIT 1), 4, 1, 'TOOL', 1);
-- 내 정보 상세(마이페이지) API
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES
('내 정보 상세(마이페이지) API', '/api/user/me', (SELECT menu_seq FROM menus WHERE menu_nm = '내 정보 상세(마이페이지)' LIMIT 1), 4, 1, 'READ', 1);
-- 내 정보 수정 API
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES
('내 정보 수정 API', '/api/user/me', (SELECT menu_seq FROM menus WHERE menu_nm = '내 정보 수정' LIMIT 1), 4, 1, 'UPDATE', 1);
-- 비밀번호 변경 API
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES
('비밀번호 변경 API', '/api/account/password', (SELECT menu_seq FROM menus WHERE menu_nm = '비밀번호 변경' LIMIT 1), 4, 1, 'UPDATE', 1);
-- 비밀번호 다음에 변경 API
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES
('비밀번호 다음에 변경 API', '/api/account/password/delay', (SELECT menu_seq FROM menus WHERE menu_nm = '비밀번호 변경' LIMIT 1), 4, 2, 'UPDATE', 1);

-- 공통 API
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES ('공통 API', NULL, (SELECT menu_seq FROM menus WHERE menu_nm = '관리자 도구' LIMIT 1), 3, 2, 'TOOL', 1);
-- 파일 등록 API
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES ('파일 등록 API', '/api/file', (SELECT menu_seq FROM menus WHERE menu_nm = '공통 API' LIMIT 1), 4, 1, 'CREATE', 1);
-- 파일 상세 API
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES ('파일 상세 API', '/api/file/{fileSeq}', (SELECT menu_seq FROM menus WHERE menu_nm = '공통 API' LIMIT 1), 4, 2, 'READ', 1);
-- 파일 개별 다운로드 API
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES ('파일 개별 다운로드 API', '/api/file/download/{dtlSeq}', (SELECT menu_seq FROM menus WHERE menu_nm = '공통 API' LIMIT 1), 4, 3, 'READ', 1);
-- 파일 압축 다운로드 API
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES ('파일 압축 다운로드 API', '/api/file/download/zip', (SELECT menu_seq FROM menus WHERE menu_nm = '공통 API' LIMIT 1), 4, 4, 'READ', 1);
-- 파일 수정 API
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES ('파일 수정 API', '/api/file', (SELECT menu_seq FROM menus WHERE menu_nm = '공통 API' LIMIT 1), 4, 5, 'UPDATE', 1);
-- 셀렉트박스 조회 (enum)
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES('셀렉트박스 조회 API (enum)', '/api/selectbox/enum', (SELECT menu_seq FROM menus WHERE menu_nm = '공통 API' LIMIT 1), 4, 6, 'READ', 1);
-- 셀렉트박스 조회 (code)
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES('셀렉트박스 조회 API (code)', '/api/selectbox/code', (SELECT menu_seq FROM menus WHERE menu_nm = '공통 API' LIMIT 1), 4, 7, 'READ', 1);
-- 셀렉트박스 조회 (role)
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES('셀렉트박스 조회 API (role)', '/api/selectbox/role', (SELECT menu_seq FROM menus WHERE menu_nm = '공통 API' LIMIT 1), 4, 8, 'READ', 1);

-- 코드 관리
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES
('코드 관리', null, 1, 2, 110, 'MENU', 1);
-- 코드 목록
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES('코드 목록', '/code/list', (SELECT menu_seq FROM menus WHERE menu_nm = '코드 관리' LIMIT 1), 3, 1, 'PAGE', 1);
-- 코드 등록
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES('코드 등록', '/code/regist', (SELECT menu_seq FROM menus WHERE menu_nm = '코드 관리' LIMIT 1), 3, 2, 'PAGE', 1);
-- 코드 상세
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES('코드 상세', '/code/info', (SELECT menu_seq FROM menus WHERE menu_nm = '코드 관리' LIMIT 1), 3, 3, 'PAGE', 1);
-- 코드 수정
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES('코드 수정', '/code/edit', (SELECT menu_seq FROM menus WHERE menu_nm = '코드 관리' LIMIT 1), 3, 4, 'PAGE', 1);
-- 코드 목록 API
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES('코드 목록 API', '/api/codes', (SELECT menu_seq FROM menus WHERE menu_nm = '코드 목록' LIMIT 1), 4, 1, 'READ', 1);
-- 그룹 코드 등록 API
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES('그룹 코드 등록 API', '/api/code-group', (SELECT menu_seq FROM menus WHERE menu_nm = '코드 등록' LIMIT 1), 4, 1, 'CREATE', 1);
-- 코드 등록 API
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES('코드 등록 API', '/api/code', (SELECT menu_seq FROM menus WHERE menu_nm = '코드 등록' LIMIT 1), 4, 2, 'CREATE', 1);
-- 그룹 코드 상세 API
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES('그룹 코드 상세 API', '/api/code-group/{grpSeq}', (SELECT menu_seq FROM menus WHERE menu_nm = '코드 상세' LIMIT 1), 4, 1, 'READ', 1);
-- 코드 상세 API
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES('코드 상세 API', '/api/code/{cdSeq}', (SELECT menu_seq FROM menus WHERE menu_nm = '코드 상세' LIMIT 1), 4, 2, 'READ', 1);
-- 그룹 코드 수정 API
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES('그룹 코드 수정 API', '/api/code-group', (SELECT menu_seq FROM menus WHERE menu_nm = '코드 수정' LIMIT 1), 4, 1, 'UPDATE', 1);
-- 코드 수정 API
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES('코드 수정 API', '/api/code', (SELECT menu_seq FROM menus WHERE menu_nm = '코드 수정' LIMIT 1), 4, 2, 'UPDATE', 1);
-- 그룹 코드 삭제 API
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES('그룹 코드 삭제 API', '/api/code-group/{grpSeq}', (SELECT menu_seq FROM menus WHERE menu_nm = '코드 상세' LIMIT 1), 4, 3, 'DELETE', 1);
-- 코드 삭제 API
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES('코드 삭제 API', '/api/code/{cdSeq}', (SELECT menu_seq FROM menus WHERE menu_nm = '코드 상세' LIMIT 1), 4, 4, 'DELETE', 1);


/**
* 메뉴 권한 등록
*/
-- 시스템관리자 메뉴 권한 등록
INSERT INTO menu_role 
(menu_seq, role_seq, reg_seq)
SELECT m.menu_seq, 1, 1
FROM menus m
ORDER BY m.menu_seq;

-- 관리자 메뉴 권한 등록
-- (개발 완료 후 권한관리에서 적용 필요)

-- 사용자(로그인) 메뉴 권한 등록
-- (개발 완료 후 권한관리에서 적용 필요)

-- 사용자(비로그인) 메뉴 권한 등록
INSERT INTO menu_role (menu_seq, role_seq, reg_seq)
SELECT m.menu_seq, 4, 1
FROM menus m
WHERE m.menu_nm IN ('ROOT', '로그인', '로그인 API', '로그아웃 API', '회원가입', '회원가입 API', '회원가입 중복체크 API', '게시판', '게시판 목록', '게시판 상세', '게시판 목록 API', '게시판 상세 API', '암호화 관리', '암호화 API', '복호화 API', '파일 상세 API')
ORDER BY m.menu_seq;