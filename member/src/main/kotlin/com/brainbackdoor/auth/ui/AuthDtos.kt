package com.brainbackdoor.auth.ui

data class LoginRequest(
    val email: String,
    val password: String
)

data class TokenResponse(
    val accessToken: String,
    val email: String = ""
)