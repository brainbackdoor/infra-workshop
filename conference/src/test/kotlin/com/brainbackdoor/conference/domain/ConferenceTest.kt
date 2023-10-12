package com.brainbackdoor.conference.domain

import com.brainbackdoor.conferences.domain.Applicants
import com.brainbackdoor.conferences.domain.Conference
import com.brainbackdoor.conferences.domain.Period
import com.brainbackdoor.conferences.domain.Recruitment
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.time.LocalDateTime

class ConferenceTest {
    @Test
    fun `참가비용은 양수여야 한다`() {
        val fee = -10_000

        assertThrows<IllegalArgumentException> { Conference(area, schedule, fee, recruitment) }
    }

    private val area = "코엑스"
    private val schedule = LocalDateTime.of(2123, 10, 10, 15, 0)
    private val fee = 10_000
    private val recruitment =
        Recruitment(
            Period(
                LocalDateTime.now().minusDays(1L),
                LocalDateTime.of(2123, 10, 9, 23, 59)
            ),
            Applicants(limited = 10)
        )
}