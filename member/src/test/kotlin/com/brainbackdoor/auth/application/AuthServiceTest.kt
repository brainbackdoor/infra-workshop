package com.brainbackdoor.auth.application

import com.brainbackdoor.auth.domain.LoginMember
import com.brainbackdoor.auth.web.LoginRequest
import com.brainbackdoor.support.AcceptanceTest
import com.brainbackdoor.support.InitialTestData.Companion.ADMIN_EMAIL
import com.brainbackdoor.support.InitialTestData.Companion.ADMIN_PASSWORD
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import javax.naming.AuthenticationException

class AuthServiceTest : AcceptanceTest() {
    @Autowired
    lateinit var authService: AuthService

    @Test
    fun `로그아웃을 한다`() {
        val token = authService.login(LoginRequest(ADMIN_EMAIL, ADMIN_PASSWORD)).accessToken
        val loginMember = authService.findLoginMemberBy(token)

        authService.logout(loginMember)
        assertThrows<AuthenticationException> { authService.findLoginMemberBy(token) }
    }

    @Test
    fun `로그인한 경우에만 로그아웃이 가능하다`() {
        assertThrows<AuthenticationException> { authService.logout(LoginMember("", ADMIN_EMAIL, listOf())) }
    }
}