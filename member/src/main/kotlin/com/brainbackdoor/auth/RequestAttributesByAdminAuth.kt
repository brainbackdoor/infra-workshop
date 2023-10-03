package com.brainbackdoor.auth

import com.brainbackdoor.web.HttpHeader.Companion.AUTHORIZATION
import com.brainbackdoor.web.HttpServletRequest.Companion.get
import com.brainbackdoor.auth.application.AuthService
import com.brainbackdoor.exception.HasNotPermissionException
import com.brainbackdoor.web.HttpHeader
import com.brainbackdoor.web.HttpServletRequestAttributes
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import mu.KotlinLogging
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component

private val logger = KotlinLogging.logger {}

@Aspect
@Order(1)
@Component
class RequestAttributesByAdminAuth(
    objectMapper: ObjectMapper = jacksonObjectMapper(),
    private val authService: AuthService,
) : HttpServletRequestAttributes(objectMapper) {

    @Before("@annotation(com.brainbackdoor.auth.AdminAuth)")
    fun execute(joinPoint: JoinPoint) {
        validAdmin(joinPoint)
        logger.info("[Admin Auth PASSED] memberId={}", loginMember().id)
    }

    private fun validAdmin(joinPoint: JoinPoint) {
        val annotation = annotation(joinPoint)
        if (annotation.validAdmin) {
            val loginMember = loginMember()
            check(loginMember.isAdmin()) {
                throw HasNotPermissionException("${loginMember.email} 계정은 관리자가 아닙니다.")
            }
        }
    }

    private fun annotation(joinPoint: JoinPoint): AdminAuth =
        (joinPoint.signature as MethodSignature).method.getAnnotation(AdminAuth::class.java)

    private fun loginMember() = authService.findLoginMemberBy(token())

    private fun token(): String = HttpHeader.auth(get(), AUTHORIZATION)

}