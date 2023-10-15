package com.brainbackdoor.analysis

import com.brainbackdoor.web.HttpHeader.Companion.AUTHORIZATION
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.*

@FeignClient(
    name = "analysis-client",
    url = "\${external.analysis.host}"
)
interface AnalysisClient {
    @GetMapping("/api/analysis/coding-as-hobby")
    fun findCodingAsHobby(@RequestHeader(AUTHORIZATION) token: String): List<CodingAsHobbyResponse>

    @GetMapping("/api/analysis/lectures-by-participants")
    fun findLecturesByParticipants(@RequestHeader(AUTHORIZATION) token: String): List<LecturesByParticipantResponse>

    @GetMapping("/api/analysis/lecture-name")
    fun findLectureNameOrderSurveyId(
        @RequestHeader(AUTHORIZATION) token: String,
        @RequestParam id: Long
    ): List<LectureNameOrderSurveyIdResponse>

    @GetMapping("/api/analysis/member-by-infra-workshop")
    fun findMemberByInfraWorkshop(@RequestHeader(AUTHORIZATION) token: String): List<MemberByInfraWorkshopResponse>
}