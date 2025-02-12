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


INSERT INTO public.roles
(role_nm, role_desc, reg_seq)
VALUES
('관리자', '최상위관리자', 1);

INSERT INTO users
(role_seq, user_id, user_pwd, user_nm)
VALUES
(1, 'admin', '$2a$12$6R5b2Om09VMfduzFtX2mQOQHE6NkaD1VHxCehYN4PNGTdG8lm2Oa6', '관리자');
