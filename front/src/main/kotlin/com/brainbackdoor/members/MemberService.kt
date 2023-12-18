package com.brainbackdoor.members

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker
import org.springframework.stereotype.Service

@Service
class MemberService(
    private val memberClient: MemberClient
) {
    // @CircuitBreaker(name = "members-circuit-breaker", fallbackMethod = "fallbackMembers")
    fun findAll(loginToken: String): List<MemberResponse> = memberClient.findAll(loginToken)

    private fun fallbackMembers(e: Throwable): List<MemberResponse> = listOf()
}