package com.brainbackdoor.conference.application

import com.brainbackdoor.conference.domain.*
import com.brainbackdoor.conference.ui.ConferenceRequest
import com.brainbackdoor.conference.ui.ConferenceResponse
import com.brainbackdoor.conference.ui.ConferenceStatusRequest
import com.brainbackdoor.conference.ui.ConferenceStatusResponse
import exception.ResourceNotFoundException
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
@Transactional
class ConferenceService(
    private val conferenceRepository: ConferenceRepository,
) {
    fun create(request: ConferenceRequest): ConferenceResponse {
        val conference = conferenceRepository.save(request.of())
        return ConferenceResponse(conference)
    }

    fun findBy(
        schedule: String?,
        area: String?,
        status: String?,
    ): List<ConferenceResponse> =
        conferenceRepository
            .findAllSortAndFilter(schedule, area, status)
            .map { ConferenceResponse(it) }

    fun findAll(): List<ConferenceResponse> =
        conferenceRepository.findAllSort().map { ConferenceResponse(it) }

    fun findById(id: String): ConferenceResponse = ConferenceResponse(conference(id))

    fun update(id: String, request: ConferenceRequest): ConferenceResponse =
        ConferenceResponse(conference(id).update(request.of()))

    fun status(id: String): ConferenceStatusResponse =
        ConferenceStatusResponse(conference(id))

    fun updateStatus(id: String, request: ConferenceStatusRequest): ConferenceStatusResponse {
        val conference = conference(id)
        conference.updateStatus(request.status)
        return ConferenceStatusResponse(conference)
    }

    fun delete(id: String) = conferenceRepository.delete(conference(id))

    private fun conference(id: String): Conference =
        conferenceRepository
            .findById(id)
            .orElseThrow { throw ResourceNotFoundException("$id 의 컨퍼런스가 없습니다.") }

    private fun ConferenceRequest.of() =
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