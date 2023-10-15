import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    idea
    id("org.springframework.boot") version "3.0.7"
    id("io.spring.dependency-management") version "1.1.0"
    id("org.jlleitschuh.gradle.ktlint") version "10.1.0"
    kotlin("jvm") version "1.7.22"
    kotlin("plugin.spring") version "1.7.22"
    kotlin("plugin.jpa") version "1.5.21"
}

allprojects {
    group = "com.brainbackdoor"
    version = "0.0.1-SNAPSHOT"
    repositories {
        mavenCentral()
    }
}

subprojects {
    apply {
        plugin("org.jetbrains.kotlin.jvm")
        plugin("org.jetbrains.kotlin.plugin.spring")
        plugin("org.jetbrains.kotlin.plugin.jpa")
        plugin("org.jetbrains.kotlin.plugin.allopen")
        plugin("org.jetbrains.kotlin.plugin.noarg")
        plugin("org.springframework.boot")
        plugin("io.spring.dependency-management")
        plugin("org.jlleitschuh.gradle.ktlint")
    }

    repositories {
        mavenCentral()
    }

    dependencies {
        // Spring and Spring Boot dependencies
        implementation("org.springframework.boot:spring-boot-starter")
        implementation("org.springframework.boot:spring-boot-starter-web")
        implementation("org.springframework.boot:spring-boot-starter-data-jpa")
        implementation("org.springframework.boot:spring-boot-starter-validation")
        implementation("org.springframework.boot:spring-boot-devtools")
        implementation("org.springframework.boot:spring-boot-starter-actuator")
        annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

        // Log
        implementation("net.logstash.logback:logstash-logback-encoder:7.3")
        implementation("io.github.microutils:kotlin-logging:3.0.5")
        runtimeOnly("net.rakugakibox.spring.boot:logback-access-spring-boot-starter:2.7.1")

        // Kotlin
        implementation(kotlin("reflect"))
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

        // Utils
        implementation("org.apache.commons:commons-lang3")
        implementation("commons-io:commons-io:2.11.0")
        implementation("com.jayway.jsonpath:json-path")

        // Test
        testImplementation("io.rest-assured:rest-assured")
        testImplementation("org.jetbrains.kotlin:kotlin-test-junit:1.6.0")
        testImplementation("org.springframework.boot:spring-boot-starter-test")
        testImplementation("com.nhaarman:mockito-kotlin:1.6.0")
    }

    tasks {
        withType<KotlinCompile> {
            kotlinOptions {
                dependsOn("processResources")
                freeCompilerArgs = listOf("-Xjsr305=strict")
                jvmTarget = "17"
            }
        }

        test {
            useJUnitPlatform()
            jvmArgs("-Djava.security.egd=file:/dev/./urandom", "-Dspring.config.location=classpath:/config/")
        }

        ktlint {
            verbose.set(true)
            disabledRules.addAll("import-ordering")
        }
    }

    allOpen {
        annotation("javax.persistence.Entity")
        annotation("javax.persistence.MappedSuperclass")
        annotation("javax.persistence.Embeddable")
    }

    noArg {
        annotation("javax.persistence.Entity")
        annotation("javax.persistence.MappedSuperclass")
        annotation("javax.persistence.Embeddable")
    }
}



