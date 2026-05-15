<div align="center">

```
╔══════════════════════════════════════════════════════╗
║                                                      ║
║   📚  JPA Study Room Reservation API                 ║
║                                                      ║
╚══════════════════════════════════════════════════════╝
```

**Spring Boot + JPA + QueryDSL** 기반의 스터디룸 예약 백엔드 API

단순 CRUD를 넘어 JWT 인증, 동적 쿼리, 페이징, 전역 예외 처리까지  
**실무 수준의 백엔드 구조**를 직접 구현하며 학습한 프로젝트입니다.

<br>

![Java](https://img.shields.io/badge/Java-21-orange?style=flat-square&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3-6DB33F?style=flat-square&logo=springboot&logoColor=white)
![Spring Security](https://img.shields.io/badge/Spring_Security-6DB33F?style=flat-square&logo=springsecurity&logoColor=white)
![JPA](https://img.shields.io/badge/Spring_Data_JPA-6DB33F?style=flat-square&logo=spring&logoColor=white)
![QueryDSL](https://img.shields.io/badge/QueryDSL-5-blue?style=flat-square)
![JWT](https://img.shields.io/badge/JWT-000000?style=flat-square&logo=jsonwebtokens&logoColor=white)
![H2](https://img.shields.io/badge/H2_Database-1232C3?style=flat-square)
![Swagger](https://img.shields.io/badge/Swagger-85EA2D?style=flat-square&logo=swagger&logoColor=black)
![Gradle](https://img.shields.io/badge/Gradle-02303A?style=flat-square&logo=gradle&logoColor=white)

</div>

---

## 📌 목차

- [프로젝트 소개](#-프로젝트-소개)
- [기술 스택](#-기술-스택)
- [주요 기능](#-주요-기능)
- [프로젝트 구조](#-프로젝트-구조)
- [ERD](#-erd)
- [API 명세](#-api-명세)
- [JWT 인증 흐름](#-jwt-인증-흐름)
- [트러블슈팅](#-트러블슈팅)
- [학습 회고](#-학습-회고)

---

## 🚀 프로젝트 소개

> **"단순 CRUD 구현을 넘어, 실무형 백엔드 설계를 직접 경험하는 것"** 이 이 프로젝트의 핵심 목표입니다.

스터디룸 예약 시스템이라는 현실적인 도메인을 통해 아래 요소들을 직접 설계하고 구현했습니다.

| 구분 | 적용 기술 | 목적 |
|------|-----------|------|
| 인증 | JWT + Spring Security | 무상태(Stateless) 인증 구조 이해 |
| 조회 | QueryDSL + Pageable | 동적 쿼리 및 페이징 처리 |
| 예외 | GlobalExceptionHandler | 일관된 에러 응답 포맷 |
| 응답 | 공통 응답 포맷 | API 응답 표준화 |
| 검증 | Spring Validation | 입력값 유효성 처리 |

---

## 🛠 기술 스택

```
Language     │  Java 21
Framework    │  Spring Boot 3
Security     │  Spring Security 6 + JWT
ORM          │  Spring Data JPA + QueryDSL 5
Database     │  H2 (In-Memory)
Docs         │  Swagger (springdoc-openapi)
Build        │  Gradle
```

---

## ✅ 주요 기능

### 👤 회원 (Member)

- **회원가입** — 이메일/비밀번호 기반 회원 등록
- **로그인** — JWT Access Token 발급

### 📅 예약 (Reservation)

- **예약 생성** — 스터디룸 예약 (시간 중복 검증 포함)
- **예약 조회** — 내 예약 목록 조회
- **동적 검색** — QueryDSL 기반 조건부 필터링 (날짜, 룸 등)
- **페이징** — `Pageable` 기반 커서/오프셋 페이징

### 🔐 인증 / 보안

- JWT 기반 Stateless 인증
- `JwtAuthenticationFilter`에서 토큰 파싱 및 검증
- `SecurityContextHolder`에 인증 정보 저장
- 인증된 사용자 기반 API 접근 제어

### ⚙️ 공통 처리

- `ApiResponse<T>` 공통 응답 래퍼
- `GlobalExceptionHandler` — 전역 예외 처리
- `@Valid` 기반 요청 유효성 검증

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
└── 📂 global
    ├── 📂 config          # Security, QueryDSL 설정
    ├── 📂 exception       # 예외 클래스 및 GlobalExceptionHandler
    ├── 📂 response        # ApiResponse 공통 응답 포맷
    └── 📂 security        # JWT 유틸리티 및 Filter
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

## 🐛 트러블슈팅

### 예약 시간 중복 검증 문제

**문제 상황**

처음에는 예약 가능 여부를 어떤 조건으로 판단해야 할지 명확하지 않았습니다.

```
기존 예약:  |████████████|
             10:00     12:00

새 예약:         |████████████|
                 11:00     13:00
                 
    → 겹침! 하지만 어떤 조건으로 판단할 것인가?
```

**해결 방법**

단순히 시작/종료 시간을 비교하는 대신, **시간 범위가 겹치는 역조건**을 정의했습니다.

```java
// 겹치는 조건 (JPQL/QueryDSL)
// 새 예약의 시작이 기존 예약의 종료보다 이전이고
// 새 예약의 종료가 기존 예약의 시작보다 이후인 경우

where reservation.startTime < :newEndTime
  and reservation.endTime   > :newStartTime
```

**검증 케이스**

| 케이스 | 기존 예약 | 새 예약 | 결과 |
|--------|-----------|---------|------|
| 완전 포함 | 10:00~12:00 | 10:30~11:30 | ❌ 중복 |
| 앞부분 겹침 | 10:00~12:00 | 09:00~11:00 | ❌ 중복 |
| 뒷부분 겹침 | 10:00~12:00 | 11:00~13:00 | ❌ 중복 |
| 완전 이전 | 10:00~12:00 | 08:00~10:00 | ✅ 예약 가능 |
| 완전 이후 | 10:00~12:00 | 12:00~14:00 | ✅ 예약 가능 |

> 핵심: **겹치지 않는 조건의 반대**가 겹치는 조건임을 이용한 논리 역전

---

## 💡 학습 회고

이 프로젝트를 통해 Spring Boot 백엔드 프로젝트의 전체 흐름을 하나의 코드베이스에서 경험할 수 있었습니다.

**가장 인상 깊었던 부분**

- **QueryDSL 동적 검색**: JPQL/Criteria와 달리 타입 안전하게 조건을 조합할 수 있다는 점이 실용적이었습니다. null 체크를 통한 조건 분기가 가독성 있게 표현되는 것이 인상적이었습니다.

- **Spring Security 인증 흐름**: `Filter → SecurityContext → Controller`로 이어지는 흐름을 직접 구현하면서 스프링 시큐리티가 어떻게 요청 단위로 인증을 관리하는지 이해할 수 있었습니다.

- **예외 처리 표준화**: `GlobalExceptionHandler`와 공통 응답 포맷을 분리함으로써 모든 API가 일관된 구조로 응답하도록 설계하는 것이 얼마나 중요한지 체감했습니다.

**앞으로 개선하고 싶은 점**

- [ ] Refresh Token 도입으로 보안 강화
- [ ] 스터디룸 관리 기능 (CRUD) 추가
- [ ] H2 → MySQL/PostgreSQL 전환
- [ ] 테스트 코드 작성 (단위/통합)
- [ ] 예약 알림 기능 (이메일/SMS)

---

