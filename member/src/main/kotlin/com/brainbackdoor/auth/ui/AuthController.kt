package com.brainbackdoor.auth.ui

import com.brainbackdoor.auth.application.AuthService
import com.brainbackdoor.auth.domain.AuthenticationPrincipal
import com.brainbackdoor.auth.domain.LoginMember
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RequestMapping("/api/auth")
@RestController
class AuthController(
    private val authService: AuthService
) {

    @PostMapping("/login")
    fun login(@RequestBody request: LoginRequest): ResponseEntity<TokenResponse> {
        val token = authService.login(request)
        return ResponseEntity.ok(token)
    }

    @GetMapping("/token-confirm")
    fun checkToken(@AuthenticationPrincipal loginMember: LoginMember): LoginMember {
        return loginMember
    }

    @GetMapping("/logout")
    fun logout(
        @AuthenticationPrincipal loginMember: LoginMember,
    ): ResponseEntity<String> {
        authService.logout(loginMember)
        return ResponseEntity.ok().build()
    }
}