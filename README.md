# Pet Diary
반려동물, 반려식물의 성장일지를 기록하고 사람들과 교류할 수 있는 토이프로젝트 입니다.

## 개발 환경 및 구성
### Common

### Design

### Backend
- corretto-20.0.1
- spring boot 3.1.1
- gradle 8.1.1
- jpa(Java Persistent API)
- 사용 예정 AWS 서비스
    - SES
    - SNS
    - S3
    - ECR
    - ECS
- 소셜 로그인 구현
  - 구글
  - 카카오
  - 네이버
- 참고 문서 및 코드
  - [_Jasypt 암호화_](./core/src/test/java/JasyptTests.java)
  - [_mysql & redis_](./domain/README.md)
  - [_auth-api-server_](./servers/auth-api/README.md)
- 참고 외부 링크
    - [_OpenAPI Specification을 이용한 더욱 효과적인 API 문서화_](https://tech.kakaopay.com/post/openapi-documentation/)

### Frontend
