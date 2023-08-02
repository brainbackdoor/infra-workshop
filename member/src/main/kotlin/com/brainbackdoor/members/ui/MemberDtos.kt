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
) {
    constructor(member: Member) : this(
        member.id,
        member.mail()
    )
}