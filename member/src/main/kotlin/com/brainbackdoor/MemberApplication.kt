package com.brainbackdoor

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
class MemberApplication

fun main(args: Array<String>) {
    runApplication<MemberApplication>(*args)
}

@ConfigurationPropertiesScan
@Configuration
class ComponentConfiguration

@EnableJpaAuditing
@Configuration
class JpaConfiguration