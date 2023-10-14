package com.brainbackdoor.infra

import mu.KotlinLogging
import org.springframework.beans.factory.BeanFactory
import org.springframework.beans.factory.BeanFactoryAware
import org.springframework.beans.factory.support.DefaultSingletonBeanRegistry
import org.springframework.context.SmartLifecycle
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import org.springframework.stereotype.Component
import java.util.concurrent.CompletableFuture

private val logger = KotlinLogging.logger { }

@Component
class WorkerGracefulShutdownService(
    private val threadPoolTaskExecutors: Map<String, ThreadPoolTaskExecutor>,
    private var isRunning: Boolean,
    private var beanFactory: DefaultSingletonBeanRegistry,
) : SmartLifecycle, BeanFactoryAware {

    override fun start() {
        this.isRunning = true
    }

    /**
     * 1. 현재 실행 중인 모든 Thread가 종료될 때까지 대기 후 ThreadPool destroy
     * 2. 이 후 Spring life cycle을 따름
     */
    override fun stop() {
        logger.info { "ThreadPoolTaskExecutor Shutdown Start" }
        try {
            this.threadPoolTaskExecutors
                .keys.stream()
                .forEach { CompletableFuture.runAsync { beanFactory.destroySingleton(it) } }
        } catch (e: Exception) {
            logger.error { "[GracefulShutdown] threadPoolTaskExecutors Exception ${e.message}" }
        }
        logger.info { "ThreadPoolTaskExecutor Shutdown Finish" }
        this.isRunning = false
    }

    override fun stop(callback: Runnable) {
        stop()
        callback.run()
    }

    /**
     * 제일 마지막 start()
     * 제일 먼저 stop()
     */
    override fun getPhase(): Int = Int.MAX_VALUE

    override fun isAutoStartup(): Boolean = true

    override fun isRunning(): Boolean = this.isRunning

    override fun setBeanFactory(beanFactory: BeanFactory) {
        this.beanFactory = beanFactory as DefaultSingletonBeanRegistry
    }
}