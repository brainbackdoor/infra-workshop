package com.brainbackdoor.members.domain

import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface MemberRepository : JpaRepository<Member, String> {
    fun findByMailAddress(mail: String): Optional<Member>
    fun existsMemberByMailAddress(mail: String): Boolean
}