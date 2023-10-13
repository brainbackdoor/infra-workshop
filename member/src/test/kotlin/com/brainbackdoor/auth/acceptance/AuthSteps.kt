package com.brainbackdoor.auth.acceptance

import com.brainbackdoor.auth.web.LoginRequest
import com.brainbackdoor.auth.web.TokenResponse
import io.restassured.RestAssured
import io.restassured.response.ExtractableResponse
import io.restassured.response.Response
import org.springframework.http.MediaType

fun 로그인_요청(email: String, password: String) : ExtractableResponse<Response> {
    val params = LoginRequest(email, password)

    return RestAssured.given().log().all()
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .body(params)
        .post("/api/auth/login")
        .then().log().all().extract()
}

fun 본인_정보_조회_요청(response: ExtractableResponse<Response>): ExtractableResponse<Response> {
    return 본인_정보_조회_요청(response.`as`(TokenResponse::class.java))
}

fun 본인_정보_조회_요청(tokenResponse: TokenResponse): ExtractableResponse<Response> {
    return RestAssured.given().log().all().auth()
        .oauth2(tokenResponse.accessToken)
        .accept(MediaType.APPLICATION_JSON_VALUE)
        .get("/api/auth/me")
        .then().log().all().extract()
}

fun 로그아웃_요청(response: ExtractableResponse<Response>): ExtractableResponse<Response> {
    return 로그아웃_요청(response.`as`(TokenResponse::class.java))
}

fun 로그아웃_요청(tokenResponse: TokenResponse) : ExtractableResponse<Response> {
    return RestAssured.given().log().all().auth()
        .oauth2(tokenResponse.accessToken)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .post("/api/auth/logout")
        .then().log().all().extract()
}