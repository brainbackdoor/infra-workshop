package com.brainbackdoor.conference.domain

import domain.RandomId
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
class Recruitment(
    @Embedded
    var recruitmentPeriod: Period,

    var fee: Int = 0,

    @Enumerated(EnumType.STRING)
    var status: RecruitmentStatus = RecruitmentStatus.READY,

    @Embedded
    var applicants: Applicants = Applicants()
) : RandomId<Recruitment>()

@Embeddable
class Period(
    var periodStart: LocalDateTime,
    var periodEnd: LocalDateTime
) {
    constructor(start: String, end: String) : this(LocalDateTime.parse(start), LocalDateTime.parse(end))

    fun isBefore(testDay: LocalDateTime): Boolean = periodEnd.isBefore(testDay)

    init {
        check(periodStart.isBefore(periodEnd)) {
            throw IllegalArgumentException("컨퍼런스 모집 기간의 종료일은 시작일보다 앞설 수 없습니다.")
        }
    }
}

enum class RecruitmentStatus {
    READY, START, STOP, FINISH
}
