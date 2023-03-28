# ChatProject

[AWS로 배포한 사이트 주소](http://3.34.185.217:8080/register/main)

1. 로컬에서 실행하고 싶다면 sts4에서 import 할 때 Gradle -> Existing Gradle Project로 import 한다.
2. DDL.txt를 복사한 후 MySQL 에 테이블을 만든다.
3. application.properties에서 url 부분을 
spring.datasource.hikari.jdbc-url=jdbc:mysql://localhost:3306/board?serverTimezone=Asia/Seoul&useUnicode=true&characterEncoding=utf8&useSSL=false&allowPublicKeyRetrieval=true 로 바꾼다.
4. application.properties 에서 username 에는 본인의 DB id password로 설정을 바꾼다.


