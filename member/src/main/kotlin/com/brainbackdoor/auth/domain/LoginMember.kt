package com.brainbackdoor.auth.domain

import com.brainbackdoor.members.domain.Member


data class LoginMember(
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
