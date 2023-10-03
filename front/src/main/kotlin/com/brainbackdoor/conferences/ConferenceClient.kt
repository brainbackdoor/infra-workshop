package com.brainbackdoor.conferences

import com.brainbackdoor.web.HttpHeader.Companion.AUTHORIZATION
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.*

@FeignClient(
    name = "conference-client",
    url = "\${external.conference.host}"
)
interface ConferenceClient {
    @PostMapping("/api/conferences")
    fun create(
        @RequestHeader(AUTHORIZATION) token: String,
        @RequestBody request: ConferenceRequest,
    ): String

    @GetMapping("/api/conferences")
    fun findBy(
        @RequestParam schedule: String?,
        @RequestParam area: String?,
        @RequestParam status: String?,
    ): List<ConferenceResponse>

    @GetMapping("/api/conferences/all")
    fun findAll(@RequestHeader(AUTHORIZATION) token: String): List<ConferenceAllResponse>

    @GetMapping("/api/conferences/{id}")
    fun findById(@PathVariable id: String): ConferenceResponse

    @PutMapping("/api/conferences/{id}")
    fun update(
        @RequestHeader(AUTHORIZATION) token: String,
        @PathVariable id: String,
        @RequestBody request: ConferenceRequest,
    ): ConferenceResponse


    @GetMapping("/api/conferences/{id}/status")
    fun status(@PathVariable id: String): ConferenceStatusResponse


    @PutMapping("/api/conferences/{id}/status")
    fun updateStatus(
        @RequestHeader(AUTHORIZATION) token: String,
        @PathVariable id: String,
        @RequestBody request: ConferenceStatusRequest,
    ): ConferenceStatusResponse

    @DeleteMapping("/api/conferences/{id}")
    fun delete(
        @RequestHeader(AUTHORIZATION) token: String,
        @PathVariable id: String
    )

    @PostMapping("/api/conferences/{id}/join")
    fun recruit(
        @RequestParam memberId: String,
        @PathVariable id: String
    ): ConferenceResponse

}