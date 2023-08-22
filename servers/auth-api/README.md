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