package com.brainbackdoor.auth.domain

import com.brainbackdoor.members.domain.Member
import com.brainbackdoor.members.domain.guestMember


data class LoginMember(
    val id: String,
    val email: String
) {
    constructor(member: Member): this(member.id, member.mail())

    companion object {
        fun guestLoginMember() : LoginMember = LoginMember(guestMember())
    }
}
