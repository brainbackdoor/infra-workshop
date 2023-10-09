package com.brainbackdoor.config

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class SwaggerConfig {

    @Bean
    fun openAPI(): OpenAPI {
        val info = Info()
            .title("Infra-workshop API")
            .description("<p>인프라공방 실습을 위한 API 입니다.</p>")

        return OpenAPI().components(Components()).info(info)
    }
}