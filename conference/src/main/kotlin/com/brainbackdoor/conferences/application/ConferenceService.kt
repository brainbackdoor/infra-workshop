package com.brainbackdoor.conferences.application

import com.brainbackdoor.conferences.domain.*
import com.brainbackdoor.conferences.ui.*
import com.brainbackdoor.exception.ResourceNotFoundException
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
@Transactional
class ConferenceService(
    private val conferenceRepository: ConferenceRepository,
    private val applicantRepository: ApplicantRepository
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

    fun findAll(): List<ConferenceAllResponse> =
        conferenceRepository.findAllSort().map { ConferenceAllResponse(it) }

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

    fun recruit(memberId: String, id: String): ConferenceResponse {
        val conference = conference(id)
        val applicant = Applicant(memberId, conference.recruitment)
        applicantRepository.save(applicant)
        conference.recruit(applicant)
        return ConferenceResponse(conference)
    }

    private fun conference(id: String): Conference =
        conferenceRepository
            .findById(id)
            .orElseThrow { throw ResourceNotFoundException("$id 의 컨퍼런스가 없습니다.") }

    private fun ConferenceRequest.of(): Conference {
        val conference = Conference(
            area,
            LocalDateTime.parse(conferenceSchedule),
            contents,
            Recruitment(
                Period(
                    LocalDateTime.parse(recruitmentStartDate),
                    LocalDateTime.parse(recruitmentEndDate)
                ),
                fee,
            )
        )
        conference.recruitment.applicants.limited = limited
        return conference
    }

}