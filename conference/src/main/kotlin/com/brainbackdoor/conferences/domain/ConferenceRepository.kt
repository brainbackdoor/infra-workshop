package com.brainbackdoor.conferences.domain

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

fun ConferenceRepository.findAllSortAndFilter(
    schedule: String?, area: String?, status: String?
): List<Conference> {
    var result = findAllSort().filter { it.isStarted() || it.isFinished() }

    if (schedule?.isNotEmpty() == true) {
        result = result.filter { it.schedule.toString() == schedule }
    }

    if (area?.isNotEmpty() == true) {
        result = result.filter { it.area == area }
    }

    if (status?.isNotEmpty() == true) {
        result = result.filter { it.recruitment.status.name == status }
    }

    return result
}

interface ConferenceRepository : JpaRepository<Conference, String> {
    @Query(value = "SELECT c FROM Conference c ORDER BY c.schedule, c.recruitment.recruitmentPeriod.periodEnd, c.area")
    fun findAllSort(): List<Conference>
}