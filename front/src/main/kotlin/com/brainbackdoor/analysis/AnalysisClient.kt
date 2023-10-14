package com.brainbackdoor.analysis

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.*

@FeignClient(
    name = "analysis-client",
    url = "\${external.analysis.host}"
)
interface AnalysisClient {
    @GetMapping("/api/analysis/coding-as-hobby")
    fun findCodingAsHobby(): List<CodingAsHobbyResponse>

    @GetMapping("/api/analysis/lectures-by-participants")
    fun findLecturesByParticipants(): List<LecturesByParticipantResponse>

    @GetMapping("/api/analysis/lecture-name")
    fun findLectureNameOrderSurveyId(@RequestParam id: Long): List<LectureNameOrderSurveyIdResponse>

    @GetMapping("/api/analysis/member-by-infra-workshop")
    fun findMemberByInfraWorkshop(): List<MemberByInfraWorkshopResponse>
}