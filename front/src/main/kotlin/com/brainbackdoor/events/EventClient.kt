package com.brainbackdoor.events

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.*

@FeignClient(
    name = "event-client",
    url = "\${external.event.host}"
)
interface EventClient {
    @GetMapping("/api/events/mbti/{id}")
    fun findMbtiBy(@PathVariable id: String): Event
}