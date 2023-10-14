package com.brainbackdoor.members.web

import com.brainbackdoor.auth.AdminAuth
import com.brainbackdoor.members.application.MemberService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI

@Tag(name = "MemberController", description = "회원시스템 API")
@RequestMapping("/api/members")
@RestController
class MemberController(
    private val memberService: MemberService,
) {

    @Operation(summary = "회원 생성")
    @PostMapping
    fun create(
        @RequestBody request: MemberCreateRequest,
    ): ResponseEntity<String> {
        val id = memberService.create(request).id
        return ResponseEntity.created(URI.create("/api/members/$id")).build()
    }

    @Operation(summary = "회원 조회")
    @GetMapping("/{id}")
    @AdminAuth
    fun find(
        @Parameter(name = "id", description = "회원 아이디") @PathVariable id: String,
    ): ResponseEntity<MemberResponse> {
        val member = memberService.find(id)
        return ResponseEntity.ok(member)
    }

    @Operation(summary = "전체 회원 조회")
    @GetMapping
    @AdminAuth
    fun findAll(): ResponseEntity<List<MemberResponse>> =
        ResponseEntity.ok(memberService.findAll())
}