# 구현
## 기술 스택
- Language
    - Java 17
- Framework
    - Spring Boot 3.3.0
- Database
    - JPA/Hibernate
    - H2 database
- Test
    - JUnit5
    - SpringBootTest
- Build
    - Gradle with Kotlin

## 구현 범위
- API 명세 (resources/openapi.yaml 참조)
    - Openapi 스펙 링크: http://127.0.0.1:8080/test/swagger.html
- 테스트 코드
    - Controller, Service, Util 등의 테스트 코드 작성 (예정)
# 코드 빌드, 테스트, 실행 방법
## 코드 빌드 및 서버 실행 방법 (Docker)
```sh
docker build -t jsk-test -f docker/Dockerfile .
docker run -p 8080:8080 jsk-test
```
