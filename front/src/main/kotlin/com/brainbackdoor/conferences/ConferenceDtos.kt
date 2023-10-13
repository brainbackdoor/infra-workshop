package com.brainbackdoor.conferences

import io.swagger.v3.oas.annotations.media.Schema


@Schema(description = "컨퍼런스 요청")
data class ConferenceRequest(

    @Schema(title = "모집 신청 일정")
    val recruitmentStartDate: String,

    @Schema(title = "모집 마감 일정")
    val recruitmentEndDate: String,

    @Schema(title = "컨퍼런스 일정")
    val conferenceSchedule: String,

    @Schema(title = "컨퍼런스 장소")
    val area: String,

    @Schema(title = "모집 최대 인원")
    val limited: Int,

    @Schema(title = "컨퍼런스 참가 비용")
    val fee: Int,

    @Schema(title = "컨퍼런스 내용")
    val contents: String = ""
)


@Schema(description = "컨퍼런스 응답")
data class ConferenceResponse(

    @Schema(title = "컨퍼런스 식별자")
    val id: String,

    @Schema(title = "모집 신청 일정")
    val recruitmentStartDate: String,

    @Schema(title = "모집 마감 일정")
    val recruitmentEndDate: String,

    @Schema(title = "컨퍼런스 일정")
    val conferenceSchedule: String,

    @Schema(title = "컨퍼런스 장소")
    val area: String,

    @Schema(title = "모집 최대 인원")
    val limited: Int,

    @Schema(title = "컨퍼런스 참가 비용")
    val fee: Int,

    @Schema(title = "컨퍼런스 내용")
    val contents: String = "",

    @Schema(title = "컨퍼런스 모집 상태")
    val status: String
)

@Schema(description = "전체 컨퍼런스 응답")
data class ConferenceAllResponse(
    @Schema(title = "컨퍼런스 식별자")
    val id: String,

    @Schema(title = "모집 신청 일정")
    val recruitmentStartDate: String,

    @Schema(title = "모집 마감 일정")
    val recruitmentEndDate: String,

    @Schema(title = "컨퍼런스 일정")
    val conferenceSchedule: String,

    @Schema(title = "컨퍼런스 장소")
    val area: String,

    @Schema(title = "모집 최대 인원")
    val limited: Int,

    @Schema(title = "컨퍼런스 참가 비용")
    val fee: Int,

    @Schema(title = "컨퍼런스 내용")
    val contents: String = "",

    @Schema(title = "컨퍼런스 모집 상태")
    val status: String,

    @Schema(title = "참가자")
    val applicants: List<String>
)

@Schema(description = "컨퍼런스 모집 상태 요청")
data class ConferenceStatusRequest(
    @Schema(title = "컨퍼런스 모집 상태")
    val status: String
)

@Schema(description = "컨퍼런스 모집 상태 응답")
data class ConferenceStatusResponse(
    @Schema(title = "컨퍼런스 식별자")
    val id: String,

    @Schema(title = "컨퍼런스 모집 상태")
    val status: String
)

@Schema(description = "컨퍼런스 참가 요청")
data class ConferenceJoinRequest(
    @Schema(title = "회원 아이디")
    val memberId: String,
)