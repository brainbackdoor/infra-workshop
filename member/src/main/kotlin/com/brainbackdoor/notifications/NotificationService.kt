package com.brainbackdoor.notifications

import mu.KotlinLogging
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

private val logger = KotlinLogging.logger { }
@Service
class NotificationService(
    private val notification: Notification
) {

    // @EventListener
    fun send(event : SendMailEvent) {
        logger.debug { "SendMailEvent happened!! $event" }

        notification.send(event.recipient, event.subject, event.contents)
    }
}

data class SendMailEvent(
    val recipient: String,
    val subject: String,
    val contents: String
)