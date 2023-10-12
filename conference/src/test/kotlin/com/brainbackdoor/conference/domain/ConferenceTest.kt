package com.brainbackdoor.conference.domain

import com.brainbackdoor.conferences.domain.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.time.LocalDateTime
import kotlin.test.assertFailsWith

class ConferenceTest {
    @Test
    fun `참가비용은 양수여야 한다`() {
        val fee = -10_000

        assertThrows<IllegalArgumentException> { Conference(area, schedule, fee, recruitment) }
    }

    @ParameterizedTest
    @MethodSource("conferenceDay")
    fun `컨퍼런스 일정은 모집 기간 후여야 한다`(schedule: LocalDateTime) {
        val period = Period(
            LocalDateTime.of(2123, 10, 1, 0, 0),
            LocalDateTime.of(2123, 10, 5, 23, 59)
        )

        assertThrows<IllegalArgumentException> {
            Conference(area, schedule, fee, Recruitment(period, Applicants(1)))
        }
    }

    @Test
    fun `모집 정원보다 많은 사람이 컨퍼런스를 신청할 수 없다`() {
        val recruitment = Recruitment(period, Applicants(0))
        val conference = Conference(area, schedule, fee, recruitment)
        val applicant = Applicant("memberId", recruitment)

        assertThrows<IllegalArgumentException> { conference.recruit(applicant) }
    }

    @Test
    fun `모집 중이 아닐 경우 신청할 수 없다`() {
        val conference = Conference(area, schedule, fee, recruitment)
        val applicant = Applicant("memberId", recruitment)

        assertThat(conference.isBeforeStart()).isTrue()
        assertFailsWith<IllegalArgumentException> { conference.recruit(applicant) }

        conference.stop()
        assertThat(conference.isStopped()).isTrue()
        assertFailsWith<IllegalArgumentException> { conference.recruit(applicant) }

        conference.finish()
        assertThat(conference.isFinished()).isTrue()
        assertFailsWith<IllegalArgumentException> { conference.recruit(applicant) }
    }

    @Test
    fun `모집 중일 경우 신청할 수 있다`() {
        val applicant = Applicant("memberId", recruitment)
        val conference = Conference(area, schedule, fee, recruitment)
        conference.start()

        conference.recruit(applicant)
        assertThat(conference.isStarted()).isTrue()
        assertThat(conference.isApplied(applicant)).isTrue()
    }

    companion object {
        @JvmStatic
        fun conferenceDay(): Array<Arguments> {
            return arrayOf(
                Arguments.of(LocalDateTime.of(2122, 10, 6, 15, 0)),
                Arguments.of(LocalDateTime.of(2123, 9, 1, 20, 0)),
                Arguments.of(LocalDateTime.of(2123, 10, 1, 20, 0)),
                Arguments.of(LocalDateTime.of(2123, 10, 5, 23, 58)),
                Arguments.of(LocalDateTime.of(2123, 10, 5, 23, 59))

            )
        }
    }

    private val area = "코엑스"
    private val schedule = LocalDateTime.of(2123, 10, 10, 15, 0)
    private val fee = 10_000
    private val period = Period(
        LocalDateTime.now().minusDays(1L),
        LocalDateTime.of(2123, 10, 9, 23, 59)
    )
    private val recruitment = Recruitment(period, Applicants(limited = 10))
}