## 데이터베이스
- 로컬 테스트용 도커 mysql 서버 생성
  ```shell
  docker run -d -p 3306:3306 -e MYSQL_ROOT_PASSWORD=P@ssw0rd --name pet-diary-mysql mysql --character-set-server=utf8mb4 --collation-server=utf8mb4_unicode_ci
  ```
- 계정 생성 예시
  ```sql
  USE mysql;
  CREATE USER 'petdiaryuser'@'%' IDENTIFIED BY '1q2w3e4r5t@#';
  CREATE DATABASE pet_diary;
  GRANT SELECT, INSERT, UPDATE, DELETE ON pet_diary.* TO 'petdiaryuser'@'%';
  CREATE DATABASE pet_diary_membership;
  GRANT SELECT, INSERT, UPDATE, DELETE ON pet_diary_membership.* TO 'petdiaryuser'@'%';
  FLUSH PRIVILEGES;
  ```

## Redis
- 로컬 테스트용 도커 redis 서버 생성
  ```shell
  docker run --name pet-diary-redis -p 6379:6379 -d redis redis-server --requirepass 1q2w3e4r5t@#
  ```
- Redis 서버 접근 및 확인
  ```shell
  # for host redis
  redis-cli -h 127.0.0.1 -p 6379
  # for docker redis
  docker exec -it pet-diary-redis redis-cli
  127.0.0.1:6379> AUTH 1q2w3e4r5t@#
  127.0.0.1:6379> PING
  PONG
  ```