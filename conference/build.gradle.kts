description = "conference module"

plugins {
    id("org.flywaydb.flyway") version "6.0.4"
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
}