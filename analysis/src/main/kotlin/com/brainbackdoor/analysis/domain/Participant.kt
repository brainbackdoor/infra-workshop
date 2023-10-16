package com.brainbackdoor.analysis.domain

import jakarta.persistence.Entity

@Entity
class Participant(
    var id: Long,
    var memberId: String,
    var surveyId: Long,
    var lectureId: String
)