package com.brainbackdoor.log

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.MDC
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.HandlerInterceptor
import java.lang.Exception
import java.util.*

class MdcLogInterceptor : HandlerInterceptor {
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        val uuid = UUID.randomUUID().toString()

        MDC.put(REQUEST_UUID, uuid)
        MDC.put(REQUEST_USER_AGENT, request.getHeader("User-Agent"))
        MDC.put(REQUEST_IP, request.remoteAddr)

        if (handler is HandlerMethod) {
            MDC.put(REQUEST_HANDLER, "${handler.beanType.simpleName}.${handler.method.name}")
        }

        return true
    }

    override fun afterCompletion(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any,
        ex: Exception?,
    ) {
        MDC.clear()
    }

    companion object {
        const val REQUEST_UUID = "REQUEST_UUID"
        const val REQUEST_USER_AGENT = "REQUEST_USER_AGENT"
        const val REQUEST_IP = "REQUEST_IP"
        const val REQUEST_HANDLER = "HANDLER"
    }
}