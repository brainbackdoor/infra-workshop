package com.brainbackdoor.members.acceptance

import com.brainbackdoor.members.web.MemberCreateRequest
import com.brainbackdoor.support.AcceptanceTest
import com.brainbackdoor.support.InitialTestData.Companion.ADMIN_PASSWORD
import io.restassured.RestAssured
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType

class MemberAcceptanceTest : AcceptanceTest() {
    @Test
    fun `회원을 생성한다`() {
        val mail = "sample@gmail.com"
        val createResponse = 회원_생성을_요청(mail, ADMIN_PASSWORD)
        회원_생성됨(createResponse)

        val findResponse = 회원_정보_조회_요청(createResponse)
        회원_정보_조회됨(findResponse, mail)
    }

    @Test
    fun `관리자만 회원생성이 가능하다`() {
        val params = MemberCreateRequest(
            "sample@gmail.com",
            ADMIN_PASSWORD,
            consentByMember = true,
            consentByPrivacy = true
        )

        val response = RestAssured.given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(params)
            .post("/api/members")
            .then().log().all().extract()

        assertThat(response.statusCode()).isEqualTo(HttpStatus.FORBIDDEN.value())
    }
}