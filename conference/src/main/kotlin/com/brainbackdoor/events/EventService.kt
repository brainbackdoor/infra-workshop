package com.brainbackdoor.events

import com.brainbackdoor.events.EventRepository.Companion.events
import org.springframework.stereotype.Service

@Service
class EventService {

    fun findMbtiBy(id: String) = events().pick(id)

}