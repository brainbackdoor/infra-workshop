package com.brainbackdoor.members.domain

import org.springframework.data.jpa.repository.JpaRepository

interface RoleRepository : JpaRepository<Role, Long> {
    fun findByRoleType(roleType: RoleType): Role?
}