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

    // Databases
    runtimeOnly("com.h2database:h2")
    runtimeOnly("com.mysql:mysql-connector-j")
    runtimeOnly("org.flywaydb:flyway-core")
    runtimeOnly("org.flywaydb:flyway-mysql")

    // Feign
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign:4.0.4")
    implementation("io.github.openfeign:feign-httpclient:11.0")

    // Swagger
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.0.2")

    testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")
    implementation("com.google.guava:guava:31.1-jre")
}