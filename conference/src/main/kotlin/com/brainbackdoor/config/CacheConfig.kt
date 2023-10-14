package com.brainbackdoor.config

import com.github.benmanes.caffeine.cache.Caffeine
import org.springframework.beans.factory.annotation.Value
import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cache.caffeine.CaffeineCache
import org.springframework.cache.support.SimpleCacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.concurrent.TimeUnit

@Configuration
@EnableCaching
class CacheConfig {
    @Value("\${cache.refresh-time-seconds}")
    private val refreshTime: String = ""

    @Bean("mbtiCache")
    fun cache(): CacheManager {
        val cacheManager = SimpleCacheManager()
        cacheManager.setCaches(listOf(cacheConfig()))
        return cacheManager
    }

    private fun cacheConfig(): CaffeineCache =
        CaffeineCache(
            "mbtiCache",
            Caffeine.newBuilder()
                .recordStats()
                .expireAfterWrite(refreshTime.toLong(), TimeUnit.SECONDS)
                .build()
        )
}