package com.brainbackdoor.conferences


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
    val status: String
)

data class ConferenceAllResponse(
    val id: String,
    val recruitmentStartDate: String,
    val recruitmentEndDate: String,
    val schedule: String,
    val area: String,
    val limited: Int,
    val fee: Int,
    val contents: String = "",
    val status: String,
    val applicants: List<String>
)

data class ConferenceStatusRequest(val status: String)
data class ConferenceStatusResponse(val id: String, val status: String)