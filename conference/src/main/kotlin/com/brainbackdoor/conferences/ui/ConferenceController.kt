package com.brainbackdoor.conferences.ui

import com.brainbackdoor.auth.AdminAuth
import com.brainbackdoor.conferences.application.ConferenceService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI

@RequestMapping("/api/conferences")
@RestController
class ConferenceController(
    private val conferenceService: ConferenceService,
) {
    @PostMapping
    @AdminAuth
    fun create(
        @RequestBody request: ConferenceRequest,
    ): ResponseEntity<String> {
        val id = conferenceService.create(request).id
        return ResponseEntity.ok(id)
    }

    @GetMapping
    fun find(
        @RequestParam schedule: String? = "",
        @RequestParam area: String? = "",
        @RequestParam status: String? = "",
    ): ResponseEntity<List<ConferenceResponse>> {
        return ResponseEntity.ok(conferenceService.findBy(schedule, area, status))
    }

    @GetMapping("/all")
    @AdminAuth
    fun findAll(): ResponseEntity<List<ConferenceAllResponse>> =
        ResponseEntity.ok(conferenceService.findAll())

    @GetMapping("/{id}")
    fun findById(@PathVariable id: String): ResponseEntity<ConferenceResponse> =
        ResponseEntity.ok(conferenceService.findById(id))

    @PutMapping("/{id}")
    @AdminAuth
    fun update(
        @PathVariable id: String,
        @RequestBody request: ConferenceRequest,
    ): ResponseEntity<ConferenceResponse> =
        ResponseEntity.ok(conferenceService.update(id, request))

    @GetMapping("/{id}/status")
    fun status(@PathVariable id: String): ResponseEntity<ConferenceStatusResponse> =
        ResponseEntity.ok(conferenceService.status(id))

    @PutMapping("/{id}/status")
    @AdminAuth
    fun updateStatus(
        @PathVariable id: String,
        @RequestBody request: ConferenceStatusRequest,
    ): ResponseEntity<ConferenceStatusResponse> =
        ResponseEntity.ok(conferenceService.updateStatus(id, request))

    @DeleteMapping("/{id}")
    @AdminAuth
    fun delete(@PathVariable id: String): ResponseEntity.HeadersBuilder<*> {
        conferenceService.delete(id)
        return ResponseEntity.noContent()
    }

    @PostMapping("/{id}/join")
    fun recruit(
        @RequestParam memberId: String,
        @PathVariable id: String
    ): ResponseEntity<ConferenceResponse> =
        ResponseEntity.ok(conferenceService.recruit(memberId, id))
}