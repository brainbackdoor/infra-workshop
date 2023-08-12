package com.brainbackdoor.conference.application

import com.brainbackdoor.conference.domain.Conference
import com.brainbackdoor.conference.domain.ConferenceRepository
import com.brainbackdoor.conference.domain.Period
import com.brainbackdoor.conference.domain.Recruitment
import com.brainbackdoor.conference.ui.ConferenceCreateRequest
import com.brainbackdoor.conference.ui.ConferenceCreateResponse
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
@Transactional
class ConferenceService(
    private val conferenceRepository: ConferenceRepository
) {
    fun create(request: ConferenceCreateRequest): ConferenceCreateResponse {
        val conference = conferenceRepository.save(request.of())
        return ConferenceCreateResponse(conference)
    }


    private fun ConferenceCreateRequest.of() =
        Conference(
            area,
            LocalDateTime.parse(conferenceSchedule),
            contents,
            Recruitment(
                Period(
                    LocalDateTime.parse(recruitmentStartDate),
                    LocalDateTime.parse(recruitmentEndDate)
                ),
                fee
            )
        )
}