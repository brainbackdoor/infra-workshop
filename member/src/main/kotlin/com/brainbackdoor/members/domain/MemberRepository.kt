package com.brainbackdoor.members.domain

import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface MemberRepository : JpaRepository<Member, String> {
    fun findByEmailAddress(email: String): Optional<Member>
    fun existsMemberByEmailAddress(email: String): Boolean
}