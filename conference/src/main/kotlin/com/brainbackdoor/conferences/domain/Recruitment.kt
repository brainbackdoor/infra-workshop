package com.brainbackdoor.conferences.domain

import com.brainbackdoor.domain.RandomId
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
class Recruitment(
    @Embedded
    var recruitmentPeriod: Period,

    @Embedded
    var applicants: Applicants,

    @Enumerated(EnumType.STRING)
    var status: RecruitmentStatus = RecruitmentStatus.READY,

    ) : RandomId<Recruitment>() {
    fun start() {
        status = RecruitmentStatus.START
    }

    fun stop() {
        status = RecruitmentStatus.STOP
    }

    fun finish() {
        status = RecruitmentStatus.FINISH
    }

    fun join(applicant: Applicant) {
        check(isStarted()) { throw IllegalArgumentException("모집 중이 아니라, 신청할 수 없습니다.") }
        check(isDone()) { throw IllegalArgumentException("모집 기간이 아니라서, 신청할 수 없습니다.") }

        applicants.join(applicant)
        if (applicants.isFulled()) {
            finish()
        }
    }

    fun leave(applicant: Applicant) {
        applicants.leave(applicant)

        if (isJoinable()) {
            start()
        }
    }

    fun lottery() {
        check(isFinished()) { throw IllegalArgumentException("모집이 완료된 후에야, 추첨을 진행할 수 있습니다.") }

        applicants.lottery()
    }

    fun updateLimit(limit: Int) {
        check(isBeforeStart() || isStopped()) {
            throw IllegalArgumentException("모집 전 혹은 모집 중단시에만 모집 최대 인원을 수정할 수 있습니다.")
        }

        applicants.limited = limit
    }

    fun contains(applicant: Applicant): Boolean = applicants.contains(applicant)

    fun isBeforeStart(): Boolean = status.ordinal < RecruitmentStatus.START.ordinal

    fun isStarted(): Boolean = status == RecruitmentStatus.START

    fun isStopped(): Boolean = status == RecruitmentStatus.STOP

    fun isFinished(): Boolean = status == RecruitmentStatus.FINISH

    fun isJoinable() = !applicants.isFulled() && !isStopped()

    private fun isDone(): Boolean = recruitmentPeriod.isBefore(LocalDateTime.now())
}

@Embeddable
class Period(
    var periodStart: LocalDateTime,
    var periodEnd: LocalDateTime,
) {
    fun isBefore(target: LocalDateTime): Boolean = target.isBefore(periodEnd)

    init {
        check(periodStart.isBefore(periodEnd)) {
            throw IllegalArgumentException("컨퍼런스 모집 기간의 종료일은 시작일보다 앞설 수 없습니다.")
        }
    }
}

enum class RecruitmentStatus {
    READY, START, STOP, FINISH
}
