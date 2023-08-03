package com.brainbackdoor.auth.domain

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface LoginTokenRepository : JpaRepository<LoginToken, Long> {
    @Query("SELECT m FROM LoginToken m WHERE m.memberId = :memberId AND m.disabled = FALSE ORDER BY m.id DESC")
    fun findMember(memberId: String): List<LoginToken>
}