package com.brainbackdoor.notifications

import com.brainbackdoor.support.delay
import jakarta.mail.internet.MimeMessage
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.mail.javamail.MimeMessagePreparator
import org.springframework.scheduling.annotation.Async

open class Mail(
    private val javaMailSender: JavaMailSender,
    private val mailProperties: MailProperties
) : Notification {
    // @Async
    override fun send(recipient: String, subject: String, contents: String) {
        send(listOf(recipient), subject, contents)
    }

    // @Async
    override fun send(recipient: List<String>, subject: String, contents: String) {
        check(recipient.isNotEmpty()) {
            throw IllegalArgumentException("수신자가 없어 메일을 발송할 수 없습니다.")
        }

        delay(1000L)
        // javaMailSender.send(info(recipient, subject, contents))
    }

    private fun info(
        recipient: List<String>,
        subject: String,
        contents: String
    ) = MimeMessagePreparator {
        val mimeMessageHelper = setTo(it, recipient)
        mimeMessageHelper.setFrom(mailProperties.username, SENDER_NAME)
        mimeMessageHelper.setSubject(subject)
        mimeMessageHelper.setText(contents, true)
    }

    private fun setTo(mimeMessage: MimeMessage, to: List<String>): MimeMessageHelper {
        val mimeMessageHelper = MimeMessageHelper(mimeMessage)
        if (to.size == 1) {
            mimeMessageHelper.setTo(to.first())
            return mimeMessageHelper
        }
        mimeMessageHelper.setBcc(to.toTypedArray())
        return mimeMessageHelper
    }

    companion object {
        const val SENDER_NAME = "인프라공방"
    }
}

@ConfigurationProperties("spring.mail")
data class MailProperties(
    val host: String,
    val port: String,
    val username: String,
    val password: String
)
