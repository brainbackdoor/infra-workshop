package com.brainbackdoor.conferences.web

import com.brainbackdoor.auth.AdminAuth
import com.brainbackdoor.conferences.application.ConferenceService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI

@Tag(name = "ConferenceController", description = "컨퍼런스시스템 API")
@RequestMapping("/api/conferences")
@RestController
class ConferenceController(
    private val conferenceService: ConferenceService,
) {
    @Operation(summary = "컨퍼런스 생성")
    @PostMapping
    @AdminAuth
    fun create(
        @RequestBody request: ConferenceRequest,
    ): ResponseEntity<String> {
        val id = conferenceService.create(request).id
        return ResponseEntity.created(URI.create("/api/conferences/$id")).build()
    }

    @Operation(summary = "컨퍼런스 조회")
    @GetMapping
    fun find(
        @Parameter(name = "schedule", description = "컨퍼런스 일정") @RequestParam schedule: String? = "",
        @Parameter(name = "area", description = "컨퍼런스 장소") @RequestParam area: String? = "",
        @Parameter(name = "status", description = "컨퍼런스 모집 상태") @RequestParam status: String? = "",
    ): ResponseEntity<List<ConferenceResponse>> {
        return ResponseEntity.ok(conferenceService.findBy(schedule, area, status))
    }

    @Operation(summary = "전체 컨퍼런스 조회")
    @GetMapping("/all")
    @AdminAuth
    fun findAll(): ResponseEntity<List<ConferenceAllResponse>> =
        ResponseEntity.ok(conferenceService.findAll())


    @Operation(summary = "특정 컨퍼런스 조회")
    @GetMapping("/{id}")
    fun findById(
        @Parameter(name = "id", description = "컨퍼런스 식별자") @PathVariable id: String,
    ): ResponseEntity<ConferenceResponse> =
        ResponseEntity.ok(conferenceService.findById(id))

    @Operation(summary = "특정 컨퍼런스 수정")
    @PutMapping("/{id}")
    @AdminAuth
    fun update(
        @Parameter(name = "id", description = "컨퍼런스 식별자") @PathVariable id: String,
        @RequestBody request: ConferenceRequest,
    ): ResponseEntity<ConferenceResponse> =
        ResponseEntity.ok(conferenceService.update(id, request))

    @Operation(summary = "특정 컨퍼런스 모집 상태 조회")
    @GetMapping("/{id}/status")
    fun status(
        @Parameter(name = "id", description = "컨퍼런스 식별자") @PathVariable id: String,
    ): ResponseEntity<ConferenceStatusResponse> =
        ResponseEntity.ok(conferenceService.status(id))

    @Operation(summary = "특정 컨퍼런스 모집 상태 수정")
    @PutMapping("/{id}/status")
    @AdminAuth
    fun updateStatus(
        @Parameter(name = "id", description = "컨퍼런스 식별자") @PathVariable id: String,
        @RequestBody request: ConferenceStatusRequest,
    ): ResponseEntity<ConferenceStatusResponse> =
        ResponseEntity.ok(conferenceService.updateStatus(id, request))

    @Operation(summary = "특정 컨퍼런스 삭제")
    @DeleteMapping("/{id}")
    @AdminAuth
    fun delete(
        @Parameter(name = "id", description = "컨퍼런스 식별자") @PathVariable id: String,
    ): ResponseEntity.HeadersBuilder<*> {
        conferenceService.delete(id)
        return ResponseEntity.noContent()
    }

    @Operation(summary = "특정 컨퍼런스에 참가")
    @PostMapping("/{id}/join")
    fun recruit(
        @Parameter(name = "memberId", description = "회원 아이디") @RequestParam memberId: String,
        @Parameter(name = "id", description = "컨퍼런스 식별자") @PathVariable id: String,
    ): ResponseEntity<ConferenceResponse> =
        ResponseEntity.ok(conferenceService.recruit(memberId, id))
}