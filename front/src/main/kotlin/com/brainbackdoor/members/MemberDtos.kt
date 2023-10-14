package com.brainbackdoor.members

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "회원 응답")
data class MemberResponse(
    @Schema(title = "회원 아이디")
    val id: String,

    @Schema(title = "회원 이메일")
    val email: String,

    @Schema(title = "회원 권한")
    val roles: List<String>
)