package com.brainbackdoor.web

import com.brainbackdoor.auth.HttpHeader
import com.brainbackdoor.auth.HttpHeader.Companion.AUTHORIZATION
import com.brainbackdoor.auth.HttpServletRequest.Companion.get
import org.springframework.core.MethodParameter
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer

@Component
class AuthorizationArgumentResolver: HandlerMethodArgumentResolver {
    override fun supportsParameter(parameter: MethodParameter): Boolean =
        parameter.hasParameterAnnotation(AuthorizationToken::class.java)

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?
    ): Any? {
        return HttpHeader.auth(get(), AUTHORIZATION)
    }
}
