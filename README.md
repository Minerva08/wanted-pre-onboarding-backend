# wanted-pre-onboarding-backend
원티드 프리온보딩 백엔드 인턴쉽 과제

---

## Design Patterns
- Aspect-Oriented Programming (AOP)
  - Validation 및 예외 처리 
  - 관심사 분리
    - 요청값에 대한 유효성 검사를 전역적으로 처리. 
    - 메서드 호출 전후에 유효성 검사를 적용하여 비즈니스 로직과 검증 로직을 분리.
    - 유효하지 않은 입력값 요청 시 AOP를 통해 예외를 자동으로 처리, 서비스 로직에서 예외 처리를 반복하지 않고도 일관된 에러 처리를 구현.
    - 코드의 가독성과 유지보수성을 고려하여 설계.

---

### Entity
  - Apply
  - Company
  - CompanyRole
  - Date
  - JobPosition
  - Role
  - User

### DB 
  - apply
    - apply_num(pk)
    - job_position(fk) 1:N
    - user_num(fk/unique) 1:1
  - company
    - company_num(pk) 
    - company_id(unique)
  - company_role
    - company_role_num(pk)
    - com_num(fk) 1:N
    - role_num(fk) 1:N
  - job_positioning
    - post_num(pk)
    - company_role_num(fk) 1:1
  - role
    - role_num(pk)
    - role_id(unique)
  - user
    - user_num(pk)
    - user_id(unique)
  

---

## RESTful API Swagger 문서 
swagger URL: http://localhost:8080/swagger-ui/index.html
1. 채용 공고 등록 POST 
2. 채용 공고 수정 PUT 
3. 채용 공고 삭제 DELETE
4. 채용 공고 검색/목록 GET
5. 채용 공고 상세 GET
6. 사용자 채용 공고 지원 POST
----
### 추가 개발 사항
7. 사용자 채용 공고 지원 취소 DELETE
8. 회사 등록 POST
9. 직무 역할 등록 POST
10. 회사별 직무 역할 조회 GET
11. 회사 삭제 DELETE
12. 회사 직무 역할 DELETE
