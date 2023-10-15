package com.brainbackdoor.infra

import org.slf4j.MDC
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Configuration
import org.springframework.core.task.TaskDecorator
import org.springframework.scheduling.annotation.Async
import org.springframework.scheduling.annotation.AsyncConfigurer
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import org.springframework.stereotype.Service
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Executor

@Service
class AsyncService(
    private val threadPoolTaskExecutor: Executor
) {
    @Async
    fun <T> supplyAsync(supplier: () -> T): CompletableFuture<T> =
        CompletableFuture.supplyAsync(supplier, threadPoolTaskExecutor)

    @Async
    fun runAsync(runnable: Runnable): CompletableFuture<Void> =
        CompletableFuture.runAsync(runnable, threadPoolTaskExecutor)
}

@Configuration
@EntityScan("com.brainbackdoor")
@EnableAsync(proxyTargetClass = true)
class AsyncConfig() : AsyncConfigurer {
    override fun getAsyncExecutor(): Executor {
        val executor = ThreadPoolTaskExecutor()
        executor.setTaskDecorator(MDCCopyTaskDecorator())
        executor.initialize()
        return executor
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