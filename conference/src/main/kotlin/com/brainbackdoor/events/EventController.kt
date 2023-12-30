package com.brainbackdoor.events

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@Tag(name = "EventController", description = "이벤트시스템 API")
@RequestMapping("/api/events")
@RestController
class EventController(
    private val eventService: EventService,
) {

    @Operation(summary = "회원별 MBTI 조회")
    @GetMapping("/mbti/{id}")
    fun findMbtiById(
        @Parameter(name = "id", description = "회원 아이디") @PathVariable id: String
    ): ResponseEntity<Event> =
        ResponseEntity.ok(eventService.findMbtiBy(convert(id)))


    companion object {
        private const val MBTI_BOUNDARY: Int = 16
        fun convert(id: String): Int = id.toCharArray()[0].code % MBTI_BOUNDARY
    }
}