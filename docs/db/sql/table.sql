
-- DROP TABLE public.roles;
CREATE TABLE public.roles (
	role_seq int4 NOT NULL DEFAULT nextval('role_sequence'::regclass),
	role_nm varchar(50) NOT NULL,
	role_desc varchar(100) NULL,
	del_yn public."yn" NOT NULL DEFAULT 'N'::yn,
	reg_dt timestamp NOT NULL DEFAULT now(),
	reg_seq int8 NOT NULL,
	upd_dt timestamp NULL,
	upd_seq int8 NULL,
	CONSTRAINT roles_pk PRIMARY KEY (role_seq)
);
COMMENT ON TABLE public.roles IS '권한';

-- Column comments

COMMENT ON COLUMN public.roles.role_seq IS '권한SEQ';
COMMENT ON COLUMN public.roles.role_nm IS '권한이름';
COMMENT ON COLUMN public.roles.role_desc IS '권한설명';
COMMENT ON COLUMN public.roles.del_yn IS '삭제여부(Y/N)';
COMMENT ON COLUMN public.roles.reg_dt IS '등록일시';
COMMENT ON COLUMN public.roles.reg_seq IS '등록자SEQ';
COMMENT ON COLUMN public.roles.upd_dt IS '수정일시';
COMMENT ON COLUMN public.roles.upd_seq IS '수정자SEQ';



-- DROP TABLE public.users;
CREATE TABLE public.users (
	user_seq int8 NOT NULL DEFAULT nextval('user_sequence'::regclass),
	role_seq int4 NOT NULL,
	user_id varchar(50) NOT NULL,
	user_pwd varchar(255) NOT NULL,
	user_nm varchar(50) NOT NULL,
	user_email varchar(100) NULL,
	profile_img text NULL,
	active_yn public."yn" NOT NULL DEFAULT 'Y'::yn,
	signup_dt timestamp NOT NULL DEFAULT now(),
	last_pwd_dt timestamp  NULL,
	last_login_dt timestamp NULL,
	upd_dt timestamp NULL,
	upd_seq int8 NULL,
	CONSTRAINT users_pk PRIMARY KEY (user_seq),
	CONSTRAINT users_fk FOREIGN KEY (role_seq) REFERENCES public.roles(role_seq)
);
COMMENT ON TABLE public.users IS '사용자';

-- Column comments

COMMENT ON COLUMN public.users.user_seq IS '사용자SEQ';
COMMENT ON COLUMN public.users.role_seq IS '권한SEQ';
COMMENT ON COLUMN public.users.user_id IS '사용자ID';
COMMENT ON COLUMN public.users.user_pwd IS '사용자비밀번호(암호화)';
COMMENT ON COLUMN public.users.user_nm IS '사용자이름';
COMMENT ON COLUMN public.users.user_email IS '사용자이메일';
COMMENT ON COLUMN public.users.profile_img IS '프로필이미지(base64)';
COMMENT ON COLUMN public.users.active_yn IS '활성화여부(Y/N)';
COMMENT ON COLUMN public.users.signup_dt IS '가입일시';
COMMENT ON COLUMN public.users.last_pwd_dt IS '마지막비밀번호변경일시';
COMMENT ON COLUMN public.users.last_login_dt IS '마지막로그인일시';
COMMENT ON COLUMN public.users.upd_dt IS '수정일시';
COMMENT ON COLUMN public.users.upd_seq IS '수정자SEQ';



-- DROP TABLE public.menus;
CREATE TABLE public.menus (
	menu_seq int8 NOT NULL DEFAULT nextval('menu_sequence'::regclass),
	menu_nm varchar(100) NOT NULL,
	menu_url varchar(200) NULL,
	parent_seq int8 NULL,
	menu_level int4 NOT NULL,
	menu_order int4 NOT NULL,
	menu_type public."menu" NOT NULL,
	active_yn public."yn" NOT NULL DEFAULT 'Y'::yn,
	reg_dt timestamp NOT NULL DEFAULT now(),
	reg_seq int8 NOT NULL,
	upd_dt timestamp NULL,
	upd_seq int8 NULL,
	CONSTRAINT menus_pk PRIMARY KEY (menu_seq)
);
COMMENT ON TABLE public.menus IS '메뉴';

-- Column comments

COMMENT ON COLUMN public.menus.menu_seq IS '메뉴SEQ';
COMMENT ON COLUMN public.menus.menu_nm IS '메뉴이름';
COMMENT ON COLUMN public.menus.menu_url IS '메뉴URL';
COMMENT ON COLUMN public.menus.parent_seq IS '부모SEQ(NULL:최상위메뉴)';
COMMENT ON COLUMN public.menus.menu_level IS '메뉴레벨';
COMMENT ON COLUMN public.menus.menu_order IS '메뉴순서';
COMMENT ON COLUMN public.menus.menu_type IS '메뉴유형';
COMMENT ON COLUMN public.menus.active_yn IS '활성화여부';
COMMENT ON COLUMN public.menus.reg_dt IS '등록일시';
COMMENT ON COLUMN public.menus.reg_seq IS '등록자SEQ';
COMMENT ON COLUMN public.menus.upd_dt IS '수정일시';
COMMENT ON COLUMN public.menus.upd_seq IS '수정자SEQ';



-- DROP TABLE public.menu_role;
CREATE TABLE public.menu_role (
	menu_role_seq int8 NOT NULL DEFAULT nextval('menu_role_sequence'::regclass),
	menu_seq int8 NOT NULL,
	role_seq int4 NOT NULL,
	del_yn public."yn" NOT NULL DEFAULT 'N'::yn,
	reg_dt timestamp NOT NULL DEFAULT now(),
	reg_seq int8 NOT NULL,
	upd_dt timestamp NULL,
	upd_seq int8 NULL,
	CONSTRAINT menu_role_pk PRIMARY KEY (menu_role_seq),
	CONSTRAINT menu_role_fk FOREIGN KEY (menu_seq) REFERENCES public.menus(menu_seq),
	CONSTRAINT menu_role_fk_1 FOREIGN KEY (role_seq) REFERENCES public.roles(role_seq)
);
COMMENT ON TABLE public.menu_role IS '메뉴권한';

-- Column comments

COMMENT ON COLUMN public.menu_role.menu_role_seq IS '메뉴권한SEQ';
COMMENT ON COLUMN public.menu_role.menu_seq IS '메뉴SEQ';
COMMENT ON COLUMN public.menu_role.role_seq IS '권한SEQ';
COMMENT ON COLUMN public.menu_role.del_yn IS '삭제여부(Y/N)';
COMMENT ON COLUMN public.menu_role.reg_dt IS '등록일시';
COMMENT ON COLUMN public.menu_role.reg_seq IS '등록자SEQ';
COMMENT ON COLUMN public.menu_role.upd_dt IS '수정일시';
COMMENT ON COLUMN public.menu_role.upd_seq IS '수정자SEQ';



-- DROP TABLE public.cmm_cd_grp;
CREATE TABLE public.cmm_cd_grp (
	grp_seq int4 NOT NULL DEFAULT nextval('cd_grp_sequence'::regclass),
	grp_cd varchar(50) NOT NULL,
	grp_nm varchar(100) NOT NULL,
	grp_desc varchar(200) NULL,
	use_yn public."yn" NOT NULL DEFAULT 'Y'::yn,
	reg_dt timestamp NOT NULL DEFAULT now(),
	reg_seq int8 NOT NULL,
	upd_dt timestamp NULL,
	upd_seq int8 NULL,
	CONSTRAINT cmm_cd_grp_pk PRIMARY KEY (grp_seq)
);
COMMENT ON TABLE public.cmm_cd_grp IS '공통 그룹 코드';

-- Column comments

COMMENT ON COLUMN public.cmm_cd_grp.grp_seq IS '그룹코드SEQ';
COMMENT ON COLUMN public.cmm_cd_grp.grp_cd IS '그룹코드';
COMMENT ON COLUMN public.cmm_cd_grp.grp_nm IS '그룹이름';
COMMENT ON COLUMN public.cmm_cd_grp.grp_desc IS '그룹코드설명';
COMMENT ON COLUMN public.cmm_cd_grp.use_yn IS '사용여부(Y/N)';
COMMENT ON COLUMN public.cmm_cd_grp.reg_dt IS '등록일시';
COMMENT ON COLUMN public.cmm_cd_grp.reg_seq IS '등록자SEQ';
COMMENT ON COLUMN public.cmm_cd_grp.upd_dt IS '수정일시';
COMMENT ON COLUMN public.cmm_cd_grp.upd_seq IS '수정자SEQ';



-- DROP TABLE public.cmm_cd;
CREATE TABLE public.cmm_cd (
	cd_seq int4 NOT NULL DEFAULT nextval('cd_sequence'::regclass),
	grp_seq int4 NOT NULL,
	cd varchar(50) NOT NULL,
	cd_nm varchar(100) NOT NULL,
	cd_desc varchar(200) NULL,
	use_yn public."yn" NOT NULL DEFAULT 'Y'::yn,
	reg_dt timestamp NOT NULL DEFAULT now(),
	reg_seq int8 NOT NULL,
	upd_dt timestamp NULL,
	upd_seq int8 NULL,
	CONSTRAINT cmm_cd_pk PRIMARY KEY (cd_seq),
	CONSTRAINT cmm_cd_fk FOREIGN KEY (grp_seq) REFERENCES public.cmm_cd_grp(grp_seq)
);
COMMENT ON TABLE public.cmm_cd IS '공통 코드';

-- Column comments

COMMENT ON COLUMN public.cmm_cd.cd_seq IS '코드SEQ';
COMMENT ON COLUMN public.cmm_cd.grp_seq IS '그룹코드SEQ';
COMMENT ON COLUMN public.cmm_cd.cd IS '코드';
COMMENT ON COLUMN public.cmm_cd.cd_nm IS '코드이름';
COMMENT ON COLUMN public.cmm_cd.cd_desc IS '코드설명';
COMMENT ON COLUMN public.cmm_cd.use_yn IS '사용여부(Y/N)';
COMMENT ON COLUMN public.cmm_cd.reg_dt IS '등록일시';
COMMENT ON COLUMN public.cmm_cd.reg_seq IS '등록자SEQ';
COMMENT ON COLUMN public.cmm_cd.upd_dt IS '수정일시';
COMMENT ON COLUMN public.cmm_cd.upd_seq IS '수정자SEQ';



-- DROP TABLE public.err_log;
CREATE TABLE public.err_log (
	log_seq int8 NOT NULL DEFAULT nextval('err_log_sequence'::regclass),
	err_cd varchar(50) NULL,
	err_msg text NULL,
	stack_trace text NULL,
	"err_level" public."err_level" NULL,
	occurred_dt timestamp NOT NULL DEFAULT now(),
	request_url varchar(255) NULL,
	request_method varchar(10) NULL,
	request_seq int8 NULL,
	resolved_dt timestamp NULL,
	CONSTRAINT err_log_pk PRIMARY KEY (log_seq)
);
COMMENT ON TABLE public.err_log IS '에러 로그';

-- Column comments

COMMENT ON COLUMN public.err_log.log_seq IS '로그SEQ';
COMMENT ON COLUMN public.err_log.err_cd IS '에러코드(HTTP 상태 코드, 애플리케이션 에러 코드 등)';
COMMENT ON COLUMN public.err_log.err_msg IS '에러메시지';
COMMENT ON COLUMN public.err_log.stack_trace IS '스택트레이스';
COMMENT ON COLUMN public.err_log."level" IS '에러 심각도';
COMMENT ON COLUMN public.err_log.occurred_dt IS '발생일시';
COMMENT ON COLUMN public.err_log.request_url IS '발생URL';
COMMENT ON COLUMN public.err_log.request_method IS '발생Method';
COMMENT ON COLUMN public.err_log.request_seq IS '연관된사용자SEQ';
COMMENT ON COLUMN public.err_log.resolved_dt IS '해결일시';



-- DROP TABLE public.file_mstr;
CREATE TABLE public.file_mstr (
	file_seq int8 NOT NULL DEFAULT nextval('file_sequence'::regclass),
	reg_dt timestamp NOT NULL DEFAULT now(),
	reg_seq int8 NOT NULL,
	CONSTRAINT file_mstr_pk PRIMARY KEY (file_seq)
);
COMMENT ON TABLE public.file_mstr IS '파일';

-- Column comments

COMMENT ON COLUMN public.file_mstr.file_seq IS ' 파일SEQ';
COMMENT ON COLUMN public.file_mstr.reg_dt IS '등록일시';
COMMENT ON COLUMN public.file_mstr.reg_seq IS '등록자SEQ';



-- DROP TABLE public.file_dtl;
CREATE TABLE public.file_dtl (
	dtl_seq int8 NOT NULL DEFAULT nextval('file_dtl_sequence'::regclass),
	file_seq int8 NOT NULL,
	stre_nm varchar(200) NOT NULL,
	ori_nm varchar(200) NOT NULL,
	file_path varchar(300) NOT NULL,
	extsn varchar(5) NULL,
	file_size int8 NULL,
	file_order int4 NOT NULL,
	del_yn public."yn" NOT NULL DEFAULT 'N'::yn,
	reg_dt timestamp NOT NULL DEFAULT now(),
	reg_seq int8 NOT NULL,
	upd_dt timestamp NULL,
	upd_seq int8 NULL,
	CONSTRAINT file_dtl_pk PRIMARY KEY (dtl_seq),
	CONSTRAINT file_dtl_fk FOREIGN KEY (file_seq) REFERENCES public.file_mstr(file_seq)
);
COMMENT ON TABLE public.file_dtl IS '파일 상세';

-- Column comments

COMMENT ON COLUMN public.file_dtl.dtl_seq IS '파일상세SEQ';
COMMENT ON COLUMN public.file_dtl.file_seq IS '파일SEQ';
COMMENT ON COLUMN public.file_dtl.stre_nm IS '저장파일명';
COMMENT ON COLUMN public.file_dtl.ori_nm IS '원본파일명';
COMMENT ON COLUMN public.file_dtl.file_path IS '저장파일경로';
COMMENT ON COLUMN public.file_dtl.extsn IS '확장자';
COMMENT ON COLUMN public.file_dtl.file_size IS '파일크기';
COMMENT ON COLUMN public.file_dtl.file_order IS '파일순서';
COMMENT ON COLUMN public.file_dtl.del_yn IS '삭제여부(Y/N)';
COMMENT ON COLUMN public.file_dtl.reg_dt IS '등록일시';
COMMENT ON COLUMN public.file_dtl.reg_seq IS '등록자SEQ';
COMMENT ON COLUMN public.file_dtl.upd_dt IS '수정일시';
COMMENT ON COLUMN public.file_dtl.upd_seq IS '수정자SEQ';



-- DROP TABLE public.board;
CREATE TABLE public.board (
	board_seq int8 NOT NULL DEFAULT nextval('board_sequence'::regclass),
	file_seq int8 NULL,
	title varchar(100) NOT NULL,
	"content" text NOT NULL,
	view_cnt int4 NOT NULL DEFAULT 0,
	del_yn public."yn" NOT NULL DEFAULT 'N'::yn,
	reg_dt timestamp NOT NULL DEFAULT now(),
	reg_seq int8 NOT NULL,
	upd_dt timestamp NULL,
	upd_seq int8 NULL,
	CONSTRAINT board_pk PRIMARY KEY (board_seq),
	CONSTRAINT board_fk FOREIGN KEY (file_seq) REFERENCES public.file_mstr(file_seq)
);
COMMENT ON TABLE public.board IS '게시판';

-- Column comments

COMMENT ON COLUMN public.board.board_seq IS '게시판SEQ';
COMMENT ON COLUMN public.board.file_seq IS '파일SEQ';
COMMENT ON COLUMN public.board.title IS '게시글제목';
COMMENT ON COLUMN public.board."content" IS '게시글내용';
COMMENT ON COLUMN public.board.view_count IS '조회수';
COMMENT ON COLUMN public.board.del_yn IS '삭제여부(Y/N)';
COMMENT ON COLUMN public.board.reg_dt IS '등록일시';
COMMENT ON COLUMN public.board.reg_seq IS '등록자SEQ';
COMMENT ON COLUMN public.board.upd_dt IS '수정일시';
COMMENT ON COLUMN public.board.upd_seq IS '수정자SEQ';