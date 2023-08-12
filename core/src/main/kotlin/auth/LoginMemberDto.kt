package auth

data class LoginMemberDto(
    val id: String,
    val email: String,
    val roles: List<Role>
) {
    fun isAdmin(): Boolean = roles.contains(Role.ROLE_ADMIN)
}

enum class Role {
    ROLE_ADMIN,
    ROLE_GUEST;
}