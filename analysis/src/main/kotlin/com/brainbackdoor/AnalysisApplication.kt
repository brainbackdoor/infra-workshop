package com.brainbackdoor

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@EnableFeignClients
@SpringBootApplication
class AnalysisApplication

fun main(args: Array<String>) {
    runApplication<AnalysisApplication>(*args)
}

@ConfigurationPropertiesScan
@Configuration
class ComponentConfiguration

@EnableJpaAuditing
@Configuration
class JpaConfiguration