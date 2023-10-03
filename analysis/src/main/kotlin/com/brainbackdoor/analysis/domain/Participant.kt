package com.brainbackdoor.analysis.domain

import com.brainbackdoor.domain.AutoIncrementId
import jakarta.persistence.Entity

@Entity
class Participant(
    var memberId: String,
    var surveyId: String,
    var lectureId: String
) : AutoIncrementId<Participant>()