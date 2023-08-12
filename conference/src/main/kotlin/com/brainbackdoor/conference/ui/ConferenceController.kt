package com.brainbackdoor.conference.ui

import auth.aop.AdminAuth
import com.brainbackdoor.conference.application.ConferenceService
import org.springframework.web.bind.annotation.GetMapping
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