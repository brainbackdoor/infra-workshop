package com.brainbackdoor.web.auth

data class LoginMember(
    val id: String,
    val email: String,
    val roles: List<Role>
) {
    fun isAdmin(): Boolean = roles.contains(Role.ROLE_ADMIN)
}

enum class Role {
    ROLE_ADMIN,
    ROLE_STUDENT,
    ROLE_GUEST;
}