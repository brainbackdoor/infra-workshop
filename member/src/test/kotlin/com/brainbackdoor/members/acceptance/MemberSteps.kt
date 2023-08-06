package com.brainbackdoor.members.acceptance

import com.brainbackdoor.auth.domain.LoginMember
import com.brainbackdoor.members.ui.MemberCreateRequest
import com.brainbackdoor.members.ui.MemberResponse
import io.restassured.RestAssured
import io.restassured.response.ExtractableResponse
import io.restassured.response.Response
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType

fun 회원_생성을_요청(
    email: String,
    password: String
): ExtractableResponse<Response> {
    val params = MemberCreateRequest(
        email,
        password,
        consentByMember = true,
        consentByPrivacy = true
    )

    return RestAssured.given().log().all()
        .contentType(MediaType.APPLICATION_JSON_VALUE).body(params)
        .post("/api/members")
        .then().log().all()
        .extract()
}

fun 회원_생성됨(response: ExtractableResponse<Response>) {
    assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value())
}

fun 회원_정보_조회_요청(response: ExtractableResponse<Response>): ExtractableResponse<Response> {
    val uri = response.header("Location")

    return RestAssured.given().log().all()
        .accept(MediaType.APPLICATION_JSON_VALUE)
        .get(uri).then().log().all()
        .extract()
}

fun 회원_정보_조회됨(response: ExtractableResponse<Response>, email: String) {
    assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value())
    val memberResponse = response.`as`(LoginMember::class.java)
    assertThat(memberResponse.id).isNotNull()
    assertThat(memberResponse.email).isEqualTo(email)
}