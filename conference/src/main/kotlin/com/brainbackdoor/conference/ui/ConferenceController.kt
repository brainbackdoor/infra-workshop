package com.brainbackdoor.conference.ui

import com.brainbackdoor.conference.application.ConferenceService
import com.brainbackdoor.web.auth.aop.AdminAuth
import com.brainbackdoor.web.auth.external.LoginMemberClient
import com.brainbackdoor.web.auth.external.MemberResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/api/conference")
@RestController
class ConferenceController(
    private val conferenceService: ConferenceService,
) {
    @GetMapping
    @AdminAuth
    fun test() {

    }
}