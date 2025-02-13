# SpringW3SP

## 📌 기본 정보
- **Base URL**: `http://api.example.com`

## 📖 ERD
| 테이블명 | 필드명 | 데이터타입 | 설명 |
|---------|--------|-----------|-----|


## 📖 API 목록
| 기능 | 메서드 | URL | request | response | 상태코드 |
|------|--------|------------|------------|------------|-------|
| **일정 생성** | `POST` | `/schedules` | 없음 | 없음 | 201 CREATED, 400 BAD REQUEST
| **전체 일정 조회** | `GET` | `/schedules` | 없음 | 다중 응답 정보 | 200 OK
| **선택 일정 조회** | `GET` | `/schedules/{id}` | param : "id" | 단일 응답 정보 | 200 OK, 404 NOT FOUND 
