package com.brainbackdoor.auth.application

import com.brainbackdoor.auth.domain.LoginToken
import com.brainbackdoor.auth.domain.LoginTokenRepository
import com.brainbackdoor.auth.ui.LoginRequest
import com.brainbackdoor.auth.ui.TokenResponse
import com.brainbackdoor.members.application.MemberService
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
@Transactional
class AuthService(
    private val loginTokenRepository: LoginTokenRepository,
    private val memberService: MemberService,
    private val tokenService: TokenService
) {
    fun login(request: LoginRequest): TokenResponse {
        val member = memberService.checkPassword(request.email, request.password)
        val token = tokenService.create(request.email)
        val persist = saveIfNotExist(member.id, token)
        return TokenResponse(persist.token, member.mail())
    }

    private fun saveIfNotExist(memberId: String, token: String): LoginToken {
        return findTopMember(memberId)
            ?: loginTokenRepository.save(LoginToken(memberId, token))
    }

    private fun findTopMember(memberId: String): LoginToken? {
        val members = loginTokenRepository.findMember(memberId)
        return members.firstOrNull() { it.isToday() }
    }
}