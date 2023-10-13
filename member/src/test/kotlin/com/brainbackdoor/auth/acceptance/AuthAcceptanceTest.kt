package com.brainbackdoor.auth.acceptance

import com.brainbackdoor.auth.web.TokenResponse
import com.brainbackdoor.support.AcceptanceTest
import com.brainbackdoor.members.acceptance.회원_정보_조회됨
import com.brainbackdoor.support.InitialTestData.Companion.ADMIN_EMAIL
import com.brainbackdoor.support.InitialTestData.Companion.ADMIN_PASSWORD
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus

class AuthAcceptanceTest: AcceptanceTest() {
    @Test
    fun `Bearer Auth 로그인 성공`() {
        val response = 로그인_요청(ADMIN_EMAIL, ADMIN_PASSWORD)
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value())

        val myData = 본인_정보_조회_요청(response)
        회원_정보_조회됨(myData, ADMIN_EMAIL)
    }

    @Test
    fun `Bearer Auth 로그인 실패`() {
        val response = 로그인_요청(ADMIN_EMAIL, "other$ADMIN_PASSWORD")

        assertThat(response.statusCode()).isEqualTo(HttpStatus.FORBIDDEN.value())
    }

    @Test
    fun `Bearer Auth 유효하지 않은 토큰`() {
        val token = TokenResponse("invalid-token")
        val response = 본인_정보_조회_요청(token)

        assertThat(response.statusCode()).isEqualTo(HttpStatus.FORBIDDEN.value())
    }

    @Test
    fun `로그인 응답값에 이메일 등의 정보를 반환한다`() {
        val response = 로그인_요청(ADMIN_EMAIL, ADMIN_PASSWORD)

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value())
        val tokenResponse = response.`as`(TokenResponse::class.java)
        assertThat(tokenResponse.email).isEqualTo(ADMIN_EMAIL)
    }

    @Test
    fun `로그아웃을 한다`() {
        val response = 로그인_요청(ADMIN_EMAIL, ADMIN_PASSWORD)
        val myData = 본인_정보_조회_요청(response)
        회원_정보_조회됨(myData, ADMIN_EMAIL)

        로그아웃_요청(response)

        val fail = 본인_정보_조회_요청(response)
        assertThat(fail.statusCode()).isEqualTo(HttpStatus.FORBIDDEN.value())
    }
}