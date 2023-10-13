package com.brainbackdoor.auth.web

import com.brainbackdoor.auth.Auth
import com.brainbackdoor.auth.application.AuthService
import com.brainbackdoor.auth.domain.LoginMember
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@Tag(name = "AuthController", description = "인증시스템 API")
@RequestMapping("/api/auth")
@RestController
class AuthController(
    private val authService: AuthService,
) {

    @Operation(summary = "로그인")
    @PostMapping("/login")
    fun login(@RequestBody request: LoginRequest): ResponseEntity<TokenResponse> =
        ResponseEntity.ok(authService.login(request))


    @Operation(summary = "인증한 회원 정보 조회")
    @GetMapping("/me")
    fun findMe(@Auth loginMember: LoginMember): ResponseEntity<LoginMember> =
        ResponseEntity.ok(loginMember)


    @Operation(summary = "로그아웃")
    @PostMapping("/logout")
    fun logout(@Auth loginMember: LoginMember): ResponseEntity<String> {
        authService.logout(loginMember)
        return ResponseEntity.ok().build()
    }
}