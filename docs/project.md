# Demo (기본 웹 프로레임워크 틀 구축)
**1. 목적**
- 포트폴리오용 프로젝트 개발
- Spring Boot 기반의 웹 프레임워크 이해와 실습

**2. 기대 결과**
- 재사용 가능한 기본 틀 확보
- 실제 프로젝트에 활용 가능한 기능 구현
- 주요 기술(JPA, Spring Security 등) 익히기

<br>

## 🔎 주요 기능
### 1. 화면 목록 및 주요 기능
**1.1 메인 화면** <br>
기능: 
- 전체 서비스의 첫 진입 화면
- 로그인 여부에 따라 다른 메뉴 표시

**1.2 로그인 화면** <br>
기능:
- 로그인
- Spring Security를 이용한 인증 처리 
- 세션 관리 방식

**1.3 기본 게시판 화면** <br>
기능:
- 글 목록 조회
- 글쓰기, 수정, 삭제
- 파일 업로드
- 파일 다운로드
    - 첨부파일을 한번에 압축 파일로 다운로드
    - 파일별 개별 다운로드
- 파일 미리보기

**1.4 암호화 관리 화면** <br>
기능:
- 입력한 텍스트를 특정 알고리즘으로 암호화
- 암호화된 텍스트를 복호화하여 원본 테스트로 변환
- 암호화/복호화 결과를 화면에 표시

**1.5 메뉴 관리 화면** <br>
기능:
- 메뉴 생성, 수정, 삭제
- 메뉴별 권한 설정
- 메뉴 활성/비활성화 설정
- DB의 메뉴 테이블과 연동

**1.6 권한 관리 화면** <br>
기능:
- 권한(Role) 생성, 수정, 삭제
- 사용자와 권한 매핑 관리 
- 권한별 접근 가능한 메뉴 확인

**1.7 사용자 관리 화면** <br>
기능:
- 사용자 목록 조회
- 사용자 추가, 수정, 삭제
- 사용자와 권한 연결

**1.8 로그 관리 화면** <br>
기능:
- 에러 로그 조회
- 로그 검색 및 필터링

<br>

### 2. 추가적인 기능과 기술 요소
**2.1 인증 및 보안**
- Spring Security 적용
    - URL 접근 제어 (권한별 접근 제한)
    - 비밀번호 암호화 (BCrypt)
    - 로그인 실패 처리 및 에러 메시지 제공

**2.2 공통 기능**
- ApiResponse
    - 공통 응답 구조 정의
- 예외 처리 
    - 전역 예외 처리기(`@ControllerAdvice`)를 통한 에러 응답 일관화

**2.3 파일 업로드/다운로드**
- 파일 처리
    - Spring `MultipartFile` 사용
    - 업로드된 파일을 로컬 디렉토리에 저장
- 다운로드
    - `ZipOutputStream`을 활용한 압축 다운로드
    - 파일별 URL을 통해 개별 다운로드
- 미리보기 

**2.4 로그 관리**
- 로깅 설정
    - Logback을 통한 로그 설정
    - 에러 로그를 DB에 저장

<br>

## ✨ 기술 스택
- **Backend**: JAVA (JDK 17), Spring boot (3.x), JPA, Lombok
- **Frontend**: HTML, CSS, JavaScript, jQuery, Thymeleaf
- **Build Tool**: Gradle
- **Security**: Spring Security, Jasypt(암호화 처리)
- **DB**: PostgreSQL 14.15
- **Logging**: Logback-Spring
- **Version Control**: GitHub
- **Projcect Control**: Trello
- **Testing**: JUnit 5
- **Environment Management**: application-local.properties, application-dev.properties, application-prod.properties

<br>

## 🛠️ 프로젝트 구조
```
src
 └── main
       ├── java/com/project/demo
       │      ├── api
       │      │    ├── domain
       │      │    ├── application
       │      │    ├── domain
       │      │    ├── infrastructure
       │      │    ├── presentation
       │      │    └── value
       │      │
       │      ├── aspect
       │      ├── common
       │      │    ├── response
       │      │    └── util
       │      │
       │      ├── config
       │      ├── exception
       │      ├── filter
       │      ├── scheduler
       │      ├── security
       │      ├── integration
       │      ├── web
       │      ├── DemoApplication.java
       │      └── ServletInitializer.java
       │
       └── resources
              ├── banner
              ├── META-INF
              ├── static
              ├── templates
              ├── application.properties
              ├── application-local.properties
              ├── application-dev.properties
              ├── application-prod.properties
              ├── logback-spring.xml
              └── message.properties
```

<br>

## 🧪 테스트 계획
1. **단위 테스트**
- 사용자 인증/인가
- 게시판 글쓰기 및 파일 업로드

2. **통합 테스트**
- 메뉴와 권한 연동 테스트
- 사용자와 권한 매핑 테스트

3. **E2E 테스트**
   - 로그인부터 게시판 파일 다운로드까지의 흐름 테스트