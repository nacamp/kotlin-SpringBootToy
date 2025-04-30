# Getting Started

## case: exception
```kotlin
throw UnauthorizedException("User not found")

GlobalExceptionHandler {
    //...
    @ExceptionHandler(UnauthorizedException::class)
    fun handleUnauthorized(e: UnauthorizedException): ResponseEntity<Map<String, Any>> {
        // ...
    }
}
// 이곳에 개별, 전체예외 처리를 할 수 있고, 필요하면 에러시 공통된 리턴포맷을 설정하면 된다.
```

## case: sqlite
- https://www.devkobe24.com/Spring/2024-10-17-spring-jpa-properties-hibernate-dialect.html
- https://spring.io/guides/gs/accessing-data-mysql
```dtd
application.properties : db설정
        
// in gradle
id("org.jetbrains.kotlin.plugin.jpa") version "1.9.25"
...
implementation("org.springframework.boot:spring-boot-starter-data-jpa")
implementation("org.xerial:sqlite-jdbc:3.42.0.0")
implementation("org.hibernate.orm:hibernate-community-dialects")

```

## case: Add JWT authentication
- https://spring.io/guides
- https://spring.io/guides/gs/securing-web
- https://github.com/auth0/java-jwt
- https://hongjinkwon.tistory.com/52
```
// in gradle
implementation("com.auth0:java-jwt:4.5.0")
implementation("org.springframework.boot:spring-boot-starter-security")
```

## case: Spring Web 추가 후 작업
- https://start.spring.io/
```dtd
ADD dependecies 에
Spring Web 추가
```

## operation
```bash
./gradlew bootRun
```