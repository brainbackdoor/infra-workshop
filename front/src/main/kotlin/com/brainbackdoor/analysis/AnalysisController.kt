package com.brainbackdoor.analysis

import com.brainbackdoor.auth.AdminAuth
import com.brainbackdoor.web.HttpHeader
import com.brainbackdoor.web.HttpServletRequest
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Tag(name = "AnalysisController", description = "분석시스템 API")
@RequestMapping("/api/analysis")
@RestController
class AnalysisController(
    private val analysisClient: AnalysisClient,
    private val analysisMemberService: AnalysisMemberService
) {
    @Value("\${auth.secret-key}")
    private val secretKey: String = ""

    @Operation(summary = "취미가 코딩인 사람 비율 조회")
    @GetMapping("/coding-as-hobby")
    @AdminAuth
    fun findCodingAsHobby(): ResponseEntity<List<CodingAsHobbyResponse>> =
        ResponseEntity.ok(analysisClient.findCodingAsHobby(secretKey))

    @Operation(summary = "참여자별로 수강 리스트 조회")
    @GetMapping("/lectures-by-participants")
    @AdminAuth
    fun findLecturesByParticipants(): ResponseEntity<List<LecturesByParticipantResponse>> =
        ResponseEntity.ok(analysisClient.findLecturesByParticipants(secretKey))

    @Operation(summary = "참여자 메일별로 수강 리스트 조회")
    @GetMapping("/lectures-by-participants-email")
    fun findLecturesByParticipantsEmail(): ResponseEntity<List<LecturesByParticipantEmailResponse>> =
        ResponseEntity.ok(analysisMemberService.findLecturesByParticipantsEmail(token()))

    @Operation(summary = "프로그래밍이 취미인 학생 혹은 주니어(0-2년)들이 수강한 강의 이름을 survey.id 기준으로 정렬하여 조회")
    @GetMapping("/lecture-name")
    @AdminAuth
    fun findLectureNameOrderSurveyId(
        @RequestParam id: Long = 0L,
    ): ResponseEntity<List<LectureNameOrderSurveyIdResponse>> =
        ResponseEntity.ok(analysisClient.findLectureNameOrderSurveyId(secretKey, id))

    @Operation(summary = "인프라공방을 수강한 30대 환자들을 OS별로 집계")
    @GetMapping("/member-by-infra-workshop")
    @AdminAuth
    fun findMemberByInfraWorkshop(): ResponseEntity<List<MemberByInfraWorkshopResponse>> =
        ResponseEntity.ok(analysisClient.findMemberByInfraWorkshop(secretKey))

    private fun token() = HttpHeader.auth(HttpServletRequest.get(), HttpHeader.AUTHORIZATION)
}