package com.brainbackdoor

import org.apache.ibatis.annotations.Mapper
import org.mybatis.spring.annotation.MapperScan
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

@MapperScan(basePackages = ["com.brainbackdoor"], annotationClass = Mapper::class)
@ConfigurationPropertiesScan
@Configuration
class ComponentConfiguration

@EnableJpaAuditing
@Configuration
class JpaConfiguration