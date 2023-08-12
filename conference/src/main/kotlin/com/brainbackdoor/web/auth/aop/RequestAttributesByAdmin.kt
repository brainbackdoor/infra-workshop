package com.brainbackdoor.web.auth.aop

import ch.qos.logback.core.CoreConstants.EMPTY_STRING
import com.brainbackdoor.web.auth.LoginMember
import com.brainbackdoor.web.auth.LoginMemberService
import com.brainbackdoor.web.auth.Role
import com.brainbackdoor.web.auth.aop.AdminAuth.Companion.ACCESS_TOKEN
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import exception.HasNotPermissionException
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
class RequestAttributesByAdmin(
    objectMapper: ObjectMapper = jacksonObjectMapper(),
    private val loginMemberService: LoginMemberService
) : HttpServletRequestAttributes(objectMapper) {

    @Before("@annotation(com.brainbackdoor.web.auth.aop.AdminAuth)")
    fun execute(joinPoint: JoinPoint) {
        validAdmin(joinPoint)
        logger.info("[Admin Auth PASSED] memberId={}", loginMember().id)
    }

    private fun validAdmin(joinPoint: JoinPoint): String {
        val annotation = annotation(joinPoint)
        return if (annotation.validAdmin) {
            isAdmin().id
        } else {
            EMPTY_STRING
        }
    }

    private fun annotation(joinPoint: JoinPoint): AdminAuth =
        (joinPoint.signature as MethodSignature).method.getAnnotation(AdminAuth::class.java)

    private fun loginMember() = loginMemberService.findMe(token())

    private fun isAdmin(): LoginMember {
        val loginMember = loginMember()
        check(loginMember.isAdmin()) {
            throw HasNotPermissionException("${loginMember.email} 계정은 관리자가 아닙니다.")
        }
        return loginMember
    }

    private fun token(): String {
        val token = get().getHeader(ACCESS_TOKEN)
        check(!token.isNullOrEmpty()) {
            throw HasNotPermissionException("토큰이 존재하지 않아, 인증을 할 수 없습니다.")
        }

        return token
    }
}