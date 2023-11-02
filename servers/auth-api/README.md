## Auth Api

### 로컬 서버 실행하기
- VM Options
  ```text
  -Duser.timezone=UTC -Djasypt.encryptor.password=RlVdb0UHFlTVTi2VUnHMLnfAfoEB7dDbztnFuphpafjg1z4SMxeBEsF4fKmt7zY0
  ```
- Gradle Environment variables
  ```text
  SPRING_PROFILES_ACTIVE=local;JASYPT_ENCRYPTOR_PASSWORD=RlVdb0UHFlTVTi2VUnHMLnfAfoEB7dDbztnFuphpafjg1z4SMxeBEsF4fKmt7zY0
  ```
  
위 코드는 예제입니다. Djasypt.encryptor.password, JASYPT_ENCRYPTOR_PASSWORD 값으로 실제 키를 넣어줘야 합니다.

### 문서 자동화
- pet-diary > servers > auth-api > documentation > openapi3 실행
- [_http://localhost:8081/swagger-ui.html_](http://localhost:8081/swagger-ui.html) 접속
- docker swagger-ui 사용시
  ```shell
  sudo docker run -d -p 8080:8080 -e URLS='[{"url": "http://localhost:8081/static/swagger-ui/openapi3.yaml", "name": "pet-diary-auth-api"}]' --name swagger swaggerapi/swagger-ui
  ```