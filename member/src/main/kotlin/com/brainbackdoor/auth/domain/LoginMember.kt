package com.brainbackdoor.auth.domain

import com.brainbackdoor.members.domain.Member
import com.brainbackdoor.members.domain.RoleType
import com.fasterxml.jackson.annotation.JsonIgnore


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

    @JsonIgnore
    fun isAdmin(): Boolean = roles.contains(RoleType.ROLE_ADMIN.name)
}
