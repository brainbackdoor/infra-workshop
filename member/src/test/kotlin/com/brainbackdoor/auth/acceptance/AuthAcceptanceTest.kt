package com.brainbackdoor.auth.acceptance

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
}