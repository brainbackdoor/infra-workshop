package com.brainbackdoor.conferences.domain

import com.brainbackdoor.domain.RandomId
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
class Conference(

    var area: String,

    var schedule: LocalDateTime,

    var fee: Int = 0,

    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(foreignKey = ForeignKey(name = "fk_conference_to_recruitment"))
    var recruitment: Recruitment,

    @Lob
    var contents: String = "",


    ) : RandomId<Conference>() {

    init {
        check(fee >= 0) {
            throw IllegalArgumentException("참가비용은 0원 이상이어야 합니다.")
        }
    }
    fun isBeforeStart(): Boolean = recruitment.isBeforeStart()

    fun isStarted(): Boolean = recruitment.isStarted()

    fun isStooped(): Boolean = recruitment.isStopped()

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
        recruitment.join(applicant)
    }
}
