package com.brainbackdoor.auth

import com.brainbackdoor.auth.HttpServletRequest.Companion.get
import jakarta.servlet.http.HttpServletRequest
import javax.naming.AuthenticationException

class HttpHeader {
    companion object {
        const val AUTHORIZATION = "Authorization"
        const val BEARER_TYPE = "Bearer"

        fun auth(request: HttpServletRequest, header: String): String {
            val token = get().getHeader(header)
            check(!token.isNullOrEmpty()) {
                throw AuthenticationException("인증과 관련된 HTTP 헤더가 존재하지 않습니다.")
            }

            return token(token, request)
        }

        fun token(token: String, request: HttpServletRequest) = if (isBearerType(token)) {
            request.setAttribute(HttpHeader::class.java.simpleName + ".ACCESS_TOKEN_TYPE", BEARER_TYPE)
            bearerType(token)
        } else {
            token
        }

        fun hasAuthorization(request: HttpServletRequest): Boolean = request.getHeaders(AUTHORIZATION).hasMoreElements()

        private fun bearerType(value: String): String {
            val authHeaderValue = value.substring(BEARER_TYPE.length).trim { it <= ' ' }
            return if (authHeaderValue.contains(","))
                authHeaderValue.split(",").toTypedArray()[0]
            else authHeaderValue
        }

        private fun isBearerType(value: String): Boolean {
            return value.lowercase().startsWith(BEARER_TYPE.lowercase())
        }
    }
}