package com.brainbackdoor.conferences.domain

import com.brainbackdoor.domain.RandomId
import jakarta.persistence.Entity
import jakarta.persistence.ForeignKey
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne

@Entity
class Applicant(
    var memberId: String,

    @ManyToOne
    @JoinColumn(foreignKey = ForeignKey(name = "fk_applicant_to_recruitment"))
    private var recruitment: Recruitment,
) : RandomId<Applicant>() {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        if (!super.equals(other)) return false

        other as Applicant

        return memberId == other.memberId
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + memberId.hashCode()
        return result
    }
}