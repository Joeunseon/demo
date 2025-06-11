# ⚙️ 실행 및 설정 가이드

<br>
<br>

## 1. 🔐 Jasypt 암호화 키 설정
- 민감 정보 복호화를 위해 환경 변수 또는 시스템 속성에 암호화 키를 지정해야 합니다. <br>
- `application-*.properties` 파일 내 DB 계정 정보 등은 암호화되어 있으며, 위 비밀키를 통해 복호화됩니다. <br>

### - 환경 변수 설정 (권장)
```bash
export JASYPT_ENCRYPTOR_PASSWORD=your-secret-key
```

### - 시스템 속성 지정 (예: Gradle 실행 시)
```bash
-Djasypt.encryptor.password=your-secret-key
```

### - lauch.json 지정
```json
"env": {
                "JASYPT_ENCRYPTOR_PASSWORD": "your-secret-key"
       }
```

<br>

## 2. ⚙️ 환경 설정 선택
`application.properties`에서 사용 환경을 지정하세요.
```properties
spring.profiles.active=local
```

- `application-local.properties`: 로컬 환경
- `application-dev.properties`: 개발 서버 환경
- `application-prod.properties`: 운영 서버 환경

<br>

### 📝 로그 설정 (Logback)
- `logback-spring.xml`을 사용하여 로그를 관리합니다.
- 각 환경 설정 파일(`application-*.properties`)에서 아래 항목을 설정할 수 있습니다:
  - `logging.file.path`
  - `logging.level.*`


<br>

## 3. 🗄️ DB 설정
- docs/db/sql 폴더의 `table.sql`, `sequence.sql`, `enum.sql`, `init_data.sql` 파일을 참고하여 DB를 구성하세요.
- 각 URL은 메뉴 정보(`menus`)와 권한 정보(`menu_role`)에 따라 접근 여부를 판단합니다.

<br>

## 4. ▶️ 애플리케이션 실행
### - 프로젝트 실행
```bash
./gradlew bootRun
```

<br>

### - 빌드 명령어
1. 빌드 초기화 : 기존 빌드 파일과 캐시를 삭제 <br>
**./gradlew clean**

2. 기본 빌드 : 애플리케이션을 빌드 <br>
**./gradlew build**
```bash
./gradlew clean build
```

<br>

## 5. 🌐 접속
브라우저에서 다음 URL로 접속합니다.
```
http://localhost:8080
```

<br>
<br>

## 🖇️ 프로젝트 문서
### 📑 README 👉 [바로가기](../README.md)
