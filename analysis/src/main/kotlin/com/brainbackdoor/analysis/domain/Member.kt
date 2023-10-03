package com.brainbackdoor.analysis.domain

import com.brainbackdoor.domain.BaseAggregateRootEntity
import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity
class Member(
    @Id
    var memberId: String,
    var age: Int,
) : BaseAggregateRootEntity<Member>()