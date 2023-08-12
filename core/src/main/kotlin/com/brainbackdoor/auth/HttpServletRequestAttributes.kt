package com.brainbackdoor.auth

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import mu.KotlinLogging
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.servlet.HandlerMapping
import java.util.*
import javax.naming.AuthenticationException

private val logger = KotlinLogging.logger {}
open class HttpServletRequestAttributes(
    private val objectMapper: ObjectMapper = jacksonObjectMapper()
) {
    fun findParameterValue(parameterValue: String): Optional<String> {
        val values = HttpServletRequest.get().parameterMap[parameterValue]
        return if (values.isNullOrEmpty()) {
            Optional.empty()
        } else {
            Optional.of(values[0])
        }
    }

    fun findPathVariable(pathVariable: String): Optional<String> {
        val pathVariables = HttpServletRequest.get().getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE) as Map<*, *>
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
}