package com.brainbackdoor.auth.ui

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "로그인 요청")
data class LoginRequest(

    @Schema(title = "회원 이메일")
    val email: String,

    @Schema(title = "비밀번호")
    val password: String
)

@Schema(title = "토큰 응답")
data class TokenResponse(

    @Schema(title = "액세스 토큰")
    val accessToken: String,

    @Schema(title = "회원 이메일")
    val email: String = ""
)