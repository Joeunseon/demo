# 🔄 주요 기능 흐름도

<br>
<br>

## 1️⃣ 기본 인증 기능 구성
```
[사용자 요청] 
    ↓
[Spring Security 인증 처리 (UsernamePasswordAuthenticationFilter)]
    ↓
[CustomUserDetailsService를 통해 사용자 정보 조회]
    ↓
[로그인 성공 → 세션에 UserSessionDTO 저장]
```

- 로그인 성공 시 UserSessionDTO가 세션에 저장되며, 이후 모든 요청에서 인증 정보로 활용됩니다.

<br>

## 2️⃣ 권한 기반 접근 검증
```
[모든 요청 → GuestUrlAuthorizationManager에서 GUEST 접근 허용 여부 판단]
    ↓
[MenuAuthorizationFilter로 진입]
    ↓
[요청 URI가 menus 테이블에 존재하는지 확인] → [존재하지 않으면 → 404 리다이렉트]
    ↓
[요청 URI + Method + 사용자 Role 기반 권한 확인]
    ↓
[접근 불가 시 → 로그인 페이지 또는 403 리다이렉트]
```

- menus, menu_role 테이블 기반으로 DB에서 접근 가능 여부를 판단합니다.
- GUEST 접근 허용 URI는 캐시되어 있으며, @Scheduled 작업으로 주기적으로 갱신됩니다.

<br>

## 3️⃣ 메뉴 렌더링 (View 요청 시)
```
[View Controller 진입 시]
    ↓
[GlobalControllerAdvice @ModelAttribute 실행]
    ↓
[사용자 Role 기반 메뉴 목록 조회 및 캐싱]
    ↓
[Model에 menuList, loginInfo 자동 주입]
    ↓
[템플릿에서 메뉴 렌더링 처리]
```

- View 진입 시마다 권한에 맞는 메뉴가 자동으로 렌더링되며, 중복 DB 조회를 방지합니다.

<br>

## 4️⃣ 정적 자원 자동 연결
```
# 페이지별 JS 파일 로딩
[View 진입 시 템플릿 경로와 동일한 JS 경로 자동 매핑]
    ↓
[/templates/menu/test.html → /js/menu/test.js 자동 로딩 시도]


# 외부 라이브러리 플러그인 로딩
[View Controller 진입 시 필요한 플러그인 설정]
    ↓
[Model 에 PagePlugin 리스트 주입]
    ↓
[공통 템플릿 head.html에서 plugin 종류에 따라 JS 조건부 로딩]
    ↓
[Dropzone.js, Toast Editor.js 등 외부 라이브러리 선택적 로딩]
```

- 페이지별 스크립트 자동 로딩과 플러그인 선택적 주입을 통해 재사용성과 유지보수성을 강화했습니다.

<br>

## 5️⃣ 예외 처리 흐름
```
[View 요청 시 예외 발생]
    → GlobalControllerAdvice 를 통해 View용 예외 페이지 리다이렉트 (404 / 403 / 500 / default)

[API 요청 시 예외 발생]
    → GlobalRestControllerAdvice 를 통해 JSON 형태의 오류 응답 반환
```

- View와 API의 예외 처리를 명확히 분리하여 사용자 친화적인 응답과 디버깅 편의성을 제공합니다.

<br>
<br>

## 🖇️ 프로젝트 문서
### 📑 README 👉 [바로가기](../README.md)