package com.brainbackdoor.conferences.ui

import com.brainbackdoor.conferences.domain.Applicants
import com.brainbackdoor.conferences.domain.Conference
import com.brainbackdoor.conferences.domain.RecruitmentStatus

data class ConferenceRequest(
    val recruitmentStartDate: String,
    val recruitmentEndDate: String,
    val conferenceSchedule: String,
    val area: String,
    val limited: Int,
    var lotteryBoundary: Int,
    val fee: Int,
    val contents: String = ""
)

data class ConferenceResponse(
    val id: String,
    val recruitmentStartDate: String,
    val recruitmentEndDate: String,
    val conferenceSchedule: String,
    val area: String,
    val limited: Int,
    var lotteryBoundary: Int,
    val fee: Int,
    val contents: String = "",
    val status: RecruitmentStatus
) {
    constructor(conference: Conference) : this(
        conference.id,
        conference.recruitment.recruitmentPeriod.periodStart.toString(),
        conference.recruitment.recruitmentPeriod.periodEnd.toString(),
        conference.schedule.toString(),
        conference.area,
        conference.recruitment.applicants.limited,
        conference.recruitment.applicants.lotteryBoundary,
        conference.recruitment.fee,
        conference.contents,
        conference.recruitment.status
    )
}

data class ConferenceAllResponse(
    val id: String,
    val recruitmentStartDate: String,
    val recruitmentEndDate: String,
    val schedule: String,
    val area: String,
    val limited: Int,
    var lotteryBoundary: Int,
    val fee: Int,
    val contents: String = "",
    val status: RecruitmentStatus,
    val applicants: List<String>
) {
    constructor(conference: Conference) : this(
        conference.id,
        conference.recruitment.recruitmentPeriod.periodStart.toString(),
        conference.recruitment.recruitmentPeriod.periodEnd.toString(),
        conference.schedule.toString(),
        conference.area,
        conference.recruitment.applicants.limited,
        conference.recruitment.applicants.lotteryBoundary,
        conference.recruitment.fee,
        conference.contents,
        conference.recruitment.status,
        conference.recruitment.applicants.applicants.map { it.memberId }
    )
}

data class ConferenceStatusRequest(val status: String)
data class ConferenceStatusResponse(val id: String, val status: String) {
    constructor(conference: Conference) : this(
        conference.id,
        conference.status().name
    )
}