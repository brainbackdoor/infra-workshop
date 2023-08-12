package com.brainbackdoor.web.auth.aop

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletRequest
import mu.KotlinLogging
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import org.springframework.web.servlet.HandlerMapping
import java.util.*
import javax.naming.AuthenticationException

private val logger = KotlinLogging.logger { }
open class HttpServletRequestAttributes(
    private val objectMapper: ObjectMapper = jacksonObjectMapper()
) {
    fun findParameterValue(parameterValue: String): Optional<String> {
        val values = get().parameterMap[parameterValue]
        return if (values.isNullOrEmpty()) {
            Optional.empty()
        } else {
            Optional.of(values[0])
        }
    }

    fun findPathVariable(pathVariable: String): Optional<String> {
        val pathVariables = get().getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE) as Map<*, *>
        return if (pathVariables.isEmpty()) {
            Optional.empty()
        } else {
            Optional.ofNullable(pathVariables[pathVariable]).map { it.toString() }
        }
    }

    fun findRequestBody(joinPoint: JoinPoint, requestBody: String): Optional<String> {
        try {
            val args = joinPoint.args
            val method = (joinPoint.signature as MethodSignature).method
            val parameterAnnotations = method.parameterAnnotations
            for (i in args.indices) {
                if (containsRequestBody(parameterAnnotations, i)) {
                    return Optional.of(json(body(args[i]), requestBody))
                }
            }
            return Optional.empty()
        } catch (e: Exception) {
            logger.warn("[Validation FAILED] message={}, cause={}", e.message, e.cause)
            throw AuthenticationException("Validation 검증에 실패했습니다.")
        }
    }

    private fun containsRequestBody(parameterAnnotations: Array<Array<Annotation>>, i: Int) =
        parameterAnnotations[i].filterIsInstance<RequestBody>().isNotEmpty()

    private fun json(body: String, requestBody: String): String {
        val jsonNode = objectMapper.readTree(body).get(requestBody)
        return refine("$jsonNode")
    }

    private fun body(arguments: Any): String {
        return if (arguments is String) {
            "$arguments"
        } else {
            objectMapper.writeValueAsString(arguments)
        }
    }

    private fun refine(text: String): String = text.replace(Regex("[\\[\\]\"]"), "")

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