# wanted-pre-onboarding-backend
원티드 프리온보딩 백엔드 인턴쉽 과제

---

- Entity
  - Apply
  - Company
  - CompanyRole
  - Date
  - JobPosition
  - Role
  - User


- DB Table
  - apply
    - job_position(fk) 1:N
    - user(fk) 1:1
  - company
  - company_role
    - company(fk) 1:N
    - role(fk) 1:N
  - job_position
    - company_role(fk) 1:1
  - role
  - user
  

---

## RESTful API 목록
swagger URL http://localhost:8080/swagger-ui/index.html
1. 채용 공고 등록 POST 
2. 채용 공고 수정 PUT 
3. 채용 공고 삭제 DELETE
4. 채용 공고 검색/목록 GET
5. 채용 공고 상세 GET
6. 사용자 채용 공고 지원 POST
7. + 사용자 채용 공고 취소 DELETE
8. + 회사 등록 POST
8. + 직무 역할 등록 POST
9. + 회사별 직무 역할 조회 GET
