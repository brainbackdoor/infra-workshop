description = "backend for front module"


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

    // Feign
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign:4.0.4")
    implementation("io.github.openfeign:feign-httpclient:11.0")
}