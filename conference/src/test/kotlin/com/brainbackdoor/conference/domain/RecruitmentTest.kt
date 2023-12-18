package com.brainbackdoor.conference.domain

import com.brainbackdoor.conferences.domain.Applicant
import com.brainbackdoor.conferences.domain.Applicants
import com.brainbackdoor.conferences.domain.Period
import com.brainbackdoor.conferences.domain.Recruitment
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.time.LocalDateTime

class RecruitmentTest {
    @Test
    fun `LocalDateTime 형 변환`() {
        val actual = LocalDateTime.of(2123, 10, 5, 23, 59)
        val parse = LocalDateTime.parse("2123-10-05T23:59")

        assertThat(actual).isEqualTo(parse)
    }

    @ParameterizedTest
    @MethodSource("period")
    fun `모집 기간의 종료일은 시작일보다 앞설 수 없다`(start: LocalDateTime, end: LocalDateTime) {
        assertThrows<IllegalArgumentException> { Period(start, end) }
    }

    @Test
    fun `모집 중이 아닐 경우 신청자를 추가할 수 없다`() {
        val recruitment = Recruitment(period, applicants)
        val applicant = Applicant("memberId", recruitment)

        assertThat(recruitment.isBeforeStart()).isTrue()
        assertThrows<IllegalArgumentException> { recruitment.join(applicant) }

        recruitment.stop()
        assertThat(recruitment.isStopped()).isTrue()
        assertThrows<IllegalArgumentException> { recruitment.join(applicant) }

        recruitment.finish()
        assertThat(recruitment.isFinished()).isTrue()
        assertThrows<IllegalArgumentException> { recruitment.join(applicant) }
    }

    @Test
    fun `모집 중에는 신청자를 추가할 수 있다`() {
        val recruitment = Recruitment(period, applicants)
        val applicant = Applicant("memberId", recruitment)

        recruitment.start()
        assertThat(recruitment.isStarted()).isTrue()
        assertDoesNotThrow { recruitment.join(applicant) }
    }

    @Test
    fun `모집 정원이 다 찰 경우, 신청자를 추가할 수 없다`() {
        val recruitment = Recruitment(period, Applicants(limited = 0))
        val applicant = Applicant("memberId", recruitment)

        recruitment.start()
        assertThat(recruitment.isStarted()).isTrue()
        assertThrows<IllegalArgumentException> { recruitment.join(applicant) }
    }

    @Test
    fun `모집 전 혹은 모집 중단시에는 모집 최대 인원을 변경할 수 있다`() {
        val recruitment = Recruitment(period, applicants)

        assertThat(recruitment.isBeforeStart()).isTrue()
        assertDoesNotThrow { recruitment.updateLimit(5) }

        recruitment.stop()
        assertDoesNotThrow { recruitment.updateLimit(10) }
    }

    @Test
    fun `모집 중 혹은 모집이 완료된 경우에는 모집 최대 인원을 변경할 수 없다`() {
        val recruitment = Recruitment(period, applicants)

        recruitment.start()
        assertThat(recruitment.isStarted()).isTrue()
        assertThrows<IllegalArgumentException> { recruitment.updateLimit(5) }

        recruitment.finish()
        assertThat(recruitment.isFinished()).isTrue()
        assertThrows<IllegalArgumentException> { recruitment.updateLimit(5) }
    }

    @Test
    fun `신청자가 하나의 모집에 두번 신청할 수 없다`() {
        val recruitment = Recruitment(period, applicants)
        val applicant = Applicant("memberId", recruitment)

        recruitment.start()
        assertThat(recruitment.isStarted()).isTrue()
        assertDoesNotThrow { recruitment.join(applicant) }
        assertThrows<IllegalArgumentException> { recruitment.join(applicant) }
    }

    @Test
    fun `모집인원이 다 차면 모집이 마감된다`() {
        val recruitment = Recruitment(period, Applicants(limited = 1))
        val applicant = Applicant("memberId", recruitment)
        recruitment.start()
        recruitment.join(applicant)

        assertThat(recruitment.isFinished()).isTrue()

        val other = Applicant("member2", recruitment)
        assertThrows<IllegalArgumentException> { recruitment.join(other) }
    }

    @Test
    fun `모집 마감 중 신청자가 취소를 하면, 모집 중으로 전환된다`() {
        val recruitment = Recruitment(period, Applicants(limited = 1))
        val applicant = Applicant("memberId", recruitment)
        recruitment.start()
        recruitment.join(applicant)
        val other = Applicant("member2", recruitment)
        assertThrows<IllegalArgumentException> { recruitment.join(other) }

        recruitment.leave(applicant)
        assertThat(recruitment.isStarted()).isTrue()
        assertDoesNotThrow { recruitment.join(other) }
        assertThat(recruitment.contains(applicant)).isFalse()
        assertThat(recruitment.contains(other)).isTrue()
    }

    @Test
    fun `신청자가 아닐 경우, 취소를 할 수 없다`() {
        val recruitment = Recruitment(period, Applicants(limited = 1))
        val applicant = Applicant("memberId", recruitment)
        recruitment.start()
        recruitment.join(applicant)
        val other = Applicant("member2", recruitment)
        assertThrows<IllegalArgumentException> { recruitment.leave(other) }
    }

    @Test
    fun `모집을 중지하면 신청자를 추가할 수 없다`() {
        val recruitment = Recruitment(period, applicants)
        val applicant = Applicant("memberId", recruitment)
        recruitment.start()
        assertDoesNotThrow { recruitment.join(applicant) }

        recruitment.stop()
        assertThat(recruitment.isStopped()).isTrue()
        assertThrows<IllegalArgumentException> { recruitment.join(applicant) }
    }

    @Test
    fun `모집 중지 중 신청자가 취소를 하여도, 새로운 신청자를 추가할 수 없다`() {
        val recruitment = Recruitment(period, applicants)
        val applicant = Applicant("memberId", recruitment)
        recruitment.start()
        recruitment.join(applicant)

        recruitment.stop()
        val other = Applicant("member2", recruitment)
        assertThrows<IllegalArgumentException> { recruitment.join(other) }

        recruitment.leave(applicant)
        assertThat(recruitment.contains(applicant)).isFalse()
        assertThrows<IllegalArgumentException> { recruitment.join(other) }
    }

    companion object {
        @JvmStatic
        fun period(): Array<Arguments> {
            return arrayOf(
                Arguments.of(
                    LocalDateTime.of(2123, 10, 5, 23, 59),
                    LocalDateTime.of(2123, 10, 1, 0, 0)
                ),
                Arguments.of(
                    LocalDateTime.of(2123, 10, 1, 0, 0),
                    LocalDateTime.of(2122, 10, 5, 23, 59)
                ),
                Arguments.of(
                    LocalDateTime.of(2123, 10, 1, 20, 0),
                    LocalDateTime.of(2123, 10, 1, 13, 59)
                ),
                Arguments.of(
                    LocalDateTime.of(2123, 10, 1, 20, 0),
                    LocalDateTime.of(2123, 9, 1, 13, 59)
                )
            )
        }
    }

    private val period = Period(
        LocalDateTime.now().minusDays(1L),
        LocalDateTime.of(2123, 10, 9, 23, 59)
    )

    private val applicants = Applicants(limited = 10)
}