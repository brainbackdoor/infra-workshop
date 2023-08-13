description = "core module"


dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:2022.0.3")
    }
}

dependencies {
    // Feign
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign:4.0.5")
    implementation("io.github.openfeign:feign-httpclient:11.0")
}