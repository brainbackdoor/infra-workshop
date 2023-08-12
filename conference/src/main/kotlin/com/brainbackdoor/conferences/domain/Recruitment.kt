package com.brainbackdoor.conferences.domain

import com.brainbackdoor.domain.RandomId
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
    var applicants: Applicants = Applicants(),
) : RandomId<Recruitment>() {
    fun isBeforeStart(): Boolean = status.ordinal < RecruitmentStatus.START.ordinal

    fun isStarted(): Boolean = status == RecruitmentStatus.START

    fun isStooped(): Boolean = status == RecruitmentStatus.STOP

    fun isFinished(): Boolean = status == RecruitmentStatus.FINISH

    fun start() {
        status = RecruitmentStatus.START
    }

    fun stop() {
        status = RecruitmentStatus.STOP
    }

    fun finish() {
        status = RecruitmentStatus.FINISH
    }

    fun isDone(): Boolean = recruitmentPeriod.isBefore(LocalDateTime.now())
    fun join(applicant: Applicant) {
        check(isStarted()) { throw IllegalArgumentException("모집 중이 아니라, 신청할 수 없습니다.") }

        applicants.join(applicant)
    }
}

@Embeddable
class Period(
    var periodStart: LocalDateTime,
    var periodEnd: LocalDateTime,
) {
    fun isBefore(target: LocalDateTime): Boolean = periodEnd.isBefore(target)

    init {
        check(periodStart.isBefore(periodEnd)) {
            throw IllegalArgumentException("컨퍼런스 모집 기간의 종료일은 시작일보다 앞설 수 없습니다.")
        }
    }
}

enum class RecruitmentStatus {
    READY, START, STOP, FINISH
}
