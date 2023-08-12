package com.brainbackdoor.conferences.domain

import com.brainbackdoor.domain.RandomId
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
class Conference(

    var area: String,

    var schedule: LocalDateTime,

    @Lob
    var contents: String = "",

    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(foreignKey = ForeignKey(name = "fk_conference_to_recruitment"))
    var recruitment: Recruitment,

    ) : RandomId<Conference>() {
    fun isBeforeStart(): Boolean = recruitment.isBeforeStart()

    fun isStarted(): Boolean = recruitment.isStarted()

    fun isStooped(): Boolean = recruitment.isStooped()

    fun isFinished(): Boolean = recruitment.isFinished()
    fun update(conference: Conference): Conference {
        this.area = conference.area
        this.schedule = conference.schedule
        this.recruitment = conference.recruitment
        return this
    }

    fun status() = recruitment.status
    fun updateStatus(status: String) {
        when (status) {
            RecruitmentStatus.START.name -> start()
            RecruitmentStatus.STOP.name -> stop()
            RecruitmentStatus.FINISH.name -> finish()
        }
    }

    private fun start() = recruitment.start()

    private fun stop() = recruitment.stop()

    private fun finish() = recruitment.finish()
    fun recruit(applicant: Applicant) {
        check(isStarted()) { throw IllegalArgumentException("모집 중이 아니라, 신청할 수 없습니다.") }
        check(!recruitment.isDone()) { throw IllegalArgumentException("모집 기간이 지나 신청할 수 없습니다.") }

        recruitment.join(applicant)
    }
}
