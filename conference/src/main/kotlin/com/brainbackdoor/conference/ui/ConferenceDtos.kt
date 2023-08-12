package com.brainbackdoor.conference.ui

import com.brainbackdoor.conference.domain.Conference
import com.brainbackdoor.conference.domain.RecruitmentStatus

data class ConferenceRequest(
    val recruitmentStartDate: String,
    val recruitmentEndDate: String,
    val conferenceSchedule: String,
    val area: String,
    val limited: Int,
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
        conference.recruitment.fee,
        conference.contents,
        conference.recruitment.status
    )
}

data class ConferenceStatusRequest(val status: String)
data class ConferenceStatusResponse(val id: String, val status: String) {
    constructor(conference: Conference) : this(
        conference.id,
        conference.status().name
    )
}