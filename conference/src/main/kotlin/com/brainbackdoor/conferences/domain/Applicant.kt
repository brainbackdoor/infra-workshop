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
    override fun toString(): String {
        return "Applicant(memberId='$memberId')"
    }
}