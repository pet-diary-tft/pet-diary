## 데이터베이스
- 로컬 테스트용 도커 mysql 서버 생성
  ```shell
  docker run -d -p 3306:3306 -e MYSQL_ROOT_PASSWORD=P@ssw0rd --name test-mysql-db --character-set-server=utf8mb4 --collation-server=utf8mb4_unicode_ci
  ```
- 계정 생성 예시
  ```sql
  USE mysql;
  CREATE USER 'petdiaryuser'@'%' IDENTIFIED BY '1q2w3e4r5t@#';
  CREATE DATABASE pet_diary;
  GRANT SELECT, INSERT, UPDATE, DELETE ON pet_diary.* TO 'petdiaryuser'@'%';
  FLUSH PRIVILEGES;
  ```
