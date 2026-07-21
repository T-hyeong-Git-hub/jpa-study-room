<div align="center">

# 📚 JPA Study Room Reservation API

**Spring Boot + JPA + QueryDSL** 기반의 스터디룸 예약 백엔드 API

단순 CRUD를 넘어 JWT 인증, 동적 쿼리, 페이징, 전역 예외 처리,  
Redis 캐싱, Docker 컨테이너화, AWS EC2 배포, GitHub Actions CI/CD까지  
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
![GitHub Actions](https://img.shields.io/badge/GitHub_Actions-2088FF?style=flat-square&logo=githubactions&logoColor=white)
![Docker Hub](https://img.shields.io/badge/Docker_Hub-2496ED?style=flat-square&logo=docker&logoColor=white)
![Swagger](https://img.shields.io/badge/Swagger-85EA2D?style=flat-square&logo=swagger&logoColor=black)
![Gradle](https://img.shields.io/badge/Gradle-02303A?style=flat-square&logo=gradle&logoColor=white)

</div>

---

## 🏗 시스템 아키텍처

```
                  Git Push
                      │
                      ▼
            GitHub Actions (CI)
                      │
       ┌──────────────┴──────────────┐
       │  Gradle Build               │
       │  Docker Image Build         │
       │  Docker Hub Push            │
       └──────────────┬──────────────┘
                      │
                      ▼
                AWS EC2 (CD)
                      │
               docker compose pull
                      │
               docker compose up -d
                      │
                ┌─────┴─────┐
                │   Nginx   │
                └─────┬─────┘
                      │
                Spring Boot
               ┌──────┴──────┐
               │             │
             MySQL         Redis
```

---

## 📌 목차

- [프로젝트 소개](#프로젝트-소개)
- [기술 스택](#기술-스택)
- [주요 기능](#주요-기능)
- [프로젝트 구조](#프로젝트-구조)
- [ERD](#erd)
- [API 명세](#api-명세)
- [JWT 인증 구조](#jwt-인증-구조)
- [Refresh Token 도입](#refresh-token-도입)
- [Redis를 사용한 이유](#redis를-사용한-이유)
- [로그아웃 처리 방식](#로그아웃-처리-방식)
- [Docker 환경 구성](#docker-환경-구성)
- [CI/CD 파이프라인](#cicd-파이프라인)
- [실행 방법](#실행-방법)
- [테스트 과정](#테스트-과정)
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
| CI/CD | GitHub Actions + Docker Hub | Git Push만으로 자동 배포되는 파이프라인 구축 |

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
CI/CD        │  GitHub Actions · Docker Hub
```

---

## ✅ 주요 기능

### 👤 회원 (Member)

- **회원가입** — 이메일/비밀번호 기반 회원 등록 (비밀번호 암호화)
- **로그인** — JWT Access Token / Refresh Token 발급

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
jpa-study-room
│
├── 📂 .github
│   └── 📂 workflows
│       └── build.yml                    # GitHub Actions CI/CD 워크플로우
│
├── 📂 src/main/java/com/kkth/jpaStudyRoom
│   ├── 📂 domain
│   │   ├── 📂 member                    # 회원 도메인 (Entity, Repository, Service, Controller)
│   │   ├── 📂 reservation               # 예약 도메인
│   │   └── 📂 room                      # 스터디룸 도메인
│   │
│   ├── 📂 global
│   │   ├── 📂 config                    # Security, QueryDSL, Redis 설정
│   │   ├── 📂 exception                 # CustomException, ErrorCode, GlobalExceptionHandler
│   │   ├── 📂 response                  # ApiResponse 공통 응답 포맷
│   │   └── 📂 security                  # JWT 인증 및 Refresh Token 관련 클래스
│   │       ├── JwtAuthenticationFilter.java
│   │       ├── JwtAuthenticationToken.java
│   │       ├── JwtProvider.java
│   │       ├── RefreshTokenService.java  # Refresh Token 발급 · Redis 저장 · 로그아웃 처리
│   │       └── SecurityConfig.java
│   │
│   └── 📂 support                       # 공통 유틸리티
│
├── docker-compose.yml                   # 멀티 컨테이너 구성
└── Dockerfile                           # Spring Boot 이미지 빌드
```

> 각 도메인은 **Entity → Repository → Service → Controller** 레이어 구조를 따르며,  
> 도메인별로 패키지를 분리하여 응집도를 높였습니다.  
> Refresh Token 발급 및 Redis 저장/삭제는 `global/security/RefreshTokenService.java`에서 처리합니다.

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

#### `POST /api/members/login` — 로그인 (Access Token + Refresh Token 발급)

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
  "memberId": 1,
  "email": "example@test.com",
  "name": "홍길동",
  "accessToken": "access-token-value",
  "refreshToken": "refresh-token-value"
}
```

> 로그인 성공 시 Access Token과 Refresh Token을 함께 발급합니다.  
> Refresh Token은 Redis에 저장되며, Access Token 만료 시 재발급에 사용됩니다.

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

#### `GET /health` — 배포 상태 확인

자동 배포 성공 여부 검증을 위한 Health Check API입니다.

```json
{ "version": "Deploy Version 2" }
```

> 배포 후 응답 버전이 변경되는 것으로 자동 배포 성공 여부를 확인합니다.

---

## 🔐 JWT 인증 구조

본 프로젝트는 JWT 기반 인증 방식을 사용하였습니다.

| 토큰 | 용도 |
|------|------|
| Access Token | API 인증용 |
| Refresh Token | Access Token 재발급용 |

로그인 성공 시 두 토큰을 모두 발급하며, Refresh Token은 Redis에 저장합니다.

```
Client                        Server
  │                              │
  │  POST /api/members/login     │
  │ ────────────────────────────▶│
  │                              │  ① 이메일/비밀번호 검증
  │                              │  ② Access Token 생성 (type=access)
  │                              │  ③ Refresh Token 생성 (type=refresh)
  │                              │  ④ Refresh Token → Redis 저장
  │  ◀────────────────────────── │
  │  { accessToken, refreshToken }│
  │                              │
  │  GET /api/reservations       │
  │  Authorization: Bearer eyJ.. │
  │ ────────────────────────────▶│
  │                              │  ⑤ JwtAuthenticationFilter
  │                              │     - 토큰 파싱 & 서명 검증
  │                              │     - type=access 확인
  │                              │     - SecurityContext에 인증 저장
  │                              │  ⑥ 인증된 사용자 기반 API 처리
  │  ◀────────────────────────── │
  │  { success: true, data: .. } │
```

**흐름 요약**

1. 클라이언트가 이메일/비밀번호로 로그인 요청
2. 서버가 Access Token + Refresh Token 발급, Refresh Token은 Redis 저장
3. 이후 API 요청 시 `Authorization: Bearer {accessToken}` 헤더 첨부
4. `JwtAuthenticationFilter`가 토큰 유효성 및 `type=access` 검증
5. `SecurityContextHolder`에 인증 정보 등록
6. 컨트롤러에서 `@AuthenticationPrincipal`로 현재 사용자 조회
7. Access Token 만료 시 Refresh Token으로 Refresh API 호출 → `RefreshTokenService`에서 Redis 검증 후 새 Access Token 발급

```
Access Token 만료
        │
        ▼
POST /api/members/refresh (Refresh Token 전달)
        │
        ▼
Redis에서 Refresh Token 유효성 확인
        │
        ▼
새 Access Token 발급
```

---

## 🔄 Refresh Token 도입

기존에는 Access Token 하나만 사용하였기 때문에 토큰이 만료되면 사용자가 다시 로그인해야 했습니다.

이를 개선하기 위해 Refresh Token을 도입했습니다.

| 항목 | Access Token | Refresh Token |
|------|-------------|---------------|
| 용도 | API 인증 | Access Token 재발급 |
| 저장 위치 | 클라이언트 | Redis |
| 만료 시간 | 짧게 설정 | 길게 설정 |
| type Claim | `access` | `refresh` |

**type Claim을 추가한 이유**

토큰 용도를 Claim으로 명시하여 Refresh Token을 Access Token 용도로 사용하거나, 그 반대의 경우를 서버에서 차단할 수 있습니다.

```
Access Token  → type=access  Claim 포함
Refresh Token → type=refresh Claim 포함
```

`JwtAuthenticationFilter`에서 `type=access`인 토큰만 API 인증에 허용하며, Refresh API에서는 `type=refresh`인 토큰만 허용합니다.

---

## 🗃 Redis를 사용한 이유

Refresh Token 저장소로 관계형 데이터베이스 대신 **메모리 기반 저장소인 Redis**를 선택한 이유는 두 가지입니다.

**빠른 조회 성능**

Access Token 재발급 요청마다 Refresh Token 유효성을 확인해야 하므로, DB보다 훨씬 빠른 Redis 인메모리 조회가 적합합니다.

**TTL(Time To Live) 자동 만료**

```bash
Redis SET refresh:{userId} {token} EX {만료시간(초)}
```

TTL을 설정하면 Refresh Token이 만료되는 시점에 Redis에서 자동으로 삭제되므로, 별도의 만료 처리 로직 없이 토큰 생명주기를 관리할 수 있습니다.

---

## 🚪 로그아웃 처리 방식

로그아웃 시 Redis에 저장된 Refresh Token을 삭제합니다.

```
로그아웃 요청
    │
    ▼
Redis에서 Refresh Token 삭제
    │
    ▼
이후 기존 Refresh Token으로 재발급 불가
```

**Access Token 처리**

현재 프로젝트에서는 Access Token은 만료 시까지 유효하도록 구현했습니다.

이는 JWT의 Stateless 특성을 활용한 일반적인 방식입니다. 서버는 토큰을 별도로 저장하지 않기 때문에 발급된 Access Token을 즉시 무효화할 수 없으며, 프론트엔드에서 로그아웃 시 보유한 Access Token을 삭제하여 사용자 입장에서는 즉시 로그아웃된 것처럼 동작합니다.

> **향후 개선 방향**: Redis BlackList를 적용하여 로그아웃 시 해당 Access Token을 블랙리스트에 등록하고, 이후 요청에서 블랙리스트에 포함된 토큰을 차단하는 방식으로 Access Token까지 즉시 무효화할 수 있습니다.

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

### 컨테이너 구성도

```
        studyroom-nginx
               │
               ▼
        studyroom-app
          │         │
          ▼         ▼
   studyroom-mysql  studyroom-redis
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

## ⚡ CI/CD 파이프라인

### 구축 목표

Git Push만으로 EC2 서버에 자동 배포되는 환경을 구축했습니다.

### 전체 흐름

```
Developer
    │
  git push
    │
    ▼
GitHub Actions
    │
    ├── ① Spring Boot Build (Gradle)
    ├── ② Docker Image Build
    └── ③ Docker Hub Push
              │
              ▼
           EC2
              │
    ├── ④ docker compose pull
    ├── ⑤ docker compose down
    └── ⑥ docker compose up -d
              │
              ▼
           Nginx
              │
              ▼
        Spring Boot
```

### GitHub Actions Workflow

```
1. Repository Checkout
      ↓
2. JDK 21 설치
      ↓
3. Gradle Build
      ↓
4. Docker Image Build
      ↓
5. Docker Hub Push
      ↓
6. EC2 SSH 접속
      ↓
7. docker compose pull
      ↓
8. docker compose down
      ↓
9. docker compose up -d
```

### Docker Hub를 도입한 이유

초기에는 EC2에서 직접 빌드하는 방식을 사용했습니다.

```
EC2: git pull → gradlew build → docker compose build
```

이 방식의 문제점:
- EC2에서 Gradle Build + Docker Build를 모두 수행하여 배포에 **약 10~20분** 소요
- EC2 CPU/메모리 부하 집중

Docker Hub 기반으로 변경한 후:

```
GitHub Actions: Build → Docker Image → Docker Hub Push
EC2: docker pull → docker compose up
```

| 방식 | 배포 시간 | EC2 부하 |
|------|-----------|----------|
| EC2 직접 Build | 약 10~20분 | CPU/메모리 집중 |
| Docker Hub Pull | 약 1~2분 | Pull만 수행 |

### Health Check API

자동 배포 성공 여부를 검증하기 위해 `GET /health` API를 추가했습니다.  
배포 후 응답 버전이 변경되는 것을 확인하여 배포 성공을 검증합니다.

```
배포 전: { "version": "Deploy Version 1" }
배포 후: { "version": "Deploy Version 2" }
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

### CI/CD 자동 배포 (GitHub Actions)

GitHub Actions가 설정된 경우 `main` 브랜치에 Push하면 자동으로 배포됩니다.

```bash
git push origin main
```

배포 완료 후 Health Check API로 배포 성공 여부를 확인합니다.

```
GET http://EC2_PUBLIC_IP/health
```

---

## 🧪 테스트 과정

직접 구현한 기능들을 아래 순서로 검증했습니다.

### 로그인

- 이메일/비밀번호로 로그인 요청
- Access Token 발급 확인
- Refresh Token 발급 확인

### Redis 저장 확인

`redis-cli`를 이용하여 Refresh Token 저장 및 TTL 감소를 직접 확인했습니다.

```bash
redis-cli
> keys *                   # refresh:{userId} 키 존재 확인
> get refresh:{userId}     # 저장된 Refresh Token 값 확인
> ttl refresh:{userId}     # TTL 감소 확인
```

### Refresh API

- Refresh Token으로 Refresh API 호출
- 새 Access Token 재발급 성공 확인

### 로그아웃

- Logout API 요청
- Redis에서 Refresh Token 삭제 확인

```bash
> keys *   # 키가 삭제된 것 확인
```

### 로그아웃 이후

- 삭제된 Refresh Token으로 재발급 재시도 → 실패 확인

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

### 2. 컨테이너 실행 순서 문제

**문제**

Docker Compose 실행 시 두 가지 문제가 연쇄적으로 발생했습니다.

- Spring Boot가 MySQL보다 먼저 실행되어 DB 연결에 실패하고 컨테이너가 즉시 종료됨
- Nginx가 Spring Boot보다 먼저 실행되어 백엔드 연결 대상이 없어 502 오류 발생

**원인**

`depends_on`은 컨테이너 **시작 순서**만 보장하고, 각 서비스가 실제로 요청을 받을 준비가 되었는지는 보장하지 않습니다.

**해결**

MySQL과 Spring Boot에 각각 `healthcheck`를 추가하고, `depends_on`에 `condition: service_healthy`를 적용하여 서비스가 완전히 준비된 이후 다음 컨테이너가 기동되도록 수정했습니다.

```yaml
# Spring Boot: MySQL이 준비된 후 실행
depends_on:
  mysql:
    condition: service_healthy

# Nginx: Spring Boot가 준비된 후 실행
depends_on:
  app:
    condition: service_healthy
```

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

### 6. EC2 SSH 접속 실패

**문제**

```
Permissions for 'studyroom-key.pem' are too open.
```

GitHub Actions에서 EC2 SSH 접속이 실패했습니다.

**원인**

Windows 환경에서 PEM 파일 권한이 너무 넓게 설정되어 있었습니다.

**해결**

PEM 파일 접근 권한을 현재 사용자만 읽을 수 있도록 수정했습니다.

```bash
chmod 400 studyroom-key.pem
```

---

### 7. GitHub Actions Deploy Timeout

**문제**

GitHub Actions Deploy 단계에서 아래 오류가 발생했습니다.

```
Run Command Timeout
```

**원인**

EC2에서 Gradle Build + Docker Build를 모두 수행하면서 실행 시간이 10분을 초과했습니다.

**해결**

Docker Hub 기반 배포 방식으로 변경했습니다. EC2에서는 Docker Image Pull만 수행하도록 구조를 변경하여 배포 시간을 대폭 단축했습니다.

---

### 8. EC2 Disk Full

**문제**

Docker Image Pull 중 아래 오류가 발생했습니다.

```
no space left on device
```

**원인**

EC2 Root Volume이 8GB로 설정되어 있어 Docker Image를 저장하기에 용량이 부족했습니다.

**해결**

EBS Volume을 8GB → 20GB로 확장하여 Docker Image Pull이 정상 수행되도록 했습니다.

---

### 9. 403 Forbidden (`GET /health`)

**문제**

`GET /health` 호출 시 아래 오류가 발생했습니다.

```
403 Forbidden
```

**원인**

Spring Security에서 `/health` 엔드포인트가 인증이 필요한 API로 설정되어 있었습니다.

**해결**

JWT 인증 후 호출하여 정상 응답을 확인했습니다. Security 설정에 따른 정상 동작임을 확인했으며, 필요 시 Security 설정에서 `/health`를 인증 예외 경로로 등록할 수 있습니다.

---

### 10. Refresh Token과 Access Token이 동일하게 생성되는 문제

**문제**

Refresh Token과 Access Token이 동일한 형태로 생성되어 용도 구분이 불가능했습니다.

**원인**

두 토큰에 동일한 만료 시간(`Expiration`)을 사용하고 있었으며, 토큰 종류를 구분하는 Claim이 없어 어떤 토큰이든 API 인증과 재발급 모두에 사용될 수 있었습니다.

**해결**

Access Token과 Refresh Token의 만료 시간을 각각 분리하고, 각 토큰에 `type` Claim을 추가하여 서버에서 용도에 맞는 토큰만 허용하도록 수정했습니다.

```java
// Access Token: 짧은 만료 시간 + type=access
claims.put("type", "access");
// expiration = accessTokenExpiration

// Refresh Token: 긴 만료 시간 + type=refresh
claims.put("type", "refresh");
// expiration = refreshTokenExpiration
```

이후 `JwtAuthenticationFilter`에서는 `type=access`인 토큰만 인증에 허용하고, `RefreshTokenService`에서는 `type=refresh`인 토큰만 재발급에 허용합니다.

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

- **GitHub Actions CI/CD**: 단순히 배포를 자동화하는 것 이상으로, Docker Hub를 활용한 이미지 기반 배포 구조를 직접 설계하면서 빌드 환경 분리와 배포 속도 개선의 중요성을 실감했습니다.

**앞으로 개선하고 싶은 점**

- [x] Refresh Token 도입 ✅
- [x] CI/CD 자동 배포 (GitHub Actions) ✅
- [ ] Access Token BlackList 적용
- [ ] Refresh Token Rotation 적용
- [ ] HttpOnly Cookie 기반 JWT 인증
- [ ] AWS RDS 분리
- [ ] Prometheus + Grafana 모니터링 구축
- [ ] 테스트 코드 고도화
- [ ] 예약 알림 기능

---

