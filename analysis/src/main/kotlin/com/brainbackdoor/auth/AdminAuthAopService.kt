package com.brainbackdoor.auth

import com.brainbackdoor.exception.HasNotPermissionException
import com.brainbackdoor.web.HttpHeader
import com.brainbackdoor.web.HttpServletRequest.Companion.get
import com.brainbackdoor.web.HttpServletRequestAttributes
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import mu.KotlinLogging
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component

private val logger = KotlinLogging.logger {}

@Aspect
@Order(1)
@Component
class AdminAuthAopServiceForAnalysis(
    objectMapper: ObjectMapper = jacksonObjectMapper(),
) : HttpServletRequestAttributes(objectMapper) {
    @Value("\${auth.secret-key}")
    private val secretKey: String? = null

    @Before("@annotation(com.brainbackdoor.auth.AdminAuth)")
    fun execute(joinPoint: JoinPoint) {
        validAdmin(joinPoint)
    }

    private fun validAdmin(joinPoint: JoinPoint) {
        val annotation = annotation(joinPoint)

        if (annotation.validAdmin) {
            check(token() == secretKey) {
                throw HasNotPermissionException("${get().requestURI}을 사용할 권한이 없습니다.")
            }
        }
    }

    private fun annotation(joinPoint: JoinPoint): AdminAuth =
        (joinPoint.signature as MethodSignature).method.getAnnotation(AdminAuth::class.java)

    fun token(): String {
        val token = HttpHeader.auth(get(), HttpHeader.AUTHORIZATION)
        check(!token.isNullOrEmpty()) {
            throw HasNotPermissionException("토큰이 존재하지 않아, 인증을 할 수 없습니다.")
        }

        return token
    }
}