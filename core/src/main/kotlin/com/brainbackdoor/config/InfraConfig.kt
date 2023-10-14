package com.brainbackdoor.config

import com.brainbackdoor.notifications.Mail
import com.brainbackdoor.notifications.MailProperties
import com.brainbackdoor.notifications.Notification
import org.slf4j.MDC
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.core.task.TaskDecorator
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.scheduling.annotation.AsyncConfigurer
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import java.util.concurrent.Executor

@Configuration
@EntityScan("com.brainbackdoor")
@EnableAsync(proxyTargetClass = true)
class InfraConfig() : AsyncConfigurer {

    override fun getAsyncExecutor(): Executor {
        val executor = ThreadPoolTaskExecutor()
        executor.setTaskDecorator(MDCCopyTaskDecorator())
        executor.initialize()
        return executor
    }

    @Bean
    @Profile("local", "prod")
    fun nsMailSenderNotTestProfile(
        javaMailSender: JavaMailSender,
        mailProperties: MailProperties
    ): Notification {
        return Mail(javaMailSender, mailProperties)
    }
}

class MDCCopyTaskDecorator : TaskDecorator {
    override fun decorate(runnable: Runnable): Runnable {
        val context = MDC.getCopyOfContextMap()
        return Runnable {
            try {
                if (context != null) {
                    MDC.setContextMap(context)
                }
                runnable.run()
            } finally {
                MDC.clear()
            }
        }
    }
}