package com.brainbackdoor.analysis

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "취미가 코딩인 사람 비율 응답")
data class CodingAsHobbyResponse(
    val hobby: String,
    val count: Double,
)

@Schema(description = "참여자별로 수강 리스트 응답")
data class LecturesByParticipantsResponse(
    val id: Long,
    val name: String,
)

@Schema(description = "프로그래밍이 취미인 학생 혹은 주니어(0-2년)들이 수강한 강의 이름을 survey.id 기준으로 정렬 응답")
data class LectureNameOrderSurveyIdResponse(
    val id: Long,
    val name: String,
)

@Schema(description = "인프라공방을 수강한 30대 환자들을 OS별 응답")
data class MemberByInfraWorkshopResponse(
    val os: String,
    val count: Double,
)