package com.brainbackdoor.conference.acceptance

import com.brainbackdoor.conferences.web.ConferenceJoinRequest
import com.brainbackdoor.conferences.web.ConferenceRequest
import com.brainbackdoor.conferences.web.ConferenceStatusRequest
import io.restassured.RestAssured
import io.restassured.response.ExtractableResponse
import io.restassured.response.Response
import org.springframework.http.MediaType

fun 컨퍼런스_생성_요청(
    token: String,
    param: ConferenceRequest,
): ExtractableResponse<Response> {
    return RestAssured.given().log().all()
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .auth().oauth2(token)
        .body(param)
        .post("/api/conferences")
        .then().log().all()
        .extract()
}

fun 컨퍼런스_전체_리스트_조회_요청(
    token: String,
): ExtractableResponse<Response> {
    return RestAssured.given().log().all()
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .auth().oauth2(token)
        .get("/api/conferences/all")
        .then().log().all()
        .extract()
}

fun 컨퍼런스_리스트_조회_요청(
    token: String,
    schedule: String = "",
    area: String = "",
    status: String = "",
): ExtractableResponse<Response> {
    return RestAssured.given().log().all()
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .auth().oauth2(token)
        .param("schedule", schedule)
        .param("area", area)
        .param("status", status)
        .get("/api/conferences")
        .then().log().all()
        .extract()
}

fun 특정_컨퍼런스_조회_요청(
    token: String,
    id: String,
): ExtractableResponse<Response> {
    return RestAssured.given().log().all()
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .auth().oauth2(token)
        .get("/api/conferences/${id}")
        .then().log().all()
        .extract()
}

fun 컨퍼런스_모집_상태_확인_요청(
    token: String,
    id: String,
): ExtractableResponse<Response> {
    return RestAssured.given().log().all()
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .auth().oauth2(token)
        .get("/api/conferences/$id/status")
        .then().log().all()
        .extract()
}

fun 컨퍼런스_모집_상태_변경_요청(
    token: String,
    id: String,
    params: ConferenceStatusRequest,
): ExtractableResponse<Response> {
    return RestAssured.given().log().all()
        .auth().oauth2(token)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .body(params)
        .put("/api/conferences/$id/status")
        .then().log().all()
        .extract()
}

fun 컨퍼런스_참가_요청(
    token: String,
    id: String,
    params: ConferenceJoinRequest,
): ExtractableResponse<Response> {
    return RestAssured.given().log().all()
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .auth().oauth2(token)
        .body(params)
        .post("/api/conferences/${id}/join")
        .then().log().all()
        .extract()
}

fun 컨퍼런스_수정_요청(
    token: String,
    id: String,
    params: ConferenceRequest,
): ExtractableResponse<Response> {
    return RestAssured.given().log().all()
        .auth().oauth2(token)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .body(params)
        .put("/api/conferences/$id")
        .then().log().all()
        .extract()
}

fun 컨퍼런스_삭제_요청(
    token: String,
    id: String,
): ExtractableResponse<Response> {
    return RestAssured.given().log().all()
        .auth().oauth2(token)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .delete("/api/conferences/$id")
        .then().log().all()
        .extract()
}