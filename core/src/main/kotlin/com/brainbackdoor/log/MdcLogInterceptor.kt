package com.brainbackdoor.log

import jakarta.annotation.PostConstruct
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import mu.KotlinLogging
import org.slf4j.MDC
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.ResourceBundleMessageSource
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.HandlerInterceptor
import java.util.*

private val logger = KotlinLogging.logger {}

class MdcLogInterceptor : HandlerInterceptor {
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        val uuid = UUID.randomUUID().toString()
        val userAgent = request.getHeader("User-Agent")

        MDC.put(REQUEST_UUID, uuid)
        MDC.put(REQUEST_USER_AGENT, userAgent)
        MDC.put(REQUEST_IP, request.remoteAddr)

        if (handler is HandlerMethod) {
            MDC.put(REQUEST_HANDLER, "${handler.beanType.simpleName}.${handler.method.name}")
        }

        val sb = StringBuilder()
        sb.append("<== [MDC Request Log]\n")
        sb.append(message("log_mdc_uuid", uuid))
        sb.append(message("log_mdc_user_agent", userAgent))
        sb.append(message("log_mdc_request_ip", request.remoteAddr))
        logger.info(sb.toString())

        return true
    }

    private fun message(code: String, argument: String): String =
        MESSAGE_SOURCE.getMessage(code, arrayOf(argument), Locale.KOREA)

    override fun afterCompletion(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any,
        ex: Exception?,
    ) {
        val sb = StringBuilder()
        sb.append("<== [MDC Response Log]\n")
        sb.append(message("log_mdc_response_status", response.status.toString()))
        logger.info(sb.toString())

        MDC.clear()
    }

    companion object {
        var MESSAGE_SOURCE = ResourceBundleMessageSource()
        const val REQUEST_UUID = "REQUEST_UUID"
        const val REQUEST_USER_AGENT = "REQUEST_USER_AGENT"
        const val REQUEST_IP = "REQUEST_IP"
        const val REQUEST_HANDLER = "HANDLER"
    }
}

@Configuration
class MessageSourceConfig(
    private val messageSource: ResourceBundleMessageSource = ResourceBundleMessageSource()
) {
    @PostConstruct
    fun initialize() {
        messageSource.addBasenames("log")
        messageSource.setDefaultEncoding("UTF-8")
        messageSource.setDefaultLocale(Locale.KOREA)
        MdcLogInterceptor.MESSAGE_SOURCE = messageSource
    }
}
