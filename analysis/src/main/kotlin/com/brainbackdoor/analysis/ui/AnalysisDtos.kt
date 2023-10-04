package com.brainbackdoor.analysis.ui

data class CodingAsHobbyResponse(
    val hobby: String,
    val count: Double,
)

data class LecturesByParticipantsResponse(
    val id: Long,
    val name: String,
)

data class LectureNameOrderSurveyIdResponse(
    val id: Long,
    val name: String,
)

data class MemberByInfraWorkshopResponse(
    val os: String,
    val count: Double,
)