package com.brainbackdoor.members.domain

import domain.AutoIncrementId
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated

@Entity
class Role(
    @Column(unique = true)
    @Enumerated(EnumType.STRING)
    val roleType: RoleType = RoleType.ROLE_GUEST,
    id: Long = 0
) : AutoIncrementId<Role>(id) {

    companion object {
        @JvmStatic
        fun admin(): Role = Role(RoleType.ROLE_ADMIN)

        @JvmStatic
        fun guest(): Role = Role(RoleType.ROLE_GUEST)
    }

    override fun equals(other: Any?): Boolean {
        return roleType.name == (other as Role).roleType.name
    }
}

enum class RoleType {
    ROLE_ADMIN,
    ROLE_GUEST;

    fun createRole(): Role {
        return Role(this)
    }
}