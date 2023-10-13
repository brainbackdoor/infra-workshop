package com.brainbackdoor.conferences.web

import com.brainbackdoor.conferences.domain.Conference
import com.brainbackdoor.conferences.domain.RecruitmentStatus
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
    val contents: String = "",

    @Schema(title = "추첨 인원")
    var lotteryBoundary: Int = 0,
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

    @Schema(title = "추첨 인원")
    var lotteryBoundary: Int,

    @Schema(title = "컨퍼런스 참가 비용")
    val fee: Int,

    @Schema(title = "컨퍼런스 내용")
    val contents: String = "",

    @Schema(title = "컨퍼런스 모집 상태")
    val status: RecruitmentStatus,
) {
    constructor(conference: Conference) : this(
        conference.id,
        conference.recruitment.recruitmentPeriod.periodStart.toString(),
        conference.recruitment.recruitmentPeriod.periodEnd.toString(),
        conference.schedule.toString(),
        conference.area,
        conference.recruitment.applicants.limited,
        conference.recruitment.applicants.lotteryBoundary,
        conference.fee,
        conference.contents,
        conference.recruitment.status
    )
}

@Schema(description = "전체 컨퍼런스 응답")
data class ConferenceAllResponse(

    @Schema(title = "컨퍼런스 식별자")
    val id: String,

    @Schema(title = "모집 신청 일정")
    val recruitmentStartDate: String,

    @Schema(title = "모집 마감 일정")
    val recruitmentEndDate: String,

    @Schema(title = "컨퍼런스 일정")
    val schedule: String,

    @Schema(title = "컨퍼런스 장소")
    val area: String,

    @Schema(title = "모집 최대 인원")
    val limited: Int,

    @Schema(title = "추첨 인원")
    var lotteryBoundary: Int,

    @Schema(title = "컨퍼런스 참가 비용")
    val fee: Int,

    @Schema(title = "컨퍼런스 내용")
    val contents: String = "",

    @Schema(title = "컨퍼런스 모집 상태")
    val status: RecruitmentStatus,

    @Schema(title = "참가자")
    val applicants: List<String>,
) {
    constructor(conference: Conference) : this(
        conference.id,
        conference.recruitment.recruitmentPeriod.periodStart.toString(),
        conference.recruitment.recruitmentPeriod.periodEnd.toString(),
        conference.schedule.toString(),
        conference.area,
        conference.recruitment.applicants.limited,
        conference.recruitment.applicants.lotteryBoundary,
        conference.fee,
        conference.contents,
        conference.recruitment.status,
        conference.recruitment.applicants.applicants.map { it.memberId }
    )
}

@Schema(description = "컨퍼런스 모집 상태 요청")
data class ConferenceStatusRequest(
    @Schema(title = "컨퍼런스 모집 상태")
    val status: String,
)

@Schema(description = "컨퍼런스 모집 상태 응답")
data class ConferenceStatusResponse(
    @Schema(title = "컨퍼런스 식별자")
    val id: String,

    @Schema(title = "컨퍼런스 모집 상태")
    val status: String,
) {
    constructor(conference: Conference) : this(
        conference.id,
        conference.status().name
    )
}

@Schema(description = "컨퍼런스 참가 요청")
data class ConferenceJoinRequest(
    @Schema(title = "회원 아이디")
    val memberId: String,
)