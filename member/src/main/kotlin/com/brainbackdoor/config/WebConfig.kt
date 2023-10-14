package com.brainbackdoor.config

import com.brainbackdoor.auth.AuthArgumentResolverService
import com.brainbackdoor.log.MdcLogInterceptor
import com.brainbackdoor.notifications.Mail
import com.brainbackdoor.notifications.MailProperties
import com.brainbackdoor.notifications.Notification
import com.brainbackdoor.support.LocalDateConverter
import com.brainbackdoor.support.LocalDateTimeConverter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.format.FormatterRegistry
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
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
    private val loginUserArgumentResolver: AuthArgumentResolverService,
) : WebMvcConfigurer {

    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
        resolvers.add(loginUserArgumentResolver)
    }

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(MdcLogInterceptor())
    }

    override fun addFormatters(registry: FormatterRegistry) {
        registry.addConverter(LocalDateConverter())
        registry.addConverter(LocalDateTimeConverter())
    }
}

@Bean
@Profile("local", "prod")
fun nsMailSenderNotTestProfile(
    javaMailSender: JavaMailSender,
    mailProperties: MailProperties
): Notification {
    return Mail(javaMailSender, mailProperties)
}