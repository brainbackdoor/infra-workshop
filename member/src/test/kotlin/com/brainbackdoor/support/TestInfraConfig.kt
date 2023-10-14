package com.brainbackdoor.support

import com.brainbackdoor.notifications.Notification
import mu.KotlinLogging
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

private val logger = KotlinLogging.logger { }

@Configuration
class TestInfraConfig {
    @Bean
    @Profile("test")
    fun mailTestProfile(): Notification {
        return FakeMail()
    }
}

class FakeMail : Notification {
    override fun send(recipient: String, subject: String, contents: String) {
        logger.debug("send mail: to - {}, subject - {}", recipient, subject)
        logger.debug("contents: {}", contents)
    }

    override fun send(recipient: List<String>, subject: String, contents: String) {
        logger.debug("send mail: to - {}, subject - {}", recipient, subject)
        logger.debug("contents: {}", contents)
    }
}
