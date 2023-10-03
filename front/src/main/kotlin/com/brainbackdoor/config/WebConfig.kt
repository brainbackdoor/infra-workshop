package com.brainbackdoor.config

import com.brainbackdoor.auth.AuthorizationArgumentResolver
import com.brainbackdoor.support.LocalDateConverter
import com.brainbackdoor.support.LocalDateTimeConverter
import org.springframework.context.annotation.Configuration
import org.springframework.format.FormatterRegistry
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebMvcConfig(
    private val authorizationArgumentResolver: AuthorizationArgumentResolver,
) : WebMvcConfigurer {

    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
        resolvers.add(authorizationArgumentResolver)
    }

    override fun addFormatters(registry: FormatterRegistry) {
        registry.addConverter(LocalDateConverter())
        registry.addConverter(LocalDateTimeConverter())
    }
}