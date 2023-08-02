package com.brainbackdoor.members.ui

import com.brainbackdoor.members.application.MemberService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RequestMapping("/api/members")
@RestController
class MemberController(
    private val memberService: MemberService
) {

    @PostMapping
    fun create(@RequestBody request: MemberCreateRequest): ResponseEntity<String> {
        val id = memberService.create(request).id
        return ResponseEntity.created(URI.create("/api/members/$id")).build()
    }

    @GetMapping("/{id}")
    fun find(
        @PathVariable id: String
    ): ResponseEntity<MemberResponse> {
        val member = memberService.find(id)
        return ResponseEntity.ok(member)
    }
}