# DEMO
'DEMO 프레임워크'는 Java Spring Boot 기반으로 구축된 웹 애플리케이션 프레임워크입니다. <br>
공통적으로 반복되는 기능들을 효육적으로 처리하고, 안정적인 구조와 유지보수성을 갖춘 개발 환경을 구성하는 것을 목표로 제작되었습니다. <br>
이 프레임워크는 로그인 및 권한 기반 메뉴 접근 제어, 공통 코드 관리, 예외 처리, 파일 업로드, 에디터 적용, Swagger 문서화 등 웹 서비스의 기본적인 구성 요소들을 통합하여 빠르게 신규 프로젝트에 적용할 수 있도록 설계되었습니다.

<br>
<br>

## ✨ 기술 스택
| 구분 | 사용 기술 |
|-----|----------|
| 언어 | Java 17 |
| 프레임워크 | Spring Boot 3.4.2 |
| Database | PostgreSQL 14.15 |
| ORM | JPA, QueryDSL |
| Security | Spring Security, BCrypt, Jasypt |
| Build Tool | Gradle |
| Frontend | Thymeleaf, HTML, JavaScript, jQuery, Bootstrap |
| JS 라이브러리 | Toast Editor, Dropzone.js | 
| 문서화 | Swagger 3.0 |

<br>

## 📌 주요 기능 및 특징
- **API와 View Controller 분리**를 통해 책임 명확화 및 유지보수성 향상
- **Jasypt 적용**으로 DB 계정 및 민감 정보 안전하게 암호화
- **QueryDSL + JPA 기반의 동적 쿼리 처리 구조**로 가독성과 재사용성 강화
- **권한 기반 접근 제어**: `menus` / `roles` 테이블 기반으로 URL 접근을 관리하며,  
  GUEST 접근 가능 URL은 `AuthorizationManager`로 처리하고, 커스텀 필터(`OncePerRequestFilter`)로 실제 메뉴 존재 및 권한을 이중 검증
- **접근 정보 자동 갱신**: `@Scheduled` 작업을 통해 GUEST 접근 URL과 메뉴 캐시를 주기적으로 갱신
- **환경별 설정 분리**: `application-local.properties`, `application-dev.properties`, `application-prod.properties`
- **HTML과 JavaScript 파일 자동 연결 처리**: 템플릿 경로 기반으로 정적 자원 자동 매핑
- **외부 라이브러리 통합 구조 구성**: Dropzone.js, Toast Editor.js 등 프론트엔드 기능 강화
- **Swagger(OpenAPI 3.0) 연동을 통한 API 문서 자동화**
- **View / API 구분된 전역 예외 처리 구조**로 디버깅 편의성 향상

<br>

## 🛠️ 프로젝트 구조
```
└── src
     └── main 
           ├── java
           │     └── com.project.demo
           │                ├── api                        // REST API 전용 패키지
           |                │    └── menu                  // [도메인] 메뉴 관리
           |                |         ├── application      // 도메인 서비스 계층
           |                |         |      └── dto       // 요청/응답 DTO 정의
           |                |         ├── domain           // Entity 정의
           |                |         ├── infrastructure   // Repository, JPA, QueryDSL 구현
           |                |         ├── presentation     // RestController
           |                |         └── value            // enum
           |                |
           |                ├── common                     // 공통 유틸/설정/검증 등 재사용 코드
           |                |      ├── advice              // 공통 처리용 Advice (@ModelAttribute, @InitBinder 등 전역 세션/메뉴 처리)
           |                |      ├── aspect              // AOP 처리 (@Validated 검증, View-JS 연결, API/View 구분 로깅 등)
           |                |      ├── constant            // 공통 상수 정의
           |                |      ├── exception           // 전역 Exception 처리 (API / View 별도), 커스텀 예외 정의
           |                |      ├── util                // 공통 유틸리티 클래스
           |                |      └── validation          // 커스텀 유효성 검증
           |                |
           |                ├── config                     // 설정 관련 패키지
           |                |      └── security            // Spring Security 구성
           |                |             ├── application
           |                |             |        ├── authorization   // AuthorizationManager 구현 (GUEST URL 접근 제어 등)
           |                |             |        └── dto             // 인증 관련 DTO
           |                |             ├── handler                  // 인증/인가 핸들러 (Success, Failure 등)
           |                |             └── infrastructure           // 사용자 권한 필터 및 접근 경로 검증 (메뉴/역할 기반),
           |                |                                          // GUEST 접근 가능 URL 자동 갱신 스케줄러
           |                └── web                        // View 전용 패키지
           |                     └── menu                  // [도메인] 메뉴 View Controller
           |
           └── resources
                   ├── banner                              // Spring Boot 배너 (콘솔 출력용)
                   ├── messages                            // 메시지
                   ├── static                              // 정적 리소스 (JS, CSS, 이미지)
                   |     ├── css
                   |     ├── images
                   |     └── js
                   |          └── menu                     // 메뉴 도메인 전용 JS
                   └── templates
                           └── menu                        // 메뉴 도메인 전용 템플릿 (Thymeleaf)
```

<br>

## 🖇️ 프로젝트 문서
### 🗂️ 화면 구성
### 🗂️ DATABASE 👉 [바로가기](docs/db/database.md)
### 📑 API 문서
### 🔐 인증/인가 처리 구조 👉 [바로가기](docs/security-structure.md)
### ⚙️ 실행 및 설정 가이드 👉 [바로가기](docs/setup.md)
### 🔄 주요 기능 흐름도

<br>

## 📆 프로젝트 진행 기간
2025.01.24 ~ 진행 중

<br>

## 🔎 향후 계획
- [ ] 비밀번호 찾기(재설정) 기능 추가 <br>
사용자가 이메일 또는 사용자 정보를 통해 비밀번호 재설정을 요청하고, 임시 비밀번호 발급 또는 본인 확인 절차를 거쳐 비밀번호를 변경할 수 있는 기능 도입 예정
- [ ] 공통 코드 관리 기능 구현 <br>
cmm_cd, comm_cd_grp 테이블을 기반으로 한 코드 그룹 및 상세 코드 관리 페이지를 추가하고, 셀렉트박스 등의 프론트 요소와 연동할 수 있도록 개선 예정
- [ ] 테스트 코드 보완 및 통합 테스트 도입 <br>
핵심 기능 위주로 JUnit + MockMvc 기반의 테스트 코드를 작성하고, 추후 CI 환경에서도 실행 가능한 자동화 테스트 기반 확보 예정