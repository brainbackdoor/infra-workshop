package com.brainbackdoor.conference.acceptance

import com.brainbackdoor.conferences.web.ConferenceRequest
import io.restassured.RestAssured
import io.restassured.response.ExtractableResponse
import io.restassured.response.Response
import org.assertj.core.api.Assertions
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType

fun 컨퍼런스_생성_요청(
    token: String,
    param: ConferenceRequest
): ExtractableResponse<Response> {
    return RestAssured.given().log().all()
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .auth().oauth2(token)
        .body(param)
        .post("/api/conferences")
        .then().log().all()
        .extract()
}

fun 컨퍼런스_생성됨(response: ExtractableResponse<Response>) {
    Assertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value())
}