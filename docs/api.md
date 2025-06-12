# 📑 API 명세서

<br>

## 📚 목차
1. [🔧 API 설계 원칙 (RESTful)](#-api-설계-원칙-restful)
2. [📦 API 응답 형식: `ApiResponse<T>`](#-api-응답-형식-apiresponset)
3. [💬 메시지 처리 방식 (`MsgUtil`, `messages-*.properties`)](#-메시지-처리-방식-msgutil-messages-properties)
4. [🧾 API 목록](#-api-목록)

<br>
<br>

## 🔧 API 설계 원칙 (RESTful)
RESTful 규칙을 기반으로 설계되었습니다.

<br>

상태/동작을 URI가 아닌 HTTP 메서드로 표현
- **소문자** 사용
    - URI는 소문자로 작성하는 것을 권장
- **하이픈(-)** 사용
    - URI에서 하이픈이 시작적으로 더 뚜렷하고 가독성을 높기에 언더바(_)나 camelCase 대신 하이픈(-)을 사용
- **HTTP 메서드** 사용
    - `GET`: 데이터 조회
        - `@GetMapping("/boards")` (다건 조회): 목록 조회 같이 다건을 조회할때 복수형식으로 표현
        - `@GetMapping("/board/{boardSeq}")` (단건 조회): 단건 조회는 camelCase로 Path Variable 표현
    - `POST`: 리소스 생성
        - `@PostMapping("/board")`: 새로운 자원을 등록
    - `PATCH (PUT)`: 수정
        - `@PutMapping("board")`: 자원 전체를 수정
        - `@PatchMapping("board")`: 자원에 대한 일부 수정
    - `DELETE`: 리소스 삭제
        - `@DeleteMapping("/board/{boardSeq}")`: 요구조건에 따라 **HARD, SOFT DELETE**로 나눔
            - HARD DELETE - 자원을 삭제 (delete문 수행)
            - SOFT DELETE - 자원의 대한 상태값을 변경하여서 삭제한 것 처럼 구현 (update문 수행)
- **HTTP 상태 코드 준수**
  - `200 OK`: 성공
  - `201 Created`: 리소스 생성
  - `204 No Content`: 성공, 본문 없음
  - `400 Bad Request`: 잘못된 요청
  - `403 Forbidden`: 권한 없음
  - `404 Not Found`: 리소스 없음
  - `500 Internal Server Error`: 서버 오류
- **응답 구조**
  - `ApiResponse<T>` 객체를 통해 `result`, `status`, `data`, `message` 일관된 구조 제공

<br>
<br>

## 📦 API 응답 형식: `ApiResponse<T>`
모든 API는 공통 응답 포맷인 `ApiResponse<T>` 객체를 반환합니다. <br>
이 객체는 API의 성공/실패 여부, 데이터, 상태 코드, 메시지를 포함하여 클라이언트와의 명확한 계약을 보장합니다.

```json
{
    "result": true,
    "status": "OK",
    "data": { ... },
    "message": "정상 처리되었습니다."
}
```

| 필드명 | 타입 | 설명 |
|-------|-----|------|
| result | `boolean` | 요청 처리 결과 (`true`/`false`) |
| status | `string` | HTTP 상태 코드 (예: `"OK"`, `"BAD_REQUEST"`) |
| data | `T` | API 응답데이터 (`null`일 수 있음) |
| message | `string` | 응답 메시지 |

<br>

## 💬 메시지 처리 방식 (`MsgUtil`, `messages-*.properties`)
응답 메시지는 고정 문자열이 아닌, 메시지 키 기반의 유틸리티(`MsgUtil`)를 통해 동적으로 처리됩니다. <br>
이를 통해 API와 UI 메시지를 분리하고, 중앙 집중식 메시지 관리가 가능해집니다.

<br>

### 📂 메시지 파일 구조
- `messages-api.properties`: API 응답용 메시지 정의
- `messages-ui.properties`: UI(View)용 알림 메시지 정의

다국어를 지원하진 않지만, 메시지의 역할을 명확히 나누어 관리 효율성을 확보했습니다.

<br>

#### ✉️ 메시지 예시 (messages-api.properties)
```properties
success.basic=정상 처리되었습니다.
error.basic=시스템에러가 발생했습니다.
```

<br>

#### 📝 사용 예시 (Java 코드)
```java
return ApiResponse.success(msgUtil.getMessage(CommonMsgKey.SUCCESS.getKey()), data);

return ApiResponse.error(HttpStatus.FORBIDDEN, msgUtil.getMessage(CommonMsgKey.FAILED_FORBIDDEN.getKey()));
```

<br>

### 🧰 메시지 키 관리
- `CommonMsgKey`와 같은 Enum 클래스를 활용해 메시지 키를 정형화
- 코드 일관성 유지를 위해 Enum 키 기반 메시지 호출 권장

```java
@Getter
@RequiredArgsConstructor
public enum CommonMsgKey {

    SUCCESS("success.basic"),
    FAILED("error.basic"),
    FAILED_VALIDATION("error.validation"),
    FAILED_LENGTH("error.length"),
    FAILED_DUPLICATION("error.duplication"),
    FAILED_FORBIDDEN("error.forbidden"),
    FAILED_SEARCH_DATE("error.search_date"),
    FAILED_REQUEST("error.request");

    private final String key;
}
```

<br>

#### ✅ 에러 응답 예시
```json
{
  "result": false,
  "status": "FORBIDDEN",
  "data": null,
  "message": "권한이 없습니다."
}
```

모든 에러 응답도 위와 같은 `ApiResponse` 구조로 반환되며, HTTP 상태코드에 따른 기본 메시지 또는 커스텀 메시지가 설정됩니다.

<br>

### 📘 비고
- `ApiResponse<T>`는 일반적인 성공/실패 케이스 외에도 다양한 조합으로 응답 객체를 생성할 수 있도록 오버로드된 정적 팩토리 메서드를 제공합니다.
- 메시지가 명시되지 않으면, 내부적으로 HTTP 상태 코드에 따른 기본 메시지를 자동 생성합니다.

<br>
<br>

## 🧾 API 목록

### 1️⃣ Swagger 연동
Swagger를 사용하여 API 문서를 자동화하였습니다. <br>
브라우저에서 아래 주소로 접속하여 API 목록 및 테스트가 가능합니다.
```
http://localhost:8080/swagger-ui/index.html
```

- 모든 API의 요청 방식, 파라미터, 응답 형식을 실시간 확인할 수 있습니다.

<br>

### 2️⃣ 주요 API 예시
| 구분 | URL | Method | 설명 | 반환 형식 | 
|-----|-----|--------|------|---------|
| 회원가입 | `/api/signup` | `POST` | 신규 사용자 등록 | `ApiResponse<T>` |
| 사용자 목록 | `/api/users` | `GET` | 사용자 리스트 조회 | `ApiResponse<T>` |
| 메뉴 조회 | `/api/menu/{menuSeq}` | `GET` | 메뉴 상세 정보 조회 | `ApiResponse<T>` |
| 게시글 수정 | `/api/board` | `PATCH` | 게시글 수정 | `ApiResponse<T>` |
| 파일 업로드 | `/api/file` | `POST` | 단일/다중 파일 업로드 | `ApiResponse<T>` |
| 파일 다운로드 (단일) | `/api/file/download/{dtlSeq}` | `GET` | 단일 파일 다운로드 | `ResponseEntity<Resource>` |
| 파일 다운로드 (다중) | `/api/file/download/zip` | `GET` | 파일 압축(zip) 다운로드 | `ResponseEntity<Resource>` |

※ 파일 다운로드 API는 JSON 응답 대신 `ResponseEntity`를 통해 바이너리 파일 스트림을 반환합니다. <br>
일반 API 응답(`ApiResponse<T>`)과는 다르게, 파일 컨텐츠와 헤더(Content-Disposition 등)를 직접 제어하여 브라우저에서 다운로드 동작이 일어나도록 구성됩니다. <br>
※ 전체 API 스펙은 Swagger 문서를 참고하세요.

<br>
<br>

## 🖇️ 프로젝트 문서
### 📑 README 👉 [바로가기](../README.md)
