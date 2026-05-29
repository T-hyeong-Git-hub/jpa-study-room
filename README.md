<div align="center">

# 📚 JPA Study Room Reservation API

**Spring Boot + JPA + QueryDSL** 기반의 스터디룸 예약 백엔드 API

단순 CRUD를 넘어 JWT 인증, 동적 쿼리, 페이징, 전역 예외 처리,  
Redis 캐싱, Docker 컨테이너화, AWS EC2 배포까지  
**실무 수준의 백엔드 구조**를 직접 구현하며 학습한 프로젝트입니다.

<br>

![Java](https://img.shields.io/badge/Java-21-orange?style=flat-square&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3-6DB33F?style=flat-square&logo=springboot&logoColor=white)
![Spring Security](https://img.shields.io/badge/Spring_Security-6DB33F?style=flat-square&logo=springsecurity&logoColor=white)
![JPA](https://img.shields.io/badge/Spring_Data_JPA-6DB33F?style=flat-square&logo=spring&logoColor=white)
![QueryDSL](https://img.shields.io/badge/QueryDSL-5-blue?style=flat-square)
![JWT](https://img.shields.io/badge/JWT-000000?style=flat-square&logo=jsonwebtokens&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-8-4479A1?style=flat-square&logo=mysql&logoColor=white)
![Redis](https://img.shields.io/badge/Redis-DC382D?style=flat-square&logo=redis&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-2496ED?style=flat-square&logo=docker&logoColor=white)
![Nginx](https://img.shields.io/badge/Nginx-009639?style=flat-square&logo=nginx&logoColor=white)
![AWS](https://img.shields.io/badge/AWS_EC2-FF9900?style=flat-square&logo=amazonaws&logoColor=white)
![Swagger](https://img.shields.io/badge/Swagger-85EA2D?style=flat-square&logo=swagger&logoColor=black)
![Gradle](https://img.shields.io/badge/Gradle-02303A?style=flat-square&logo=gradle&logoColor=white)

</div>

---

## 📌 목차

- [프로젝트 소개](#프로젝트-소개)
- [기술 스택](#기술-스택)
- [주요 기능](#주요-기능)
- [프로젝트 구조](#프로젝트-구조)
- [ERD](#erd)
- [API 명세](#api-명세)
- [JWT 인증 흐름](#jwt-인증-흐름)
- [Docker 환경 구성](#docker-환경-구성)
- [실행 방법](#실행-방법)
- [트러블슈팅](#트러블슈팅)
- [학습 회고](#학습-회고)

---

## 🚀 프로젝트 소개

> **"단순 CRUD 구현을 넘어, 실무형 백엔드 설계를 직접 경험하는 것"** 이 이 프로젝트의 핵심 목표입니다.

스터디룸 예약 시스템이라는 현실적인 도메인을 통해 아래 요소들을 직접 설계하고 구현했습니다.

| 구분 | 적용 기술 | 목적 |
|------|-----------|------|
| 인증 | JWT + Spring Security | 무상태(Stateless) 인증 구조 이해 |
| 조회 | QueryDSL + Pageable | 동적 쿼리 및 페이징 처리 |
| 캐싱 | Redis Cache | 반복 조회 DB 부하 감소 |
| 예외 | GlobalExceptionHandler | 일관된 에러 응답 포맷 |
| 응답 | 공통 응답 포맷 | API 응답 표준화 |
| 검증 | Spring Validation | 입력값 유효성 처리 |
| 인프라 | Docker + Nginx + AWS EC2 | 실제 운영 환경 배포 경험 |

---

## 🛠 기술 스택

```
Language     │  Java 21
Framework    │  Spring Boot 3.x
Security     │  Spring Security 6 + JWT
ORM          │  Spring Data JPA + QueryDSL 5
Database     │  MySQL 8
Cache        │  Redis
Docs         │  Swagger (springdoc-openapi)
Build        │  Gradle
Infra        │  Docker · Docker Compose · Nginx · AWS EC2 (Ubuntu)
```

---

## ✅ 주요 기능

### 👤 회원 (Member)

- **회원가입** — 이메일/비밀번호 기반 회원 등록 (비밀번호 암호화)
- **로그인** — JWT Access Token 발급

### 📅 예약 (Reservation)

- **예약 생성** — 스터디룸 예약 (시간 중복 검증 포함)
- **예약 조회** — 내 예약 목록 조회
- **동적 검색** — QueryDSL 기반 조건부 필터링 (날짜, 룸 등)
- **페이징** — `Pageable` 기반 오프셋 페이징

### 🔐 인증 / 보안

- JWT 기반 Stateless 인증
- `JwtAuthenticationFilter`에서 토큰 파싱 및 검증
- `SecurityContextHolder`에 인증 정보 저장
- 인증된 사용자 기반 API 접근 제어

### 🗄 캐싱 (Redis)

- Room 조회 API에 `@Cacheable` 적용으로 반복 조회 시 DB 부하 감소
- `GenericJackson2JsonRedisSerializer` 기반 직렬화 설정
- Docker Redis 컨테이너 연동

### ⚙️ 공통 처리

- `ApiResponse<T>` 공통 응답 래퍼
- `CustomException + ErrorCode` 기반 전역 예외 처리
- `@Valid` 기반 요청 유효성 검증

**ErrorCode 예시**

| 코드 | 설명 |
|------|------|
| `MEMBER_NOT_FOUND` | 존재하지 않는 회원 |
| `ROOM_NOT_FOUND` | 존재하지 않는 스터디룸 |
| `RESERVATION_CONFLICT` | 예약 시간 중복 |
| `INVALID_INPUT_VALUE` | 잘못된 입력값 |
| `UNAUTHORIZED` | 인증되지 않은 접근 |

### 🧪 테스트

비즈니스 로직 검증을 위한 서비스 단위 테스트 작성

- 예약 성공 테스트
- 예약 시간 충돌 실패 테스트
- 로그인 실패 테스트

---

## 📁 프로젝트 구조

```
src/main/java/com/kkth/jpaStudyRoom
│
├── 📂 domain
│   ├── 📂 member          # 회원 도메인 (Entity, Repository, Service, Controller)
│   ├── 📂 reservation     # 예약 도메인
│   └── 📂 room            # 스터디룸 도메인
│
├── 📂 global
│   ├── 📂 config          # Security, QueryDSL, Redis 설정
│   ├── 📂 exception       # CustomException, ErrorCode, GlobalExceptionHandler
│   ├── 📂 response        # ApiResponse 공통 응답 포맷
│   └── 📂 security        # JWT 유틸리티 및 Filter
│
└── 📂 support             # 공통 유틸리티
```

> 각 도메인은 **Entity → Repository → Service → Controller** 레이어 구조를 따르며,  
> 도메인별로 패키지를 분리하여 응집도를 높였습니다.

---

## 📊 ERD

```
┌──────────────────┐         ┌──────────────────────────┐         ┌──────────────────┐
│      Member      │         │       Reservation        │         │      Room        │
├──────────────────┤         ├──────────────────────────┤         ├──────────────────┤
│ PK  id (Long)    │──────┐  │ PK  id (Long)            │  ┌──────│ PK  id (Long)    │
│     name         │      └─▶│ FK  member_id            │  │      │     name         │
│     email        │         │ FK  room_id              │◀─┘      │     capacity     │
│     password     │         │     startTime            │         └──────────────────┘
└──────────────────┘         │     endTime              │
                             └──────────────────────────┘

  Member (1) ──────────────── (N) Reservation (N) ──────────────── (1) Room
```

**관계 요약**
- 한 명의 회원은 여러 예약을 생성할 수 있습니다. (`1:N`)
- 한 개의 스터디룸은 여러 예약을 가질 수 있습니다. (`1:N`)
- 예약은 시간 범위(`startTime ~ endTime`)를 기반으로 중복을 검증합니다.

---

## 📡 API 명세

### 회원 API

#### `POST /api/members/signup` — 회원가입

<details>
<summary>Request / Response 보기</summary>

**Request Body**
```json
{
  "name": "홍길동",
  "email": "test@test.com",
  "password": "1234"
}
```

**Response**
```json
{
  "success": true,
  "data": null,
  "message": "회원가입이 완료되었습니다."
}
```

</details>

---

#### `POST /api/members/login` — 로그인 (JWT 발급)

<details>
<summary>Request / Response 보기</summary>

**Request Body**
```json
{
  "email": "test@test.com",
  "password": "1234"
}
```

**Response**
```json
{
  "success": true,
  "data": {
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
  },
  "message": null
}
```

</details>

---

### 예약 API

> 🔒 모든 예약 API는 `Authorization: Bearer {token}` 헤더 필요

#### `POST /api/reservations` — 예약 생성

<details>
<summary>Request / Response 보기</summary>

**Request Body**
```json
{
  "roomId": 1,
  "startTime": "2025-06-01T10:00:00",
  "endTime": "2025-06-01T12:00:00"
}
```

**Response**
```json
{
  "success": true,
  "data": {
    "reservationId": 1,
    "roomName": "스터디룸 A",
    "startTime": "2025-06-01T10:00:00",
    "endTime": "2025-06-01T12:00:00"
  },
  "message": null
}
```

</details>

---

#### `GET /api/reservations?roomId=1&date=2025-06-01&page=0&size=10` — 예약 목록 조회

<details>
<summary>Response 보기</summary>

```json
{
  "success": true,
  "data": {
    "content": [ ... ],
    "totalElements": 25,
    "totalPages": 3,
    "number": 0
  },
  "message": null
}
```

</details>

---

## 🔐 JWT 인증 흐름

```
Client                        Server
  │                              │
  │  POST /api/members/login     │
  │ ────────────────────────────▶│
  │                              │  ① 이메일/비밀번호 검증
  │                              │  ② JWT 생성
  │  ◀────────────────────────── │
  │  { token: "eyJ..." }         │
  │                              │
  │  GET /api/reservations       │
  │  Authorization: Bearer eyJ.. │
  │ ────────────────────────────▶│
  │                              │  ③ JwtAuthenticationFilter
  │                              │     - 토큰 파싱 & 서명 검증
  │                              │     - SecurityContext에 인증 저장
  │                              │  ④ 인증된 사용자 기반 API 처리
  │  ◀────────────────────────── │
  │  { success: true, data: ..} │
```

**흐름 요약**

1. 클라이언트가 이메일/비밀번호로 로그인 요청
2. 서버가 자격증명 검증 후 JWT 발급
3. 이후 요청 시 `Authorization: Bearer {token}` 헤더 첨부
4. `JwtAuthenticationFilter`가 토큰 유효성 검증
5. `SecurityContextHolder`에 인증 정보 등록
6. 컨트롤러에서 `@AuthenticationPrincipal`로 현재 사용자 조회

---

## 🐳 Docker 환경 구성

### 컨테이너 구조

```
Client
  ↓
Nginx (80)          ← Reverse Proxy
  ↓
Spring Boot (8083)  ← Application Server
  ↓
MySQL / Redis       ← Data Layer
```

### 컨테이너 목록

| 컨테이너 | 역할 |
|----------|------|
| `studyroom-nginx` | Reverse Proxy (외부 요청 → Spring Boot 전달) |
| `studyroom-app` | Spring Boot 애플리케이션 |
| `studyroom-mysql` | MySQL 8 데이터베이스 |
| `studyroom-redis` | Redis 캐시 서버 |

### Nginx Reverse Proxy

```
Client → Nginx(80) → Spring Boot(8083)
```

Nginx를 Reverse Proxy로 구성하여 얻은 것:
- 내부 애플리케이션 포트 은닉
- 웹 서버와 애플리케이션 서버 분리 구조 경험
- 실제 운영 환경과 유사한 인프라 구성

### AWS EC2 배포

Ubuntu 기반 AWS EC2에 Docker Compose로 배포했습니다.

```
EC2 Ubuntu 생성 → Docker 설치 → Git Clone
→ Gradle Build → Docker Compose 실행 → Nginx 연결
```

---

## ▶️ 실행 방법

### 로컬 실행

**1. 프로젝트 Clone**
```bash
git clone <repository-url>
```

**2. Build**
```bash
./gradlew clean build
```

**3. Docker Compose 실행**
```bash
docker compose up --build -d
```

**4. Swagger 접속**
```
http://localhost/swagger-ui/index.html
```

---

### AWS EC2 배포 실행

**1. EC2 접속**
```bash
ssh -i studyroom-key.pem ubuntu@EC2_PUBLIC_IP
```

**2. 프로젝트 디렉토리 이동 및 최신 코드 Pull**
```bash
cd jpa-study-room
git pull
```

**3. Build**
```bash
./gradlew clean build -x test
```

> EC2 환경에서는 테스트 실행 시 메모리 부족이 발생할 수 있어 배포 빌드에서는 테스트를 제외했습니다.  
> 로컬에서 테스트를 충분히 검증한 후 배포하는 것을 권장합니다.

**4. Docker Compose 실행**
```bash
sudo docker compose up --build -d
```

**5. 배포 확인 (Swagger)**
```
http://EC2_PUBLIC_IP/swagger-ui/index.html
```

---

## 🐛 트러블슈팅

---

### 1. 예약 시간 중복 검증 문제

**문제**

예약 가능 여부를 어떤 조건으로 판단해야 할지 명확하지 않았습니다.

```
기존 예약:  |████████████|
             10:00     12:00

새 예약:         |████████████|
                 11:00     13:00

    → 겹침! 하지만 어떤 조건으로 판단할 것인가?
```

**원인**

시작/종료 시간을 단순 비교하는 방식으로는 모든 겹침 케이스를 커버하기 어려웠습니다.

**해결**

**시간 범위가 겹치는 역조건**을 정의하여 해결했습니다.

```java
// 새 예약의 시작이 기존 예약의 종료보다 이전이고
// 새 예약의 종료가 기존 예약의 시작보다 이후인 경우 → 중복

where reservation.startTime < :newEndTime
  and reservation.endTime   > :newStartTime
```

| 케이스 | 기존 예약 | 새 예약 | 결과 |
|--------|-----------|---------|------|
| 완전 포함 | 10:00~12:00 | 10:30~11:30 | ❌ 중복 |
| 앞부분 겹침 | 10:00~12:00 | 09:00~11:00 | ❌ 중복 |
| 뒷부분 겹침 | 10:00~12:00 | 11:00~13:00 | ❌ 중복 |
| 완전 이전 | 10:00~12:00 | 08:00~10:00 | ✅ 예약 가능 |
| 완전 이후 | 10:00~12:00 | 12:00~14:00 | ✅ 예약 가능 |

> 핵심: **겹치지 않는 조건의 반대**가 겹치는 조건임을 이용한 논리 역전

---

### 2. Docker App 컨테이너 즉시 종료

**문제**

Spring Boot 컨테이너가 실행 직후 종료되며 DB 연결에 실패했습니다.

**원인**

`depends_on`은 컨테이너 **시작 순서**만 보장하고, MySQL이 실제로 요청을 받을 준비가 되었는지는 보장하지 않습니다.

**해결**

```bash
docker compose restart app
```

근본적인 해결을 위해서는 `healthcheck` + `condition: service_healthy` 설정으로 MySQL 준비 완료 후 앱이 기동되도록 구성할 수 있습니다.

---

### 3. Redis 직렬화 오류

**문제**

```
SerializationException: Cannot serialize
```

**원인**

Redis 직렬화 방식이 명시되지 않아 기본 직렬화 설정이 객체를 처리하지 못했습니다.

**해결**

`RedisConfig`에서 직렬화 방식을 명시적으로 지정했습니다.

```java
@Bean
public RedisCacheConfiguration redisCacheConfiguration() {
    return RedisCacheConfiguration.defaultCacheConfig()
        .serializeValuesWith(
            RedisSerializationContext.SerializationPair
                .fromSerializer(new GenericJackson2JsonRedisSerializer())
        );
}
```

---

### 4. Docker 환경 초기 데이터 미생성

**문제**

Docker 환경에서 애플리케이션 실행 시 초기 데이터가 생성되지 않았습니다.

**원인**

`InitDataConfig`가 `local` 프로파일에서만 동작하도록 설정되어 있어 Docker 환경에서는 실행되지 않았습니다.

**해결**

`docker` 프로파일을 추가했습니다.

```java
@Profile({"local", "docker"})
```

---

### 5. EC2 메모리 부족으로 인한 빌드 중단 및 502 오류

**문제**

EC2 서버에서 Gradle Build 수행 중 아래 상태에서 1시간 이상 진행되지 않았습니다.

```
78% EXECUTING
:test > 0 tests completed
```

이후 배포된 서버에 접속하면 아래 오류가 발생했습니다.

```
502 Bad Gateway
```

컨테이너 상태를 확인한 결과 Spring Boot 앱 컨테이너가 종료된 상태였습니다.

```bash
sudo docker ps -a
# studyroom-app   Exited (1)
```

**원인**

두 문제 모두 EC2 인스턴스(`t3.micro`)의 메모리 부족이 근본 원인이었습니다.

- **빌드 중단**: 테스트 실행 과정에서 Spring Boot + MySQL + Redis + Docker를 동시에 구동하기에 메모리가 부족해 빌드가 멈췄습니다.
- **502 오류**: 리소스 부족으로 앱 컨테이너가 정상 기동되지 못하면서 Nginx → Spring Boot 연결이 실패했습니다.

```
Client → Nginx        ← 정상
Nginx  → Spring Boot  ← 연결 실패 → 502
```

**해결**

우선 테스트를 제외한 빌드로 빌드 중단 문제를 해결했습니다.

```bash
./gradlew clean build -x test
```

앱 컨테이너는 재기동으로 임시 복구했습니다.

```bash
sudo docker compose restart app
```

근본적으로는 EC2 인스턴스 타입을 `t3.micro` → `t3.small`로 상향하여 빌드와 서비스가 안정적으로 운영되도록 개선했습니다.

---

## 💡 학습 회고

이 프로젝트를 통해 Spring Boot 백엔드 프로젝트의 전체 흐름을 하나의 코드베이스에서 경험할 수 있었습니다.

**가장 인상 깊었던 부분**

- **QueryDSL 동적 검색**: JPQL/Criteria와 달리 타입 안전하게 조건을 조합할 수 있다는 점이 실용적이었습니다. null 체크를 통한 조건 분기가 가독성 있게 표현되는 것이 인상적이었습니다.

- **Spring Security 인증 흐름**: `Filter → SecurityContext → Controller`로 이어지는 흐름을 직접 구현하면서 스프링 시큐리티가 어떻게 요청 단위로 인증을 관리하는지 이해할 수 있었습니다.

- **Docker + Nginx 인프라 구성**: 컨테이너 간 통신, Reverse Proxy 설정, 컨테이너 실행 순서 문제 등 로컬 개발과 다른 운영 환경의 복잡성을 직접 경험할 수 있었습니다.

- **Redis 캐싱**: `@Cacheable` 한 줄로 캐싱이 적용되는 편의성 뒤에 직렬화 설정, TTL 관리 등 고려해야 할 요소가 있다는 것을 배웠습니다.

- **예외 처리 표준화**: `CustomException + ErrorCode` 구조로 모든 예외를 일관되게 관리하는 것이 유지보수에 얼마나 중요한지 체감했습니다.

- **AWS EC2 배포 경험**: 로컬에서 잘 돌아가던 환경이 서버에서는 인스턴스 사양 하나로 빌드가 멈추고 서비스가 중단되는 걸 직접 겪으면서, 운영 환경에서는 리소스 설계도 개발만큼 중요하다는 것을 체감했습니다.

**앞으로 개선하고 싶은 점**

- [ ] Refresh Token 도입으로 보안 강화
- [ ] CI/CD 자동 배포 (GitHub Actions)
- [ ] AWS RDS 분리
- [ ] 모니터링 구축
- [ ] 테스트 고도화 (단위/통합)
- [ ] 예약 알림 기능 (이메일/SMS)

---

