package com.brainbackdoor.analysis.domain

import com.brainbackdoor.domain.BaseAggregateRootEntity
import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity
class Lecture(
    @Id
    var lectureId: String,
    var name: String,
) : BaseAggregateRootEntity<Lecture>()