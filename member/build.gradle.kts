description = "member module"

plugins {
    id("org.flywaydb.flyway") version "6.0.4"
}

dependencies {
    api(project(":core"))

    testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")

    // Spring and Spring Boot dependencies
    implementation("org.springframework.boot:spring-boot-starter-security")
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")

    // jwt
    implementation("io.jsonwebtoken:jjwt:0.9.1")

    // Databases
    runtimeOnly("com.h2database:h2")
    runtimeOnly("com.mysql:mysql-connector-j")
    runtimeOnly("org.flywaydb:flyway-core")
    runtimeOnly("org.flywaydb:flyway-mysql")
}