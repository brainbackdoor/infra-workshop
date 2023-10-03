package com.brainbackdoor.web

import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletRequest
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import java.util.*

class HttpServletRequest {
    companion object {
        fun get(): HttpServletRequest =
            (RequestContextHolder.currentRequestAttributes() as ServletRequestAttributes).request

        fun remoteIpAddress(): String {
            val request = get()
            val ip: String = request
                .getHeader("X-FORWARDED-FOR")
                ?: request.remoteAddr
            return ip.replace(Regex("\\s*,.*"), "")
        }

        fun userAgent(): String = get().getHeader("User-Agent")

        fun cookie(
            request: HttpServletRequest,
            value: String
        ): Optional<Cookie> {
            return try {
                Arrays.stream(request.cookies)
                    .filter { value == it.name }
                    .findFirst()
            } catch (e: Exception) {
                Optional.empty()
            }
        }
    }
}