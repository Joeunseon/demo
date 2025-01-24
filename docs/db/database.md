## 🗂️ DATABASE

<br>

### ⚡ ERD
```mermaid
erDiagram
    %% entitiy
    roles {
        int role_seq PK "권한SEQ"
        varchar role_nm "권한이름"
        varchar role_desc "권한설명"
        yn del_yn "삭제여부(Y:삭제/N:정상)"
        timestamp reg_dt "등록일시"
        int reg_seq "등록자SEQ"
        timestamp upd_dt "수정일시"
        int upd_seq "수정자SEQ"
    }

    users {
        int user_seq PK "사용자SEQ"
        int role_seq FK "권한SEQ"
        varchar user_id "사용자ID"
        varchar user_pwd "사용자비밀번호(암호화)"
        varchar user_nm "사용자이름(암호화)"
        varchar user_email "사용자이메일"
        text profile_img "프로필이미지(base64)"
        yn active_yn "활성화여부(Y:활성/N:탈퇴)"
        timestamp join_dt "가입일시"
        timestamp last_pwd_dt "마지막비밀번호변경일시"
        timestamp last_login_dt "마지막로그인일시"
        timestamp upd_dt "수정일시"
        int upd_seq "수정자SEQ"
    }

    menus {
        int menu_seq PK "메뉴SEQ"
        varchar menu_nm "메뉴이름"
        varchar menu_url "메뉴URL"
        int parent_seq "부모SEQ(NULL:최상위메뉴)"
        int menu_level "메뉴레벨"
        int menu_order "메뉴순서"
        menu menu_type "메뉴유형"
        yn active_yn "활성화여부(Y:활성/N:비활성)"
        timestamp reg_dt "등록일시"
        int reg_seq "등록자SEQ"
        timestamp upd_dt "수정일시"
        int upd_seq "수정자SEQ"
    }

    menu_role {
        int menu_role_seq PK "메뉴권한SEQ"
        int menu_seq FK "메뉴SEQ"
        int role_seq FK "권한SEQ"
        yn del_yn "삭제여부(Y:삭제/N:정상)"
        timestamp reg_dt "등록일시"
        int reg_seq "등록자SEQ"
        timestamp upd_dt "수정일시"
        int upd_seq "수정자SEQ"
    }

    cmm_cd_grp {
        int grp_seq PK "그룹코드SEQ"
        varchar grp_cd "그룹코드"
        varchar grp_nm "그룹이름"
        varchar grp_desc "그룹코드설명"
        yn user_yn "사용여부(Y:사용/N:미사용)"
        timestamp reg_dt "등록일시"
        int reg_seq "등록자SEQ"
        timestamp upd_dt "수정일시"
        int upd_seq "수정자SEQ"
    }

    cmm_cd {
        int cd_seq PK "코드SEQ"
        int grp_seq FK "그룹코드SEQ"
        varhcar cd "코드"
        varchar cd_nm "코드이름"
        varchar cd_desc "코드설명"
        yn use_yn "사용여부(Y:사용/N미사용)"
        timestamp reg_dt "등록일시"
        int reg_seq "등록자SEQ"
        timestamp upd_dt "수정일시"
        int upd_seq "수정자SEQ"
    }
    
    err_log {
        int log_seq PK "로그SEQ"
        varchar err_cd "에러코드(HTTP 상태 코드, 애플리케이션 에러 코드 등)"
        text err_msg "에러메시지"
        text stack_trace "스택트레이스"
        err_level level "에러심각도"
        timestamp occured_dt "발생일시"
        varchar request_url "발생URL"
        int request_seq "연관된사용자SEQ"
        timestamp resolved_dt "해결일시"
    }

    file_mstr {
        int file_seq PK "파일SEQ"
        timestamp reg_dt "등록일시"
        int reg_seq "등록자SEQ"
    }

    file_dtl {
        int dtl_seq PK "파일상세SEQ"
        int file_seq FK "파일SEQ"
        varchar stre_nm "저장파일명"
        varchar ori_nm "원본파일명"
        varchar file_path "저장파일경로"
        varchar extsn "파일확장자"
        int file_size "파일크기"
        int file_order "파일순서"
        yn del_yn "삭제여부(Y:삭제/N:정상)"
        timestamp reg_dt "등록일시"
        int reg_seq "등록자SEQ"
        timestamp upd_dt "수정일시"
        int upd_seq "수정자SEQ"
    }

    board {
        int board_seq PK "게시글SEQ"
        int file_seq FK "파일SEQ"
        varchar title "게시글제목"
        text content "게시글내용"
        int view_cnt "조회수"
        yn del_yn "삭제여부(Y:삭제/N:정상)"
        timestamp reg_dt "등록일시"
        int reg_seq "등록자SEQ"
        timestamp upd_dt "수정일시"
        int upd_seq "수정자SEQ"
    }

    %% relation
    roles ||--|| users : ""
    roles ||--o{ menu_role : ""
    menus ||--o{ menu_role : ""
    cmm_cd_grp ||--o{ cmm_cd : ""
    file_mstr ||--o{ file_dtl : ""
    file_mstr |o--o| board : ""
```

<br>
<br>

## 🖇️ 프로젝트 문서
### 🗂️ DATABASE
### 📑 Create Table 👉 [바로가기](sql/create_table.sql)
### 📑 Create Sequence 👉 [바로가기](sql/create_sequence.sql)
### 📑 Create Enum 👉 [바로가기](sql/create_enum.sql)