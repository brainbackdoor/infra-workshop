package com.brainbackdoor.auth

import com.brainbackdoor.auth.application.AuthService
import com.brainbackdoor.auth.domain.AuthenticationPrincipal
import com.brainbackdoor.auth.domain.LoginMember.Companion.guestLoginMember
import jakarta.servlet.http.HttpServletRequest
import org.springframework.core.MethodParameter
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer
import javax.naming.AuthenticationException

@Component
class LoginUserHandlerArgumentResolver(
    private val authService: AuthService
) : HandlerMethodArgumentResolver {
    override fun supportsParameter(parameter: MethodParameter): Boolean =
        parameter.hasParameterAnnotation(AuthenticationPrincipal::class.java)


    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?
    ): Any? {
        val request = webRequest.getNativeRequest(HttpServletRequest::class.java)!!
        if (HttpHeader.hasAuthorization(request)) {
            val token: String = HttpHeader.auth(request)
            return authService.findLoginMemberBy(token)
        }
        return guestLoginMember()
    }
}

class HttpHeader {
    companion object {
        private const val AUTHORIZATION = "Authorization"
        private const val BEARER_TYPE = "Bearer"

        fun auth(request: HttpServletRequest): String {
            val headers = request.getHeaders(AUTHORIZATION)
            while (headers.hasMoreElements()) {
                val value = headers.nextElement()
                if (isBearerType(value)) {
                    request.setAttribute(HttpHeader::class.java.simpleName + ".ACCESS_TOKEN_TYPE", BEARER_TYPE)
                    return bearerType(value)
                }
            }
            throw AuthenticationException("인증과 관련된 Http Header 가 존재하지 않습니다.")
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