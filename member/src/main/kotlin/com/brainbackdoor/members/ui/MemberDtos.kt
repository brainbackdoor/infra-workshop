package com.brainbackdoor.members.ui

import com.brainbackdoor.members.domain.Member


data class MemberCreateRequest(
    val email: String,
    val password: String,
    val consentByMember: Boolean,
    val consentByPrivacy: Boolean
)

data class MemberResponse(
    val id: String,
    val email: String,
    val roles: List<String>
) {
    constructor(member: Member) : this(
        member.id,
        member.email(),
        member.roles.map { it.roleType.name }
    )
}