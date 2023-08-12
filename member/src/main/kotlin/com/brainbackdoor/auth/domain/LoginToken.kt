package com.brainbackdoor.auth.domain

import com.brainbackdoor.domain.AutoIncrementId
import jakarta.persistence.Column
import jakarta.persistence.Entity
import com.brainbackdoor.support.RandomValueIdGenerator.Companion.ID_LENGTH
import java.time.LocalDate

@Entity
class LoginToken(

    @Column(length = ID_LENGTH)
    val memberId: String,
    val token: String,
    private var disabled: Boolean = false
) : AutoIncrementId<LoginToken>() {
    fun logout() {
        this.disabled = true
    }

    fun isToday(): Boolean = LocalDate.now().isEqual(createdDate!!.toLocalDate())
}