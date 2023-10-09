description = "core module"


dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:2022.0.3")
    }
}

dependencies {
    // Feign
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign:4.0.4")
    implementation("io.github.openfeign:feign-httpclient:11.0")

    // Swagger
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.0.2")
}