package com.brainbackdoor.members.web

import com.brainbackdoor.members.domain.Member
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "회원 생성 요청")
data class MemberCreateRequest(
    @Schema(title = "회원 이메일")
    val email: String,

    @Schema(title = "비밀번호")
    val password: String,

    @Schema(title = "회원 약관 동의")
    val consentByMember: Boolean,

    @Schema(title = "개인정보 수취 동의")
    val consentByPrivacy: Boolean
)

@Schema(description = "회원 응답")
data class MemberResponse(
    @Schema(title = "회원 아이디")
    val id: String,

    @Schema(title = "회원 이메일")
    val email: String,

    @Schema(title = "회원 권한")
    val roles: List<String>
) {
    constructor(member: Member) : this(
        member.id,
        member.email(),
        member.roles.map { it.roleType.name }
    )
}