package com.brainbackdoor.conferences

import com.brainbackdoor.auth.AdminAuth
import com.brainbackdoor.auth.LoginMemberClient
import com.brainbackdoor.web.AuthorizationToken
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI

@RequestMapping("/api/conferences")
@RestController
class ConferenceController(
    private val conferenceClient: ConferenceClient,
    private val loginMemberClient: LoginMemberClient
) {
    @PostMapping
    @AdminAuth
    fun create(
        @AuthorizationToken token: String,
        @RequestBody request: ConferenceRequest,
    ): ResponseEntity<String> {
        val id = conferenceClient.create(token, request)
        return ResponseEntity.created(URI.create("/api/conferences/$id")).build()
    }

    @GetMapping
    fun find(
        @RequestParam schedule: String? = "",
        @RequestParam area: String? = "",
        @RequestParam status: String? = "",
    ): ResponseEntity<List<ConferenceResponse>> {
        return ResponseEntity.ok(conferenceClient.findBy(schedule, area, status))
    }

    @GetMapping("/all")
    @AdminAuth
    fun findAll(
        @AuthorizationToken token: String,
    ): ResponseEntity<List<ConferenceAllResponse>> =
        ResponseEntity.ok(conferenceClient.findAll(token))

    @GetMapping("/{id}")
    fun findById(@PathVariable id: String): ResponseEntity<ConferenceResponse> =
        ResponseEntity.ok(conferenceClient.findById(id))

    @PutMapping("/{id}")
    @AdminAuth
    fun update(
        @AuthorizationToken token: String,
        @PathVariable id: String,
        @RequestBody request: ConferenceRequest,
    ): ResponseEntity<ConferenceResponse> =
        ResponseEntity.ok(conferenceClient.update(token, id, request))

    @GetMapping("/{id}/status")
    fun status(@PathVariable id: String): ResponseEntity<ConferenceStatusResponse> =
        ResponseEntity.ok(conferenceClient.status(id))

    @PutMapping("/{id}/status")
    @AdminAuth
    fun updateStatus(
        @AuthorizationToken token: String,
        @PathVariable id: String,
        @RequestBody request: ConferenceStatusRequest,
    ): ResponseEntity<ConferenceStatusResponse> =
        ResponseEntity.ok(conferenceClient.updateStatus(token, id, request))

    @DeleteMapping("/{id}")
    @AdminAuth
    fun delete(
        @AuthorizationToken token: String,
        @PathVariable id: String
    ): ResponseEntity.HeadersBuilder<*> {
        conferenceClient.delete(token, id)
        return ResponseEntity.noContent()
    }

    @PostMapping("/{id}/join")
    fun recruit(
        @AuthorizationToken token: String,
        @PathVariable id: String
    ): ResponseEntity<ConferenceResponse> {
        val memberId = loginMemberClient.findMe(token).id
        return ResponseEntity.ok(conferenceClient.recruit(memberId, id))
    }

}