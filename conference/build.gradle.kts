description = "conference module"

plugins {
    id("org.flywaydb.flyway") version "6.0.4"
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:2022.0.3")
    }
}

dependencies {
    api(project(":core"))
    api(project(":core"))

    // Spring and Spring Boot dependencies
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")

    // Databases
    runtimeOnly("com.h2database:h2")
    runtimeOnly("com.mysql:mysql-connector-j")
    runtimeOnly("org.flywaydb:flyway-core")
    runtimeOnly("org.flywaydb:flyway-mysql")

    // Feign
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign:4.0.3")
    implementation("io.github.openfeign:feign-httpclient:11.0")
}