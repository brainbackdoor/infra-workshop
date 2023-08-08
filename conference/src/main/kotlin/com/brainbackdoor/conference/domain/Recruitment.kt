package com.brainbackdoor.conference.domain

import domain.RandomId
import jakarta.persistence.Embeddable
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import java.time.LocalDateTime

@Entity
class Recruitment(
    @Embedded
    var recruitmentPeriod: Period,

    var fee: Int = 0,

    @Enumerated(EnumType.STRING)
    var status: RecruitmentStatus = RecruitmentStatus.READY,
) : RandomId<Recruitment>()

@Embeddable
class Period(
    start: LocalDateTime,
    private var end: LocalDateTime
) {
    constructor(start: String, end: String) : this(LocalDateTime.parse(start), LocalDateTime.parse(end))

    fun isBefore(testDay: LocalDateTime): Boolean = end.isBefore(testDay)

    init {
        check(start.isBefore(end)) {
            throw IllegalArgumentException("컨퍼런스 모집 기간의 종료일은 시작일보다 앞설 수 없습니다.")
        }
    }
}

enum class RecruitmentStatus {
    READY, START, STOP, FINISH
}
