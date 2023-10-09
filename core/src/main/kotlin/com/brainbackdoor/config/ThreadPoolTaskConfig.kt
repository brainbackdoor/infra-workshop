package com.brainbackdoor.config

import org.slf4j.MDC
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.task.TaskDecorator
import org.springframework.scheduling.annotation.AsyncConfigurer
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor

@Configuration
@EnableAsync
class ThreadPoolTaskConfig() : AsyncConfigurer {
    @Bean
    fun threadPoolTaskExecutor() {
        val executor = ThreadPoolTaskExecutor()
        executor.setTaskDecorator(MDCCopyTaskDecorator())
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