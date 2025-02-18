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
VALUES('로그인 API', '/api/login', 2, 3, 1, 'READ', 1);
-- 로그아웃 API
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES('로그아웃 API', '/api/loginout', 2, 3, 2, 'READ', 1);

-- 회원가입
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES
('회원가입', '/register', 1, 2, 20, 'PAGE', 1);
-- 회원가입 API
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES('회원가입 API', '/api/register', 5, 3, 1, 'CREATE', 1);

-- 게시판
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES('게시판', NULL, 1, 2, 30, 'PAGE', 1);
-- 게시판 목록
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES('게시판 목록', '/board/list', 7, 3, 1, 'PAGE', 1);
-- 게시판 등록
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES('게시판 등록', '/board/regist', 7, 3, 2, 'PAGE', 1);
-- 게시판 상세
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES('게시판 상세', '/board/info', 7, 3, 3, 'PAGE', 1);
-- 게시판 수정
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES('게시판 수정', '/board/edit', 7, 3, 4, 'PAGE', 1);
-- 게시판 목록 API
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES('게시판 목록 API', '/api/boards', 8, 4, 1, 'READ', 1);
-- 게시판 등록 API
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES('게시판 등록 API', '/api/board', 9, 4, 1, 'CREATE', 1);
-- 게시판 상세 API
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES('게시판 상세 API', '/api/board/{boardSeq}', 10, 4, 1, 'READ', 1);
-- 게시판 수정 API
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES('게시판 수정 API', '/api/board/{boardSeq}', 11, 4, 1, 'UPDATE', 1);
-- 게시판 삭제 API
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES('게시판 삭제 API', '/api/board/{boardSeq}', 10, 4, 2, 'DELETE', 1);

-- 암호화 관리
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES('암호화 관리', '/encryption', 1, 2, 40, 'PAGE', 1);
-- 암호화 API 
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES('암호화 API', '/api/encryption', 17, 3, 1, 'READ', 1);
-- 복호화 API
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES('암호화 API', '/api/decryption', 17, 3, 2, 'READ', 1);

-- 메뉴 관리
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES('메뉴 관리', NULL, 1, 2, 50, 'PAGE', 1);
-- 메뉴 목록
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES('메뉴 목록', '/menu/list', 20, 3, 1, 'PAGE', 1);
-- 메뉴 등록
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES('메뉴 등록', '/menu/regist', 20, 3, 2, 'PAGE', 1);
-- 메뉴 상세
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES('메뉴 상세', '/menu/info', 20, 3, 3, 'PAGE', 1);
-- 메뉴 수정
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES('메뉴 수정', '/menu/edit', 20, 3, 4, 'PAGE', 1);
-- 메뉴 목록 API
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES('메뉴 목록 API', '/api/menus', 21, 4, 1, 'READ', 1);
-- 메뉴 등록 API
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES('메뉴 등록 API', '/api/menu', 22, 4, 1, 'CREATE', 1);
-- 메뉴 상세 API
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES('메뉴 상세 API', '/api/menu/{menuSeq}', 23, 4, 1, 'READ', 1);
-- 메뉴 수정 API
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES('메뉴 수정 API', '/api/menu/{menuSeq}', 24, 4, 1, 'UPDATE', 1);
-- 메뉴 순서 변경 API
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES('메뉴 순서 변경 API', '/api/order', 21, 4, 2, 'UPDATE', 1);

-- 권한 관리
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES('권한 관리', NULL, 1, 2, 60, 'PAGE', 1);
-- 권한 목록
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES('권한 목록', '/role/list', 30, 3, 1, 'PAGE', 1);
-- 권한 등록
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES('권한 등록', '/role/regist', 30, 3, 2, 'PAGE', 1);
-- 권한 상세
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES('권한 상세', '/role/info', 30, 3, 3, 'PAGE', 1);
-- 권한 수정
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES('권한 수정', '/role/edit', 30, 3, 4, 'PAGE', 1);
-- 권한 목록 API
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES('권한 목록 API', '/api/roles', 31, 4, 1, 'READ', 1);
-- 권한 등록 API
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES('권한 등록 API', '/api/role', 32, 4, 1, 'CREATE', 1);
-- 권한 상세 API
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES('권한 상세 API', '/api/role/{roleSeq}', 33, 4, 1, 'READ', 1);
-- 권한 수정 API
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES('권한 수정 API', '/api/role/{roleSeq}', 34, 4, 1, 'UPDATE', 1);
-- 권한 삭제 API
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES('권한 삭제 API', '/api/role/{roleSeq}', 33, 4, 2, 'DELETE', 1);

-- 사용자 관리
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES('사용자 관리', NULL, 1, 2, 70, 'PAGE', 1);
-- 사용자 목록
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES('사용자 목록', '/user/list', 40, 3, 1, 'PAGE', 1);
-- 사용자 등록
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES('사용자 등록', '/user/regist', 40, 3, 2, 'PAGE', 1);
-- 사용자 상세
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES('사용자 상세', '/user/info', 40, 3, 3, 'PAGE', 1);
-- 사용자 수정
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES('사용자 수정', '/user/edit', 40, 3, 4, 'PAGE', 1);
-- 사용자 목록 API
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES('사용자 목록 API', '/api/users', 41, 4, 1, 'READ', 1);
-- 사용자 등록 API
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES('사용자 등록 API', '/api/user', 42, 4, 1, 'CREATE', 1);
-- 사용자 상세 API
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES('사용자 상세 API', '/api/user/{userSeq}', 43, 4, 1, 'READ', 1);
-- 사용자 수정 API
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES('사용자 수정 API', '/api/user/{userSeq}', 44, 4, 1, 'UPDATE', 1);
-- 사용자 삭제 API
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES('사용자 삭제 API', '/api/user/{userSeq}', 43, 4, 2, 'DELETE', 1);

-- 로그 관리
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES('로그 관리', NULL, 1, 2, 80, 'PAGE', 1);
-- 로그 목록
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES('로그 목록', '/log/list', 50, 3, 1, 'PAGE', 1);
-- 로그 상세
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES('로그 상세', '/log/info', 50, 3, 2, 'PAGE', 1);
-- 로그 수정
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES('로그 수정', '/log/edit', 50, 3, 3, 'PAGE', 1);
-- 로그 목록 API
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES('로그 목록 API', '/api/logs', 51, 4, 1, 'READ', 1);
-- 로그 상세 API
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES('로그 상세 API', '/api/log/{logSeq}', 52, 4, 1, 'READ', 1);
-- 로그 수정 API
INSERT INTO menus
(menu_nm, menu_url, parent_seq, menu_level, menu_order, menu_type, reg_seq)
VALUES('로그 수정 API', '/api/log/{logSeq}', 53, 4, 1, 'UPDATE', 1);


/**
* 메뉴 권한 등록
*/
-- 시스템관리자 메뉴 권한 등록
INSERT INTO menu_role 
(menu_seq, role_seq, reg_seq)
SELECT m.menu_seq, 1, 1
FROM menus m
ORDER BY m.menu_seq;

-- 메뉴 관리자 메뉴 권한 등록
INSERT INTO menu_role 
(menu_seq, role_seq, reg_seq)
SELECT m.menu_seq, 2, 1
FROM menus m
WHERE m.menu_seq < 50
ORDER BY m.menu_seq;

-- 사용자(로그인) 메뉴 권한 등록
INSERT INTO menu_role 
(menu_seq, role_seq, reg_seq)
SELECT m.menu_seq, 3, 1
FROM menus m
WHERE m.menu_seq < 20
ORDER BY m.menu_seq;

-- 사용자(비로그인) 메뉴 권한 등록
INSERT INTO menu_role (menu_seq, role_seq, reg_seq)
SELECT m.menu_seq, 4, 1
FROM menus m
WHERE m.menu_seq IN (1,2,3,5,6,7,8,10,12,14,17,18,19)
ORDER BY m.menu_seq;