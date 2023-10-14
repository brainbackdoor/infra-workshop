package com.brainbackdoor.conferences

import com.brainbackdoor.auth.AdminAuth
import com.brainbackdoor.auth.Auth
import com.brainbackdoor.auth.LoginMemberClient
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI

@Tag(name = "ConferenceController", description = "BFF 컨퍼런스 API")
@RequestMapping("/api/conferences")
@RestController
class ConferenceController(
    private val conferenceClient: ConferenceClient,
    private val loginMemberClient: LoginMemberClient,
) {
    @Value("\${auth.secret-key}")
    private val secretKey: String = ""

    @Operation(summary = "컨퍼런스 생성")
    @PostMapping
    @AdminAuth
    fun create(
        @RequestBody request: ConferenceRequest,
    ): ResponseEntity<String> {
        val id = conferenceClient.create(secretKey, request)
        return ResponseEntity.created(URI.create("/api/conferences/$id")).build()
    }

    @Operation(summary = "컨퍼런스 조회")
    @GetMapping
    fun find(
        @Parameter(name = "schedule", description = "컨퍼런스 일정") @RequestParam schedule: String? = "",
        @Parameter(name = "area", description = "컨퍼런스 장소") @RequestParam area: String? = "",
        @Parameter(name = "status", description = "컨퍼런스 모집 상태") @RequestParam status: String? = "",
    ): ResponseEntity<List<ConferenceResponse>> {
        return ResponseEntity.ok(conferenceClient.findBy(secretKey, schedule, area, status))
    }

    @Operation(summary = "전체 컨퍼런스 조회")
    @GetMapping("/all")
    @AdminAuth
    fun findAll(
    ): ResponseEntity<List<ConferenceAllResponse>> =
        ResponseEntity.ok(conferenceClient.findAll(secretKey))

    @Operation(summary = "특정 컨퍼런스 조회")
    @GetMapping("/{id}")
    fun findById(
        @Parameter(name = "id", description = "컨퍼런스 식별자") @PathVariable id: String,
    ): ResponseEntity<ConferenceResponse> =
        ResponseEntity.ok(conferenceClient.findById(secretKey, id))

    @Operation(summary = "특정 컨퍼런스 수정")
    @PutMapping("/{id}")
    @AdminAuth
    fun update(
        @Parameter(name = "id", description = "컨퍼런스 식별자") @PathVariable id: String,
        @RequestBody request: ConferenceRequest,
    ): ResponseEntity<ConferenceResponse> =
        ResponseEntity.ok(conferenceClient.update(secretKey, id, request))

    @Operation(summary = "특정 컨퍼런스 모집 상태 조회")
    @GetMapping("/{id}/status")
    fun status(
        @Parameter(name = "id", description = "컨퍼런스 식별자") @PathVariable id: String,
    ): ResponseEntity<ConferenceStatusResponse> =
        ResponseEntity.ok(conferenceClient.status(secretKey, id))

    @Operation(summary = "특정 컨퍼런스 모집 상태 수정")
    @PutMapping("/{id}/status")
    @AdminAuth
    fun updateStatus(
        @Parameter(name = "id", description = "컨퍼런스 식별자") @PathVariable id: String,
        @RequestBody request: ConferenceStatusRequest,
    ): ResponseEntity<ConferenceStatusResponse> =
        ResponseEntity.ok(conferenceClient.updateStatus(secretKey, id, request))

    @Operation(summary = "특정 컨퍼런스 삭제")
    @DeleteMapping("/{id}")
    @AdminAuth
    fun delete(
        @Parameter(name = "id", description = "컨퍼런스 식별자") @PathVariable id: String,
    ): ResponseEntity.HeadersBuilder<*> {
        conferenceClient.delete(secretKey, id)
        return ResponseEntity.noContent()
    }

    @Operation(summary = "특정 컨퍼런스에 참가")
    @PostMapping("/{id}/join")
    fun recruit(
        @Auth token: String,
        @Parameter(name = "id", description = "컨퍼런스 식별자") @PathVariable id: String,
    ): ResponseEntity<ConferenceResponse> {
        val request = ConferenceJoinRequest(loginMemberClient.findMe(token).id)
        return ResponseEntity.ok(conferenceClient.recruit(secretKey, request, id))
    }

}