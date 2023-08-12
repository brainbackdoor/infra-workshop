package com.brainbackdoor.web.config

import com.brainbackdoor.auth.AuthorizationArgumentResolver
import com.brainbackdoor.support.LocalDateConverter
import com.brainbackdoor.support.LocalDateTimeConverter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.format.FormatterRegistry
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebSecurityConfig {
    @Bean
    @Throws(java.lang.Exception::class)
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf().disable()
            .formLogin().disable()
            .authorizeHttpRequests()
            .anyRequest().permitAll()
            .and().logout().logoutSuccessUrl("/")
        return http.build()
    }
}

@Configuration
class WebMvcConfig(
    private val loginUserArgumentResolver: AuthorizationArgumentResolver,
) : WebMvcConfigurer {

    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
        resolvers.add(loginUserArgumentResolver)
    }

    override fun addFormatters(registry: FormatterRegistry) {
        registry.addConverter(LocalDateConverter())
        registry.addConverter(LocalDateTimeConverter())
    }
}