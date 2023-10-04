package com.brainbackdoor.analysis.ui

import com.brainbackdoor.analysis.application.AnalysisService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/api/analysis")
@RestController
class AnalysisController(
    private val analysisService: AnalysisService,
) {

    @GetMapping("/coding-as-hobby")
    fun findCodingAsHobby(): ResponseEntity<List<CodingAsHobbyResponse>> =
        ResponseEntity.ok(analysisService.findCodingAsHobby())

    @GetMapping("/lectures-by-participants")
    fun findLecturesByParticipants(): ResponseEntity<List<LecturesByParticipantsResponse>> =
        ResponseEntity.ok(analysisService.findLecturesByParticipants())

    @GetMapping("/lecture-name")
    fun findLectureNameOrderSurveyId(
        @RequestParam id: Long = 0L,
    ): ResponseEntity<List<LectureNameOrderSurveyIdResponse>> =
        ResponseEntity.ok(analysisService.findLectureNameOrderSurveyId(id))

    @GetMapping("member-by-infra-workshop")
    fun findMemberByInfraWorkshop(): ResponseEntity<List<MemberByInfraWorkshopResponse>> =
        ResponseEntity.ok(analysisService.findMemberByInfraWorkshop())
}