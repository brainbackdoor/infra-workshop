package com.brainbackdoor.events

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping


@RequestMapping("/api/events")
class EventController(
    private val eventService: EventService,
) {

    @GetMapping("/mbti/{id}")
    fun findMbtiById(@PathVariable id: String): ResponseEntity<Event> =
        ResponseEntity.ok(eventService.findMbtiBy(convert(id)))


    companion object {
        private const val MBTI_BOUNDARY: Int = 16
        fun convert(id: String): Int = id.toCharArray()[0].code % MBTI_BOUNDARY
    }
}