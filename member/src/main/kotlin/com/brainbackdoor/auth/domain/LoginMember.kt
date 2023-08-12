package com.brainbackdoor.auth.domain

import com.brainbackdoor.members.domain.Member
import com.brainbackdoor.members.domain.Role


data class LoginMember(
    val id: String,
    val email: String,
    val roles: List<String>
) {
    constructor(member: Member) : this(
        member.id,
        member.mail(),
        member.roles.map { it.roleType.name }
    )
}
