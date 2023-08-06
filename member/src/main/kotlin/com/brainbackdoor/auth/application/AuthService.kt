package com.brainbackdoor.auth.application

import com.brainbackdoor.auth.domain.LoginMember
import com.brainbackdoor.auth.domain.LoginToken
import com.brainbackdoor.auth.domain.LoginTokenRepository
import com.brainbackdoor.auth.ui.LoginRequest
import com.brainbackdoor.auth.ui.TokenResponse
import com.brainbackdoor.members.application.MemberService
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import javax.naming.AuthenticationException

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

    fun findLoginMemberBy(token: String): LoginMember {
        validToken(token)

        val email = tokenService.parsePayload(token)
        val member = memberService.findByEmail(email)
        return LoginMember(member)
    }

    fun logout(loginMember: LoginMember) {
        val loginToken = findTopMember(loginMember.id!!)
            ?: throw AuthenticationException("로그인 사용자가 아닙니다.")

        loginToken.logout()
    }

    private fun saveIfNotExist(memberId: String, token: String): LoginToken {
        return findTopMember(memberId)
            ?: loginTokenRepository.save(LoginToken(memberId, token))
    }

    private fun findTopMember(memberId: String): LoginToken? {
        val members = loginTokenRepository.findMember(memberId)
        return members.firstOrNull() { it.isToday() }
    }

    private fun validToken(token: String) {
        check(tokenService.validate(token)) {
            throw AuthenticationException("토큰의 유효기간이 지났습니다.")
        }

        val token = loginTokenRepository.findByTokenAndDisabledFalse(token)
            ?: throw AuthenticationException("토큰이 유효하지 않습니다.")
        check(token.isToday()) { throw AuthenticationException("토큰의 유효기간이 지났습니다.") }
    }
}