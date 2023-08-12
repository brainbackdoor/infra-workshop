package com.brainbackdoor.members.ui

import com.brainbackdoor.members.domain.Member


data class MemberCreateRequest(
    val mail: String,
    val password: String,
    val consentByMember: Boolean,
    val consentByPrivacy: Boolean
)

data class MemberResponse(
    val id: String,
    val mail: String,
    val roles: List<String>
) {
    constructor(member: Member) : this(
        member.id,
        member.mail(),
        member.roles.map { it.roleType.name }
    )
}