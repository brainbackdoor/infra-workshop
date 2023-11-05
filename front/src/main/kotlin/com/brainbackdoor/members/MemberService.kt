package com.brainbackdoor.members

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class MemberService(
    private val memberClient: MemberClient
) {
    @Value("\${auth.secret-key}")
    private val secretKey: String = ""

    // @CircuitBreaker(name = "members-circuit-breaker", fallbackMethod = "fallbackMembers")
    fun findAll(): List<MemberResponse> = memberClient.findAll(secretKey)

    private fun fallbackMembers(e: Throwable): List<MemberResponse> = listOf()
}