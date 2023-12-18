description = "backend for front module"


dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:2022.0.3")
    }
}

dependencies {
    implementation(project(":core"))

    // Spring and Spring Boot dependencies
    implementation("org.springframework.boot:spring-boot-starter-aop")

    // Feign
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign:4.0.4")
    implementation("io.github.openfeign:feign-httpclient:11.0")

    // Swagger
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.0.2")

    // CircuitBreaker
    implementation("org.springframework.cloud:spring-cloud-starter-circuitbreaker-resilience4j")
}